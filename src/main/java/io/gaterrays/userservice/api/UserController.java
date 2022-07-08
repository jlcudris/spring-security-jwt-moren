package io.gaterrays.userservice.api;

import io.gaterrays.userservice.domain.Role;
import io.gaterrays.userservice.domain.User;
import io.gaterrays.userservice.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController @RequiredArgsConstructor

@RequestMapping("/users")
public class UserController {

@Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return  ResponseEntity.ok().body(userService.getUsers());
    }
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){

        User user=userService.finById(id);
        if(user == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user){
        if(userService.findByUsername(user.getUsername())){
            return new  ResponseEntity<>(Collections.singletonMap("response","Este username ya esta registrado"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return new ResponseEntity<>(userService.saveRole(role), HttpStatus.CREATED);
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(),form.getRolename());
        return new ResponseEntity<>(Collections.singletonMap("response","rol agregado al usuario"),HttpStatus.OK);
    }

}
@Data
class RoleToUserForm{
    private String username;
    private String rolename;
}
