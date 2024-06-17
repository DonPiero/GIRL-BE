package com.example.girlbe.assembler;

import com.example.girlbe.dto.request.UserEditProfileRequest;
import com.example.girlbe.dto.request.UserSignUpRequest;
import com.example.girlbe.dto.response.UserResponse;
import com.example.girlbe.model.User;
import com.example.girlbe.model.enums.Rank;

import java.util.ArrayList;

public class UserAssembler {

    public static User createUser(UserSignUpRequest userSignUpRequest) {
        User user = new User();
        user.setEmail(userSignUpRequest.getEmail());
        user.setPassword(userSignUpRequest.getPassword());
        user.setCompletedExp(0);
        user.setOngoingExp(0);
        user.setRank(Rank.BEGINNER);
        user.setExperiments(new ArrayList<>());
        return user;
    }

    public static UserResponse createUserResponse(User user) {
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .city(user.getCity())
                .country(user.getCountry())
                .phoneNumber(user.getPhoneNumber())
                .rank(user.getRank().getName())
                .profilePicture(user.getProfilePicture())
                .prefix(user.getPrefix())
                .email(user.getEmail())
                .completedExp(user.getCompletedExp())
                .ongoingExp(user.getOngoingExp())
                .build();
    }

    public static User updateUser(User user, UserEditProfileRequest userRequest){
           return User.builder()
                   .id(user.getId())
                   .rank(user.getRank())
                   .ongoingExp(user.getOngoingExp())
                   .completedExp(user.getCompletedExp())
                   .email(user.getEmail())
                   .profilePicture(!userRequest.getProfilePicture().isEmpty() ?  userRequest.getProfilePicture() : user.getProfilePicture())
                   .firstName(!userRequest.getFirstName().isEmpty() ? userRequest.getFirstName() : user.getFirstName())
                   .lastName(!userRequest.getLastName().isEmpty() ? userRequest.getLastName() : user.getLastName())
                   .prefix(!userRequest.getPrefix().isEmpty() ? userRequest.getPrefix() : user.getPrefix())
                   .phoneNumber(!userRequest.getPhoneNumber().isEmpty() ? userRequest.getPhoneNumber() : user.getPhoneNumber())
                   .country(!userRequest.getCountry().isEmpty() ? userRequest.getCountry() : user.getCountry())
                   .city(!userRequest.getCity().isEmpty() ? userRequest.getCity() : user.getCity())
                   .password(!userRequest.getPassword().isEmpty() ? userRequest.getPassword() : user.getPassword())
                   .build();
    }
}
