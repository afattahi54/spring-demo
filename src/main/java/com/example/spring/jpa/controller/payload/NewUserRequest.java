package com.example.spring.jpa.controller.payload;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
  //@NotBlank(message = "can't be empty")
  //@Email(message = "should be an email")
  private String email;

  //@NotBlank(message = "can't be empty")
  private String username;

  //@NotBlank(message = "can't be empty")
  private String password;
}