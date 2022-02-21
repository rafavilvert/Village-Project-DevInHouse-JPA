package com.api.villagedevin.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.villagedevin.model.persistence.UserSpringSecurity;
import com.api.villagedevin.model.transport.MailDTO;
import com.api.villagedevin.service.AuthService;
import com.api.villagedevin.service.UserService;
import com.api.villagedevin.utils.JWTUtil;

@RestController
@RequestMapping("/auth")
public class AuthRest {

	private JWTUtil jwtUtil;

	private AuthService authService;

	public AuthRest(JWTUtil jwtUtil, AuthService authService) {
		this.jwtUtil = jwtUtil;
		this.authService = authService;
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity userSpringSecurity = UserService.authenticated();
		String newToken = jwtUtil.generateToken(userSpringSecurity.getUsername());
		response.addHeader("Authorization", "Bearer " + newToken);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/forgot")
	public ResponseEntity<Void> forgot(@RequestBody MailDTO mail) {
		System.out.println(mail.getEmail());
		authService.sendNewPass(mail.getEmail());
		return ResponseEntity.noContent().build();
	}

}
