package com.example.boomermathblog.data.entities;

import com.example.boomermathblog.data.values.BlogArticleRoles;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BlogUserArticles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @ToString.Exclude
    private BlogUser blogUser;

    @ManyToOne
    @ToString.Exclude
    private BlogArticle blogArticle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BlogArticleRoles articleRole;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate updatedDate;
}