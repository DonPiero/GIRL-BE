package com.example.girlbe.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String profilePicture;
    private String rank;
    private String firstName;
    private String lastName;
    private String prefix;
    private String phoneNumber;
    private String country;
    private String city;
    private String email;
    private Integer ongoingExp;
    private Integer completedExp;
}
