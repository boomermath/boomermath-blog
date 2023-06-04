package com.boomermath.boomermathblog.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginData {
    private String nameOrEmail;
    private String password;
}
