package com.thirdmoira.offer_backend.api.rest;

import lombok.Data;

@Data
public class ApiCreateUserRequest {
    String firstName;
    String lastName;
    String email;
    String password;
    Long groupId;
    Long userId;

}
