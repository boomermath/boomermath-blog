package com.example.boomermathblog.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @NotEmpty(message = "Specify a username")
    @Size(min = 8, max = 20, message = "Username must be between 8 and 20 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty(message = "Specify an email")
    @Email(regexp = ".+[@].+[\\.].+", message = "Specify a valid email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Specify a password")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @Column(nullable = false)
    private String password;
}