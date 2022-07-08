package io.gaterrays.userservice.Repo;

import io.gaterrays.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
