package com.example.boomermathblog.data.entities;

import com.example.boomermathblog.data.values.ArticlesRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlogUserArticles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private BlogUser blogUser;

    @ManyToOne
    private BlogArticle blogArticle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticlesRoles articleRole;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate updatedDate;
}
