package com.example.boomermathblog.data.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Getter
@Builder
public class JwtPayload {
    private UUID id;
    private GrantedAuthority role;
}
