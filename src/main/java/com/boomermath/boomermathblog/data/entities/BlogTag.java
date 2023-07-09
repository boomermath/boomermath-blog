package com.boomermath.boomermathblog.data.entities;

import jakarta.persistence.*;
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
public class BlogTag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<BlogArticle> articles = new ArrayList<>();

    public void addArticle(BlogArticle blogArticle) {
        articles.add(blogArticle);
    }
}
