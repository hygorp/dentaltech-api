package com.dentaltechapi.repository.repositories.user;

import com.dentaltechapi.model.entities.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String login);
    Boolean existsByUsername(String username);
}
