package com.api.villagedevin.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.villagedevin.model.persistence.User;
import com.api.villagedevin.model.repository.UserRepository;
import com.api.villagedevin.model.transport.UserDTO;

public class UserServiceTest {

	private UserService userService;

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	public void beforeEach() {
		userRepository = Mockito.mock(UserRepository.class);
		passwordEncoder = Mockito.mock(PasswordEncoder.class);

		this.userService = new UserService(userRepository, passwordEncoder);
	}

	@Test
	void getUsersByEmail() {

		String email = "rafavilvert@";

		List<User> usersList = new ArrayList<>();

		usersList.add(new User(email, "1234"));

		when(userRepository.findByEmailContaining(email)).thenReturn(usersList);

		List<UserDTO> userDTO = this.userService.getUsersByEmail(email);

		verify(userRepository, atLeastOnce()).findByEmailContaining(email);

		assertTrue(!userDTO.isEmpty());

	}

	@Test
	void getUsersByEmailNotFound() {

		List<User> usersList = new ArrayList<>();

		when(userRepository.findByEmailContaining("rafavilvert@")).thenReturn(usersList);
		List<UserDTO> userDTO = this.userService.getUsersByEmail("rafavilvert@");
		verify(userRepository, atLeastOnce()).findByEmailContaining("rafavilvert@");
		assertTrue(userDTO.isEmpty());

		when(userRepository.findByEmailContaining(null)).thenReturn(usersList);
		List<UserDTO> userDTO2 = this.userService.getUsersByEmail(null);
		verify(userRepository, atLeastOnce()).findByEmailContaining(null);
		assertTrue(userDTO2.isEmpty());
	}

	@Test
	void getUserByIdUserNotFound() {

		Exception exceptionUserNull = assertThrowsExactly(Exception.class, () -> userService.getUserById(null));
		assertEquals("Usuário não encontrado.", exceptionUserNull.getMessage());

	}

	@Test
	void getUserById() throws Exception {
		User user = new User();
		when(userRepository.findUserById(1)).thenReturn(user);

		User user2 = userService.getUserById(1);

		assertNotNull(user2);
	}

	@Test
	void listAllUsersNotFound() {

		List<User> userList = new ArrayList<User>();

		when(userRepository.findAll()).thenReturn(userList);

		List<UserDTO> listAll = userService.listAll();
		assertTrue(listAll.isEmpty());

	}

	@Test
	void listAllUsers() {

		List<User> userList = new ArrayList<User>();

		User user = new User();
		userList.add(user);

		when(userRepository.findAll()).thenReturn(userList);
		List<UserDTO> listAll = userService.listAll();
		assertTrue(!listAll.isEmpty());

	}

	@Test
	void createUserExpecteErrors() {
		User user = new User();
		IllegalArgumentException exceptionUserNull = assertThrowsExactly(IllegalArgumentException.class,
				() -> userService.create(null));
		assertEquals("Usuário Nulo", exceptionUserNull.getMessage());

		IllegalArgumentException exceptionEmail = assertThrowsExactly(IllegalArgumentException.class,
				() -> userService.create(user));
		assertEquals("Email está nulo!", exceptionEmail.getMessage());

		user.setEmail("");
		IllegalArgumentException exceptionEmailEmpyt = assertThrowsExactly(IllegalArgumentException.class,
				() -> userService.create(user));
		assertEquals("Email inválido", exceptionEmailEmpyt.getMessage());

		user.setEmail("rafa@teste.com");
		IllegalArgumentException exceptionPassqordNull = assertThrowsExactly(IllegalArgumentException.class,
				() -> userService.create(user));
		assertEquals("Password está nulo!", exceptionPassqordNull.getMessage());
		user.setPassword("1234");
		IllegalArgumentException exceptionPasswordInvalid = assertThrowsExactly(IllegalArgumentException.class,
				() -> userService.create(user));

		assertEquals("Senha inválida", exceptionPasswordInvalid.getMessage());

	}

	@Test
	void createUserSuccesses() {
		User user = new User();

		user.setEmail("rafa@teste.com");
		user.setPassword("Spring@1");
		user.setRoles(List.of("USER"));
		when(passwordEncoder.encode(user.getPassword())).thenReturn("Simulating");
		User user2 = userService.create(user);
		verify(userRepository, atLeastOnce()).save(user);

		assertNotNull(user2);
		assertEquals("Simulating", user2.getPassword());

	}

	@Test
	void updateUser() {
		User user = new User();
		when(userRepository.save(user)).thenReturn(user);
		assertDoesNotThrow(() -> userService.update(user));
	}

	@Test
	public void testLoadUserByEmailSuccessfully() throws Exception {
		User user = new User("test@test.com", "123", List.of("USER"));
		UserDTO userDTO = new UserDTO(user);
		when(userRepository.findUserByEmail("test@test.com")).thenReturn(user);
		final UserDetails userDetails = userService.loadUserByUsername("test@test.com");

		assertNotNull(userDetails);
		assertEquals(userDTO.getEmail(), userDetails.getUsername());
	}

	@Test
	public void testLoadUserByEmailException() throws Exception {

		when(userRepository.findUserByEmail("test@test.com")).thenThrow(UsernameNotFoundException.class);
		assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test@test.com"));
	}

//	@Test
//	void isValidDataOnUserAttribute() {
//
//		User user = new User();
//		user.setEmail("1234");
//		user.setPassword("0123");
//
//		boolean validUsername = ValidationUtil.isValidUsername(user.getEmail());
//		assertFalse(validUsername);
//
//		user.setEmail("rafa@teste.com");
//		validUsername = ValidationUtil.isValidUsername(user.getEmail());
//		assertTrue(validUsername);
//
//		boolean validPassword = ValidationUtil.isValidPassword(user.getPassword());
//		assertFalse(validPassword);
//
//		user.setPassword("Spring@12");
//		validPassword = ValidationUtil.isValidPassword(user.getPassword());
//		assertTrue(validPassword);
//
//	}

}
