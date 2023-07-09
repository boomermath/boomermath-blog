package com.boomermath.boomermathblog.data.entities;

import com.boomermath.boomermathblog.data.values.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NamedEntityGraph(
        name = "User.seenArticles",
        attributeNodes = {
                @NamedAttributeNode(value = "ownedArticles", subgraph = "Article.tags")
        },
        subgraphs =
        @NamedSubgraph(
                name = "Article.tags",
                attributeNodes = @NamedAttributeNode(value = "tags")
        )
)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @Builder.Default
    private List<BlogArticle> ownedArticles = new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<BlogArticle> readArticles = new ArrayList<>();

    public void addReadArticle(BlogArticle blogArticle) {
        if (blogArticle.getAuthor() == null) {
            blogArticle.setAuthor(this);
        }

        readArticles.add(blogArticle);
    }
}
