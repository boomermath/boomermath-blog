package com.example.boomermathblog.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BlogComment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @ToString.Exclude
    private BlogUser blogUser;

    @ManyToOne
    @ToString.Exclude
    private BlogArticle blogArticle;
}
