package com.api.villagedevin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.villagedevin.model.persistence.Citizen;
import com.api.villagedevin.model.persistence.User;
import com.api.villagedevin.model.repository.CitizenRepository;
import com.api.villagedevin.model.transport.CitizenDTO;
import com.api.villagedevin.model.transport.CreateCitizenAndUserDTO;
import com.api.villagedevin.model.transport.VillageReportDTO;

@Service
public class CitizenService {
	
	@Value("${VILLAGE_BUDGET}")
	private Double TotalRevenueVillage;

	private final CitizenRepository citizenRepository;

	private final UserService userService;

	public CitizenService(CitizenRepository citizenRepository, UserService userService) {
		this.citizenRepository = citizenRepository;
		this.userService = userService;
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

	public ResponseEntity<HttpStatus> create(CreateCitizenAndUserDTO createCitizenAndUserDTO) {
		Citizen citizen = new Citizen();
		citizen.setName(createCitizenAndUserDTO.getName());
		citizen.setLastname(createCitizenAndUserDTO.getLastname());
		citizen.setCPF(createCitizenAndUserDTO.getCpf());
		citizen.setIncome(createCitizenAndUserDTO.getIncome());
		citizen.setExpense(createCitizenAndUserDTO.getExpense());
		citizen.setDataNascimento(createCitizenAndUserDTO.getBirthDate());

		this.citizenRepository.save(citizen);

		User user = new User();
		user.setEmail(createCitizenAndUserDTO.getEmail());
		user.setPassword(createCitizenAndUserDTO.getPassword());
		user.setRoles(createCitizenAndUserDTO.getRoles());
		user.setCitizen(citizen);

		this.userService.create(user);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	public ResponseEntity<HttpStatus> delete(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Erro ID vazio");
		}
		this.citizenRepository.deleteById(id);
		return ResponseEntity.accepted().build();
	}

	public VillageReportDTO getReport() throws IllegalAccessException {
		Double revenue = 0.0;
		Double expense = 0.0;
		Double mostExpenseCitizen = 0.0;

		List<CitizenDTO> citizens = this.listAll();
		VillageReportDTO villageReport = new VillageReportDTO();
		if (citizens == null || citizens.isEmpty()) {
			throw new IllegalAccessException("O relatório está nulo!");
		}

		for (CitizenDTO citizen : citizens) {

			if (citizen.getExpense() > mostExpenseCitizen) {
				villageReport.setMostExpenseCitizen(citizen.getExpense()); // Cidadao que gasta mais
			}

			revenue += citizen.getIncome();
			expense += citizen.getExpense();
			villageReport.setTotalRevenue(revenue); // Total de ganhos dos cidadãos
			villageReport.setDifferenceRevenueAndExpense(TotalRevenueVillage - expense); // Total de gastos dos Cidadãos

			villageReport.setBudget(TotalRevenueVillage); // Orçamento

		}

		villageReport.setBudget(TotalRevenueVillage);

		System.out.println(villageReport.getMostExpenseCitizen());
		return villageReport;

	}

}
