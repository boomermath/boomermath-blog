package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BlogUserRepository extends CrudRepository<BlogUser, UUID> {
    Optional<BlogUser> findBlogUserByUsername(String username);
}
