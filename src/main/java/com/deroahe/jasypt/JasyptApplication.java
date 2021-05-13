package com.deroahe.jasypt;

import com.deroahe.jasypt.model.User;
import com.deroahe.jasypt.service.impl.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableEncryptableProperties
@Slf4j
public class JasyptApplication {

	public static void main(String[] args) {
		SpringApplication.run(JasyptApplication.class, args);
	}

	@Bean
	public CommandLineRunner setup(UserService userService) {
		return (args) -> {
			log.info("Generating sample data");
			userService.deleteAllUsers();
			List<String> users = Arrays.asList("Bob", "Peter", "Gus", "John", "David");
			users.forEach(user ->
					userService.saveUser(User.builder()
							.username(user)
							.password(LocalDateTime.now().toString())
							.build()) );

			userService.getAllUsers().forEach(user ->
					log.info("USER --> " + user.getUsername() + " ID: " + user.getId() + " PASSWORD: " + user.getPassword()));
		};
	}
}
