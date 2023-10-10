package com.randstad.events.infra.repositories;

import com.randstad.events.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
