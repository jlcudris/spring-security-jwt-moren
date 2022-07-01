package io.gaterrays.userservice.Repo;

import io.gaterrays.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User userFindByUsername(String username);
}
