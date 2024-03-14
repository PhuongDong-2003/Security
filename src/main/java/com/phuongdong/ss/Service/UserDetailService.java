package com.phuongdong.ss.Service;


import com.phuongdong.ss.DTO.UserDetailDTO;

import com.phuongdong.ss.Entity.Role;
import com.phuongdong.ss.Entity.User;
import com.phuongdong.ss.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findUserByEmail(email);

        if (optional.isPresent()) {
            User user = optional.get();
            return UserDetailDTO.builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .authorities(mapRolesToAuthorities(user.getRoles()))
                    .build();
        }
        return null;
    }

    public Collection<GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }


}
