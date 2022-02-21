package com.api.villagedevin.service;

import org.springframework.mail.SimpleMailMessage;

import com.api.villagedevin.model.persistence.User;

public interface EmailService {

	void sendEmail(SimpleMailMessage message);

	void sendNewPassword(User user, String newPassword);

}
