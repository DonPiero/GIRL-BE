package com.example.girlbe.controller;

import com.example.girlbe.assembler.UserAssembler;
import com.example.girlbe.dto.request.UserEditProfileRequest;
import com.example.girlbe.dto.request.UserSignInRequest;
import com.example.girlbe.dto.request.UserSignUpRequest;
import com.example.girlbe.dto.response.UserResponse;
import com.example.girlbe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @GetMapping(path = "/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return UserAssembler.createUserResponse(userService.getUserById(id));
    }

    @GetMapping(path = "/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return UserAssembler.createUserResponse(userService.getUserByEmail(email));
    }

    @PostMapping(path = "/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserEditProfileRequest userEditProfileRequest) {
        return UserAssembler.createUserResponse(userService.updateUser(userEditProfileRequest, id));
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserSignUpRequest userSignUpRequest) {
        return userService.registerUser(userSignUpRequest);
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserSignInRequest userSignInRequest) {
        return userService.loginUser(userSignInRequest);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
