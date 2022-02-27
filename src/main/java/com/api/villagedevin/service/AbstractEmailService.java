package com.api.villagedevin.service;

import java.sql.Date;

import org.springframework.mail.SimpleMailMessage;

import com.api.villagedevin.model.persistence.User;

public abstract class AbstractEmailService implements EmailService {

	private String sender;

	public AbstractEmailService(String sender) {
		this.sender = sender;
	}

	@Override
	public void sendNewPassword(User user, String newPass) {
		SimpleMailMessage sm = prepareSimpleMailMessage(user.getEmail(), "Nova senha: " + newPass, "Solicitação de nova senha");
		sendEmail(sm);
	}
	
	public void send(String email, String message, String subject) {
		SimpleMailMessage sm = prepareSimpleMailMessage(email, message, subject);
		sendEmail(sm);
	}

	private SimpleMailMessage prepareSimpleMailMessage(String email, String message, String subject) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(email);
		sm.setFrom(sender);
		sm.setSubject(subject);
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(message);
		return sm;
	} 

}
