package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogTag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BlogTagRepository extends CrudRepository<BlogTag, UUID> {
    Optional<BlogTag> findBlogTagByNameContaining(String name);
}
