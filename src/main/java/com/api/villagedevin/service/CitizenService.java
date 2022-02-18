package com.api.villagedevin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.villagedevin.model.persistence.Citizen;
import com.api.villagedevin.model.repository.CitizenRepository;
import com.api.villagedevin.model.transport.CitizenDTO;

@Service
public class CitizenService {

	private final CitizenRepository citizenRepository;

	public CitizenService(CitizenRepository citizenRepository) {
		this.citizenRepository = citizenRepository;
	}

	public List<CitizenDTO> listAll() {
		List<CitizenDTO> citizensDTO = new ArrayList<>();
		Iterable<Citizen> iterable = this.citizenRepository.findAll();
		iterable.forEach(citizen -> citizensDTO.add(new CitizenDTO(citizen)));
		System.out.println(citizensDTO);
		return citizensDTO;
	}

	public List<String> listCitizensNames() {
		List<String> citizensName = new ArrayList<>();
		List<String> listCitizensName = this.citizenRepository.listCitizensName();
		citizensName.addAll(listCitizensName);
		return citizensName;
	}

	public Citizen listCitizens(Integer id) {

		Citizen citizen = this.citizenRepository.findAllById(id);

		return citizen;
	}

	public CitizenDTO getById(Integer id) {
		Citizen citizen = this.citizenRepository.findAllById(id);
		return new CitizenDTO(citizen);
	}

	public List<CitizenDTO> getCitizensByName(String name) {
		List<CitizenDTO> citizensDTO = new ArrayList<>();
		Iterable<Citizen> itarable = this.citizenRepository.findByName(name);
		itarable.forEach(citizen -> citizensDTO.add(new CitizenDTO(citizen)));
		return citizensDTO;
	}

	public List<CitizenDTO> getCitizensByMonth(Integer month) {
		List<CitizenDTO> citizensDTO = new ArrayList<>();
		List<Citizen> citizens = this.citizenRepository.findByMonth(month);
		for (Citizen citizen : citizens) {
			CitizenDTO dto = new CitizenDTO(citizen.getName(), citizen.getLastname(), citizen.getCPF(),
					citizen.getIncome(), citizen.getExpense(), citizen.getDataNascimento());
			citizensDTO.add(dto);
		}
		return citizensDTO;
	}

	public List<CitizenDTO> getCitizensByAge(Integer age) {
		List<CitizenDTO> citizensDTO = new ArrayList<>();
		List<Citizen> citizens = this.citizenRepository.findByAge(age);
		for (Citizen citizen : citizens) {
			CitizenDTO dto = new CitizenDTO(citizen.getName(), citizen.getLastname(), citizen.getCPF(),
					citizen.getIncome(), citizen.getExpense(), citizen.getDataNascimento());
			citizensDTO.add(dto);
		}
		return citizensDTO;
	}

	public ResponseEntity<HttpStatus> create(Citizen citizen) {
		this.citizenRepository.save(citizen);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	public ResponseEntity<HttpStatus> delete(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Erro ID vazio");
		}
		this.citizenRepository.deleteById(id);
		return ResponseEntity.accepted().build();
	}



}
