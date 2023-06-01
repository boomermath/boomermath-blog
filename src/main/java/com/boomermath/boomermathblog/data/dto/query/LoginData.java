package com.boomermath.boomermathblog.data.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginData {
    private String nameOrEmail;
    private String password;
}
