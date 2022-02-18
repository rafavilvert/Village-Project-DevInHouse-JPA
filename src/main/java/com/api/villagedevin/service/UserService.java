package com.api.villagedevin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.villagedevin.model.persistence.User;
import com.api.villagedevin.model.persistence.UserSpringSecurity;
import com.api.villagedevin.model.repository.UserRepository;
import com.api.villagedevin.model.transport.UserDTO;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
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

	public ResponseEntity<HttpStatus> create(User user) {
		
		String encode = passwordEncoder.encode(user.getPassword());
		user.setPassword(encode);
		
		this.userRepository.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public static UserSpringSecurity authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return new UserSpringSecurity((String) authentication.getPrincipal(), null, new ArrayList<>());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = getUser(username);
			return new UserSpringSecurity(user.getEmail(), user.getPassword(), user.getRoles());
		} catch (UsernameNotFoundException e) {

			throw new UsernameNotFoundException(username);
		}
	}

	public User getUser(String email) {
		return userRepository.findUserByEmail(email);
	}

}
