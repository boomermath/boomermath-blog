package com.example.boomermathblog.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginData {
    private String nameOrEmail;
    private String password;
}
