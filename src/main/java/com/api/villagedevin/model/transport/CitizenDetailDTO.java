package com.api.villagedevin.model.transport;

import java.util.Date;

public class CitizenDetailDTO {

	private String name;
	private String lastname;
	private String cpf;
	private Double income;
	private Double expense;
	private Date birthDate;

	public CitizenDetailDTO(String name, String lastname, String cpf, Double income, Double expense, Date birthDate) {
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.income = income;
		this.expense = expense;
		this.birthDate = birthDate;
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

	public Date getDataNascimento() {
		return birthDate;
	}

	public void setDataNascimento(Date birthDate) {
		this.birthDate = birthDate;
	}

}
