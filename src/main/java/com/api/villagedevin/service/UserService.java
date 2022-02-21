package com.api.villagedevin.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

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

//	public ResponseEntity<HttpStatus> createroles(List<String> roles) {
//
//		User user = this.getUserById(1);
//
//		this.userRepository.createRoles(user.getId(), roles);
//
//		return ResponseEntity.status(HttpStatus.CREATED).build();
//	}
	
	@Transactional(value = TxType.REQUIRED)
	public void update(User user) {
		userRepository.save(user);
	}

	public ResponseEntity<HttpStatus> create(User user) {
		String passwordEnconde = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordEnconde);

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
			Set<String> roles = new HashSet<String>();
			for (String listRoles : user.getRoles()) {
				roles.add(listRoles);
			}
			return new UserSpringSecurity(user.getEmail(), user.getPassword(), roles);
		} catch (UsernameNotFoundException e) {

			throw new UsernameNotFoundException(username);
		}

	}

	public User getUser(String email) {
		return userRepository.findUserByEmail(email);
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
