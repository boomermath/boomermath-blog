package com.boomermath.boomermathblog.data.dto.query;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {
    private String token;
}
