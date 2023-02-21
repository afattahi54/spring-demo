package com.example.spring.jpa.controller.payload;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@JsonTypeName(value = "user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class NewUserRequest {
  @NotBlank(message = "can't be empty")
  @Email(message = "should be an email")
  private String email;

  @NotBlank(message = "can't be empty")
  private String username;

  @NotBlank(message = "can't be empty")
  private String password;
}