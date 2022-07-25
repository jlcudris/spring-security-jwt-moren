package io.gaterrays.userservice.service;

import io.gaterrays.userservice.Repo.RoleRepo;
import io.gaterrays.userservice.Repo.UserRepo;
import io.gaterrays.userservice.domain.Role;
import io.gaterrays.userservice.domain.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepo.findByUsername(username);
        if (user==null){
            log.error("usernameno no encontrado");
            throw new UsernameNotFoundException("usernameno no encontrado");

        }else {
            log.info("Username encontrado en la base de datos: {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(
                role -> {
                    authorities.add( new SimpleGrantedAuthority(role.getName()));
                });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        User user =userRepo.findByUsername(username);
        if(user !=null){
            Role role=roleRepo.findByName(roleName);
            if(role != null){
                if(!user.getRoles().contains(role)){
                    user.getRoles().add(role);
                }
            }
        }

    }

    @Override
    public User getUser(String userName) {
        return userRepo.findByUsername(userName);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public boolean findByUsername(String username) {

      if(userRepo.findByUsername(username) != null){
          return true;
      }
      return false;
    }

    @Override
    public User finById(Long idUser) {
        return userRepo.findById(idUser).map(
                user -> {
                    return user;
                }
        ).orElseThrow(null);
    }



}
