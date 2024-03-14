package com.phuongdong.ss.Service;



import com.phuongdong.ss.DTO.AuthDTO;
import com.phuongdong.ss.DTO.UserResponseDTO;

import com.phuongdong.ss.Entity.User;
import com.phuongdong.ss.Entity.ValidToken;
import com.phuongdong.ss.Exception.AuthException;
import com.phuongdong.ss.Exception.NotFoundException;
import com.phuongdong.ss.Repository.UserRepository;
import com.phuongdong.ss.Repository.ValidTokenRepository;
import com.phuongdong.ss.Security.JwtGenerator;
import com.phuongdong.ss.Service.IService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    ValidTokenRepository validTokenRepository;


    @Autowired
    private JwtGenerator jwtGenerator;

    public UserResponseDTO login(AuthDTO authDTO) throws AuthException {
        Optional<User> optional = userRepository.findUserByEmail(authDTO.getEmail());

        if (optional.isPresent()) {
            User user = optional.get();
            if (authDTO.getPassword().equals(user.getPassword())) {
                ValidToken validToken = ValidToken
                        .builder()
                        .user(user)
                        .token(jwtGenerator.generateToken(user.getEmail()))
                        .createAt(new Date())
                        .build();
                ValidToken savedToken = validTokenRepository.save(validToken);
                return UserResponseDTO
                        .builder()
                        .access_token("Bearer " + savedToken.getToken())
                        .user(user)
                        .build();
            } else {
                Map<String, String> mapError = new HashMap<>();
                mapError.put("password", "Password không chính xác");
                throw new AuthException(mapError);
            }
        } else {
            Map<String, String> mapError = new HashMap<>();
            mapError.put("email", "Email không chính xác");
            throw new AuthException(mapError);
        }
    }

    public User loginWithGoogle(String email) throws NotFoundException {
        Optional<User> optional = userRepository.findUserByEmail(email);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NotFoundException("Người dùng không tồn tại");
        }
    }

    public void logout(String email) throws NotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("Người dùng chưa được xác thực");
        } else {
            User user = userOptional.get();
            validTokenRepository.deleteTokenByUserId(user.getId());
        }
    }
}
