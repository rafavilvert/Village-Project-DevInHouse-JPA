package com.api.villagedevin.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private final Logger LOG = LogManager.getLogger(UserService.class);

	private final UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserDTO> listAll() {
		List<UserDTO> users = new ArrayList<>();
		this.LOG.info("Buscando Usuários no Banco...");
		Iterable<User> iterable = this.userRepository.findAll();
		iterable.forEach(all -> users.add(new UserDTO(all)));

		if (users == null || users.isEmpty()) {
			this.LOG.info("Nenhum Usuário encontrado.");
			return users;
		} else {
			this.LOG.info("Usuários encontrados com sucesso!");
			return users;
		}

	}

	public List<UserDTO> getUsersByEmail(String email) {
		List<UserDTO> userDTO = new ArrayList<>();
		this.LOG.info("Buscando Usuários no Banco por email");
		Iterable<User> iterable = this.userRepository.findByEmail(email);

		iterable.forEach(user -> userDTO.add(new UserDTO(user)));

		if (userDTO == null || userDTO.isEmpty()) {
			this.LOG.info("Nenhum Usuário encontrado.");
			return userDTO;
		} else {
			this.LOG.info("Usuário encontrado com sucesso!");
			return userDTO;
		}

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
		this.LOG.info("Atualizando usuário...");
		userRepository.save(user);
		this.LOG.info("Usuário atualizado.");
	}

	public ResponseEntity<HttpStatus> create(User user) {
		String passwordEnconde = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordEnconde);
		this.LOG.info("Salvando usuário no Banco...");

		if (user == null) {
			this.LOG.info("Problemas para salva usuário.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} else {
			this.userRepository.save(user);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}

	}

	public User getUserById(Integer id) {
		User user = userRepository.findUserById(id);
		this.LOG.info("Buscando Usuário no Banco por Id...");
		List<String> roles = userRepository.fingRolesByUserId(id);
		user.setRoles(roles);
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
