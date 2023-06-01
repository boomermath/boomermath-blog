package com.boomermath.boomermathblog.data.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterData {
    private String username;
    private String email;
    private String password;
}
