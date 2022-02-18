package com.api.villagedevin.model.transport;

import java.util.Objects;
import java.util.Set;

import com.api.villagedevin.model.persistence.User;

public class UserDTO {

	private String email;

	private String password;

	private Set<String> roles;

	public UserDTO() {
	}

	public UserDTO(String email) {
		this.email = email;
	}

	public UserDTO(User user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(email, other.email);
	}

}
