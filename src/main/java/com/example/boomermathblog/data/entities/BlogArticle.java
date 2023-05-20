package com.example.boomermathblog.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class BlogArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @NotEmpty(message = "Specify a title")
    @Size(min = 8, max = 20, message = "Title must be between 8 and 20 characters")
    @Pattern(regexp = "[A-Z0-9]+", message = "Only letters and numbers are allowed!")
    @Column(nullable = false, unique = true)
    private String title;

    @NotEmpty(message = "Specify a description")
    @Size(min = 40, max = 1000, message = "Title must be between 40 and 1000 characters")
    @Column(nullable = false)
    private String description;

    @NotEmpty(message = "Put something in your blog article")
    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private int likes = 0;

    @OneToMany(mappedBy = "blogArticle")
    @ToString.Exclude
    private List<BlogUserArticles> blogUserArticles;
    public String getSlug() {
        return title.toLowerCase().trim().replaceAll("\\s+", "-");
    }
}
