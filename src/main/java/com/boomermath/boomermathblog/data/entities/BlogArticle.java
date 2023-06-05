package com.boomermath.boomermathblog.data.entities;

import com.boomermath.boomermathblog.data.values.ArticleStatus;
import com.github.slugify.Slugify;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NamedEntityGraph(
        name = "testGraph",
        attributeNodes = @NamedAttributeNode("comments")
)
public class BlogArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @NotEmpty(message = "Specify a title")
    @Size(min = 5, max = 20, message = "Title must be between 8 and 20 characters")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "Only letters and numbers are allowed!")
    @Column(nullable = false, unique = true)
    private String title;

    @Column(unique = true, nullable = false)
    private String slug;

    @NotEmpty(message = "Specify a description")
    @Size(min = 5, max = 1000, message = "Description must be between 40 and 1000 characters")
    @Column(nullable = false)
    private String description;

    @NotEmpty(message = "Put something in your blog article")
    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private int likes = 0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @ManyToMany(mappedBy = "articles")
    @Builder.Default
    @ToString.Exclude
    private List<BlogTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    @Builder.Default
    @ToString.Exclude
    private List<BlogComment> comments = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    @ToString.Exclude
    private List<BlogUser> editors = new ArrayList<>();

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate lastModifiedDate;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private BlogUser author;

    @LastModifiedBy
    @ManyToOne
    @ToString.Exclude
    private BlogUser lastEditor;

    @PreUpdate
    @PrePersist
    private void configureSlug() {
        slug = Slugify.builder()
                .build()
                .slugify(title + " " + hashCode());
    }
}
