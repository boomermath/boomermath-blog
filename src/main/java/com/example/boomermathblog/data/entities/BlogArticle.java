package com.example.boomermathblog.data.entities;

import com.example.boomermathblog.data.values.ArticleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
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

    @Transient
    private String slug;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @OneToMany
    private List<BlogComment> comments;

    @ManyToMany
    @JoinTable(
            name = "blog_article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<BlogTag> tags;

    @ManyToMany
    @JoinTable(
            name="blog_article_editors",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<BlogUser> editors;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate lastModified;

    @ManyToOne
    private BlogUser author;
    @PostLoad
    private void loadSlug() {
        slug = title.toLowerCase().replaceAll("\\s+", "");
    }
}
