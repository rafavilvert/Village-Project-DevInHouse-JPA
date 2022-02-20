package com.api.villagedevin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.villagedevin.model.persistence.User;
import com.api.villagedevin.model.repository.UserRepository;
import com.api.villagedevin.model.transport.UserDTO;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserDTO> listAll() {
		List<UserDTO> users = new ArrayList<>();
		Iterable<User> iterable = this.userRepository.findAll();
		iterable.forEach(all -> users.add(new UserDTO(all)));
		System.out.println(users);
		return users;
	}

	public List<UserDTO> getUsersByEmail(String email) {
		List<UserDTO> userDTO = new ArrayList<>();
		Iterable<User> iterable = this.userRepository.findByEmail(email);

		iterable.forEach(user -> userDTO.add(new UserDTO(user)));

		return userDTO;
	}
	
	public ResponseEntity<HttpStatus> createroles(List<String> roles) {
		
		User user = this.getUserById(1);

		this.userRepository.createRoles(user.getId(), roles);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	public ResponseEntity<HttpStatus> create(User user) {

		this.userRepository.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	public User getUserById(Integer id) {
		User user = userRepository.findUserById(id);
		List<String> roles = userRepository.fingRolesByUserId(id);
		user.setRoles(roles);
		System.out.println(user.getRoles());		
		System.out.println(user.getCitizen());		
		return userRepository.findUserById(id);
	}

//	public ResponseEntity<HttpStatus> create(User user) {
//
//		String encode = passwordEncoder.encode(user.getPassword());
//		user.setPassword(encode);
//
//		this.userRepository.save(user);
//
//		return ResponseEntity.status(HttpStatus.CREATED).build();
//	}
//
//	public User getUser(String email) {
//		return userRepository.findUserByEmail(email);
//	}

}
