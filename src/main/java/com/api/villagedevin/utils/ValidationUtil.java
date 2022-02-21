package com.api.villagedevin.utils;

import java.util.regex.Pattern;

public class ValidationUtil {

	public static boolean isValidUsername(String username) {
		final Pattern pattern = Pattern.compile("^[a-z0-9.]+@[a-z0-9]+.[a-z]+.([a-z]+)?$");
		return pattern.matcher(username).matches();
	}

	public static boolean isValidPassword(String password) {
		final Pattern pattern = Pattern
				.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
		return pattern.matcher(password).matches();
	}

	public static boolean isValidCPF(final String cpf) {
		if (null == cpf) {
			return false;
		}

		final Pattern pattern = Pattern.compile("(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)");
		return pattern.matcher(cpf).matches();
	}

	public static boolean isValidName(final String name) {
		if (null == name || name.trim().isEmpty()) {
			return false;
		}

		final Pattern pattern = Pattern
				.compile("^[A-Za-zÀ-ÖØ-öø-ÿ]+(([',. -][A-Za-zÀ-ÖØ-öø-ÿ ])?[A-Za-zÀ-ÖØ-öø-ÿ]*)*$");
		return pattern.matcher(name).matches();
	}

}
