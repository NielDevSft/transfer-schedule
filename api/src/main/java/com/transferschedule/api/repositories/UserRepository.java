package com.transferschedule.api.repositories;

import com.transferschedule.api.models.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    public Optional<User> findByUsername(String username);
}
