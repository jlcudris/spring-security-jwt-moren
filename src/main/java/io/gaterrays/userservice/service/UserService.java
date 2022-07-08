package io.gaterrays.userservice.service;

import io.gaterrays.userservice.domain.Role;
import io.gaterrays.userservice.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    User getUser(String userName);
    List<User> getUsers();

    boolean findByUsername(String username);

    User finById(Long idUser);
}
