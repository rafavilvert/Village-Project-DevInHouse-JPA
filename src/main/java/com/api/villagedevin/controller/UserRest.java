package com.api.villagedevin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.villagedevin.model.persistence.User;
import com.api.villagedevin.model.transport.UserDTO;
import com.api.villagedevin.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRest {

	private UserService userService;

	public UserRest(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/list-all")
	public List<UserDTO> listAll() {
		return userService.listAll();
	}

	@GetMapping("/filter-email")
	public List<UserDTO> getUsersByEmail(@RequestParam("email") String email) {
		return userService.getUsersByEmail(email);
	}

	@GetMapping("/list-user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}
	}

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> create(@RequestBody User user) throws IllegalAccessException {
		this.userService.create(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
