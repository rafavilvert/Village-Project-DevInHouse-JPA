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
@RequestMapping("/users")
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
	public User getUserById(@PathVariable("id") Integer id) {
		return userService.getUserById(id);
	}
	
	@PostMapping("/create")
	public ResponseEntity<HttpStatus> create(@RequestBody User user) {
		return this.userService.create(user);
	}
	
	@PostMapping("/create-roles")
	public ResponseEntity<HttpStatus> create(@RequestBody List<String> roles) {
		return this.userService.createroles(roles);
	}

}
