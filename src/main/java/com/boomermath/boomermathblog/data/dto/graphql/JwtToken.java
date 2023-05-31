package com.boomermath.boomermathblog.data.dto.graphql;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {
    private String token;
}
