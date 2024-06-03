package com.example.girlbe.dto.request;

import lombok.Getter;

@Getter
public class UserEditProfileRequest {
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String prefix;
    private String phoneNumber;
    private String country;
    private String city;
    private String password;
}
