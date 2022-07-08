package io.gaterrays.userservice;

import io.gaterrays.userservice.domain.Role;
import io.gaterrays.userservice.domain.User;
import io.gaterrays.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_MANAGER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));
			userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null,"jonathan cudris","jhomtan","1234",new ArrayList<>()));
			userService.saveUser(new User(null,"maria florez","mp_florez","1234",new ArrayList<>()));
			userService.saveUser(new User(null,"cindy florez","cindyF","1234",new ArrayList<>()));
			userService.saveUser(new User(null,"martha peralta","martha","1234",new ArrayList<>()));

			userService.addRoleToUser("jhomtan","ROLE_SUPER_ADMIN");
			userService.addRoleToUser("jhomtan","ROLE_MANAGER");
			userService.addRoleToUser("jhomtan","ROLE_ADMIN");
			userService.addRoleToUser("mp_florez","ROLE_ADMIN");
			userService.addRoleToUser("cindyF","ROLE_MANAGER");
			userService.addRoleToUser("martha","ROLE_USER");
		};
	}

}
