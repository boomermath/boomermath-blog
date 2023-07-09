package com.boomermath.boomermathblog.data.input;

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
