package com.api.villagedevin.model.transport;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.api.villagedevin.model.persistence.Citizen;
import com.api.villagedevin.model.persistence.User;

public class CreateCitizenAndUserDTO {

	private Integer id;
	private String name;
	private String lastname;
	private String cpf;
	private Double income;
	private Double expense;
	private LocalDate birthDate;

	private String email;
	private String password;
	private List<String> roles;

	public CreateCitizenAndUserDTO() {

	}

	public CreateCitizenAndUserDTO(String name, String lastname, String cpf, Double income, Double expense,
			LocalDate birthDate, String email, String password, List<String> roles) {
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.income = income;
		this.expense = expense;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public CreateCitizenAndUserDTO(Citizen citizen, User user) {
		this.name = citizen.getName();
		this.lastname = citizen.getLastname();
		this.cpf = citizen.getCPF();
		this.income = citizen.getIncome();
		this.expense = citizen.getExpense();
		this.birthDate = citizen.getDataNascimento();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();

	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getExpense() {
		return expense;
	}

	public void setExpense(Double expense) {
		this.expense = expense;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "CreateCitizenAndUserDTO [name=" + name + ", lastname=" + lastname + ", cpf=" + cpf + ", income="
				+ income + ", expense=" + expense + ", birthDate=" + birthDate + ", email=" + email + ", password="
				+ password + ", roles=" + roles + "]";
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
		CreateCitizenAndUserDTO other = (CreateCitizenAndUserDTO) obj;
		return Objects.equals(email, other.email);
	}

}
