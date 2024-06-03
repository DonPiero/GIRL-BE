package com.example.girlbe.service;

import com.example.girlbe.assembler.UserAssembler;
import com.example.girlbe.dto.request.UserEditProfileRequest;
import com.example.girlbe.dto.request.UserSignUpRequest;
import com.example.girlbe.model.User;
import com.example.girlbe.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor

public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    public User createUser(UserSignUpRequest userSignUpRequest) {
        return userRepository.save(UserAssembler.createUser(userSignUpRequest));
    }

    public User updateUser(UserEditProfileRequest userEditProfileRequest) {
        return userRepository.save(UserAssembler.updateUser(userRepository.findUserById(11L).get(), userEditProfileRequest));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
