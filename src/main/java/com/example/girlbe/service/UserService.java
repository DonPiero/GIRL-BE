package com.example.girlbe.service;

import com.example.girlbe.assembler.UserAssembler;
import com.example.girlbe.dto.request.UserEditProfileRequest;
import com.example.girlbe.dto.request.UserSignInRequest;
import com.example.girlbe.dto.request.UserSignUpRequest;
import com.example.girlbe.dto.response.UserResponse;
import com.example.girlbe.model.User;
import com.example.girlbe.repository.UserRepository;
import com.example.girlbe.util.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    public ResponseEntity<UserResponse> registerUser(UserSignUpRequest userSignUpRequest) {
        if (userRepository.findUserByEmail(userSignUpRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(UserAssembler.createUserResponse(userRepository.save(UserAssembler.createUser(userSignUpRequest))), HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> loginUser(UserSignInRequest userSignInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSignInRequest.getEmail(), userSignInRequest.getPassword()));
        User user = userRepository.findUserByEmail(userSignInRequest.getEmail()).orElse(null);
        if (user != null /* && passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword()) */) {
            return new ResponseEntity<>(UserAssembler.createUserResponse(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    public User updateUser(UserEditProfileRequest userEditProfileRequest, Long id) {
        return userRepository.save(UserAssembler.updateUser(userRepository.findUserById(id).get(), userEditProfileRequest));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
