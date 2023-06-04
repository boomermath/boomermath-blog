package com.boomermath.boomermathblog.data.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private BlogUser author;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private BlogArticle article;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdTimestamp;
}
