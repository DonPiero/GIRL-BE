package com.example.girlbe.controller;

import com.example.girlbe.assembler.UserAssembler;
import com.example.girlbe.dto.request.UserEditProfileRequest;
import com.example.girlbe.dto.request.UserSignUpRequest;
import com.example.girlbe.dto.response.UserResponse;
import com.example.girlbe.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return UserAssembler.createUserResponse(userService.getUserById(id));
    }

    @GetMapping(path = "/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return UserAssembler.createUserResponse(userService.getUserByEmail(email));
    }

    @PostMapping(path = "/")
    public UserResponse updateUser(@RequestBody UserEditProfileRequest userEditProfileRequest) {
        return UserAssembler.createUserResponse(userService.updateUser(userEditProfileRequest));
    }

    @PostMapping(path = "/signup")
    public UserResponse createUser(@RequestBody UserSignUpRequest userSignUpRequest) {
        return UserAssembler.createUserResponse(userService.createUser(userSignUpRequest));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }


}
