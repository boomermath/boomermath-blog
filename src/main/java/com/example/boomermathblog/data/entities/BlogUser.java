package com.example.boomermathblog.data.entities;

import com.example.boomermathblog.data.values.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
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
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "author")
    private List<BlogArticle> ownedArticles;

    @ManyToMany
    private List<BlogArticle> readArticles;
}
