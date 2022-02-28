package com.api.villagedevin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.api.villagedevin.utils.ValidationUtil;

@Service
public class CitizenService {

	private final Logger LOG = LogManager.getLogger(CitizenService.class);

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
		this.LOG.info("Buscando cidadãos no Banco...");
		Iterable<Citizen> iterable = this.citizenRepository.findAll();
		iterable.forEach(citizen -> citizensDTO.add(new CitizenDTO(citizen)));

		if (citizensDTO == null || citizensDTO.isEmpty()) {
			this.LOG.info("Nenhum cidadão encontrado no Banco...");
			return citizensDTO;
		} else {
			this.LOG.info("Cidadãos encontrados com sucesso!");
			return citizensDTO;
		}

	}

	public List<String> listCitizensNames() throws Exception {
		List<String> citizensName = new ArrayList<>();
		this.LOG.info("Buscando nomes no Banco.");
		List<String> listCitizensName = this.citizenRepository.listCitizensName();
		citizensName.addAll(listCitizensName);

		if (citizensName == null || citizensName.isEmpty()) {
			this.LOG.info("Nenhum nome encontrado.");
			throw new Exception("Cidadão está nulo.");
		}

		this.LOG.info("Nomes encontrados com sucesso!");
		return citizensName;

	}

	public Citizen listCitizens(Integer id) throws Exception {
		this.LOG.info("Buscando cidadão no Banco...");

		Citizen citizen = this.citizenRepository.findAllById(id);

		if (citizen == null) {
			this.LOG.info("Nenhum cidadão encontrado.");
			throw new Exception("Cidadão está nulo.");
		}

		this.LOG.info("Cidadão encontrado com sucesso");
		return citizen;

	}

	public CitizenDTO getById(Integer id) throws Exception {
		this.LOG.info("Buscando cidadão no Banco...");
		Citizen citizen = this.citizenRepository.findAllById(id);

		if (citizen == null) {
			this.LOG.info("Nenhum cidadão encontrado.");
			throw new Exception("Cidadão não encontrado.");
		}

		this.LOG.info("Cidadão encontrado com sucesso!");
		return new CitizenDTO(citizen);

	}

	public List<CitizenDTO> getCitizensByName(String name) throws Exception {
		this.LOG.info("Buscando cidadão por nome no Banco...");
		List<CitizenDTO> citizensDTO = new ArrayList<>();
		Iterable<Citizen> itarable = this.citizenRepository.findByName(name);
		itarable.forEach(citizen -> citizensDTO.add(new CitizenDTO(citizen)));

		if (citizensDTO == null || citizensDTO.isEmpty()) {
			this.LOG.info("Nenhum cidadão encontrado no Banco.");
			throw new Exception("Cidadão está nulo.");
		}

		this.LOG.info("Cidadãos encontrados com sucesso!");
		return citizensDTO;

	}

	public List<CitizenDTO> getCitizensByMonth(Integer month) throws Exception {
		List<CitizenDTO> citizensDTO = new ArrayList<>();
		this.LOG.info("Buscando cidadão por mês no Banco...");
		List<Citizen> citizens = this.citizenRepository.findByMonth(month);

		if (citizens == null || citizens.isEmpty()) {
			this.LOG.info("Nenhum cidadão encontrado no Banco.");
			throw new Exception("Cidadão está nulo.");
		}

		for (Citizen citizen : citizens) {
			CitizenDTO dto = new CitizenDTO(citizen.getName(), citizen.getLastname(), citizen.getCPF(),
					citizen.getIncome(), citizen.getExpense(), citizen.getDataNascimento());
			citizensDTO.add(dto);
		}

		this.LOG.info("Cidadão(s) encontrado com sucesso!");

		return citizensDTO;
	}

	public List<CitizenDTO> getCitizensByAge(Integer age) throws Exception {
		List<CitizenDTO> citizensDTO = new ArrayList<>();
		this.LOG.info("Buscando cidadão por idade no Banco...");
		List<Citizen> citizens = this.citizenRepository.findByAge(age);

		if (citizens == null || citizens.isEmpty()) {
			this.LOG.info("Nenhum cidadão encontrado.");
			throw new Exception("Cidadão está nulo.");
		}

		for (Citizen citizen : citizens) {
			CitizenDTO dto = new CitizenDTO(citizen.getName(), citizen.getLastname(), citizen.getCPF(),
					citizen.getIncome(), citizen.getExpense(), citizen.getDataNascimento());
			citizensDTO.add(dto);
		}

		this.LOG.info("Cidadãos encontrados com sucesso!");
		return citizensDTO;
	}

	public ResponseEntity<HttpStatus> create(CreateCitizenAndUserDTO createCitizenAndUserDTO)
			throws IllegalAccessException {

		if (createCitizenAndUserDTO == null) {
			throw new IllegalAccessException("O cidadão está nulo!");
		}

		if (!ValidationUtil.isValidName(createCitizenAndUserDTO.getName())) {
			throw new IllegalAccessException("Nome inválido");
		}

		if (!ValidationUtil.isValidName(createCitizenAndUserDTO.getLastname())) {
			throw new IllegalAccessException("Sobrenome inválido");
		}

		if (!ValidationUtil.isValidCPF(createCitizenAndUserDTO.getCpf())) {
			throw new IllegalAccessException("CPF inválido");
		}

		Citizen citizen = new Citizen();
		citizen.setName(createCitizenAndUserDTO.getName());
		citizen.setLastname(createCitizenAndUserDTO.getLastname());
		citizen.setCPF(createCitizenAndUserDTO.getCpf());
		citizen.setIncome(createCitizenAndUserDTO.getIncome());
		citizen.setExpense(createCitizenAndUserDTO.getExpense());
		citizen.setDataNascimento(createCitizenAndUserDTO.getBirthDate());

		this.LOG.info("Salvando cidadão no Banco...");
		this.citizenRepository.save(citizen);
		this.LOG.info("Cidadão salvo com sucesso!");

		User user = new User();
		user.setEmail(createCitizenAndUserDTO.getEmail());
		user.setPassword(createCitizenAndUserDTO.getPassword());
		user.setRoles(createCitizenAndUserDTO.getRoles());
		user.setCitizen(citizen);

		this.LOG.info("Salvando usuário no Banco...");
		this.userService.create(user);
		this.LOG.info("Usuário salvo com sucesso!");

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	public ResponseEntity<HttpStatus> delete(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Erro ID vazio");
		}
		this.citizenRepository.deleteById(id);
		this.LOG.info("Cidadão deletado com sucesso...");
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

		this.LOG.info("Relatório gerado com sucesso!");
		return villageReport;

	}

}
