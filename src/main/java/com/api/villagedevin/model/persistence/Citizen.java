package com.api.villagedevin.model.persistence;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.api.villagedevin.model.transport.CitizenDTO;

@Entity
@Table(name = "citizen")
public class Citizen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String lastname;

	@Column(unique = true, nullable = false)
	private String CPF;

	@Column(nullable = false)
	private Double income;

	@Column(nullable = false)
	private Double expense;

	@Column(name = "datanascimento", nullable = false)
	private LocalDate dataNascimento;
	
	@OneToOne(mappedBy = "citizen")
//	@OneToOne()
//	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public Citizen() {
	}

	public Citizen(CitizenDTO citizenDTO) {
		this.name = citizenDTO.getName();
		this.lastname = citizenDTO.getLastname();
		this.CPF = citizenDTO.getCpf();
		this.income = citizenDTO.getIncome();
		this.expense = citizenDTO.getExpense();
		this.dataNascimento = citizenDTO.getDataNascimento();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Citizen [id=" + id + ", name=" + name + ", lastname=" + lastname + ", CPF=" + CPF + ", income=" + income
				+ ", expense=" + expense + ", dataNascimento=" + dataNascimento + "]";
	}

}
