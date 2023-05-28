package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogUserRepository extends CrudRepository<BlogUser, UUID> {
    Optional<BlogUser> findBlogUserByUsernameOrEmail(String username, String email);

    Optional<BlogUser> findBlogUserByUsername(String username);
}
