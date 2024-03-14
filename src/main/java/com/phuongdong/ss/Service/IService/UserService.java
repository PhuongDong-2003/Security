package com.phuongdong.ss.Service.IService;


import com.phuongdong.ss.DTO.AuthDTO;
import com.phuongdong.ss.DTO.UserResponseDTO;
import com.phuongdong.ss.Exception.AuthException;
import com.phuongdong.ss.Exception.NotFoundException;

public interface UserService {


    public UserResponseDTO login(AuthDTO authDTO) throws AuthException;

    public void logout(String email) throws NotFoundException;

}
