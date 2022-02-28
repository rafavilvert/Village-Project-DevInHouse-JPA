package com.api.villagedevin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.api.villagedevin.model.persistence.Citizen;
import com.api.villagedevin.model.repository.CitizenRepository;
import com.api.villagedevin.model.transport.CitizenDTO;
import com.api.villagedevin.model.transport.CreateCitizenAndUserDTO;

public class CitizenServiceTest {

	private CitizenService citizenService;
	private CitizenRepository citizenRepository;
	private UserService userService;

	@BeforeEach
	public void beforeEach() {
		userService = Mockito.mock(UserService.class);
		citizenRepository = Mockito.mock(CitizenRepository.class);

		this.citizenService = new CitizenService(citizenRepository, userService);
	}

	@Test
	void listAllCitizensNotFound() {

		List<Citizen> citizenList = new ArrayList<Citizen>();

		when(citizenRepository.findAll()).thenReturn(citizenList);

		List<CitizenDTO> listAll = citizenService.listAll();
		assertTrue(listAll.isEmpty());

	}

	@Test
	void listAllCitizens() {

		List<Citizen> citizenList = new ArrayList<Citizen>();

		Citizen citizen = new Citizen();
		citizenList.add(citizen);

		when(citizenRepository.findAll()).thenReturn(citizenList);
		List<CitizenDTO> listAll = citizenService.listAll();
		assertTrue(!listAll.isEmpty());

	}

	@Test
	void listCitizensNamesNull() {
		List<String> citizensName = new ArrayList<>();
		when(citizenRepository.listCitizensName()).thenReturn(citizensName);

		Exception exceptionCitizenrNull = assertThrowsExactly(Exception.class,
				() -> citizenService.listCitizensNames());
		assertEquals("Cidadão está nulo.", exceptionCitizenrNull.getMessage());
	}

	@Test
	void listCitizensNames() throws Exception {
		List<String> citizensName = new ArrayList<>();
		citizensName.add("Vilvert");

		when(citizenRepository.listCitizensName()).thenReturn(citizensName);
		List<String> listCitizensNames = citizenService.listCitizensNames();

		assertTrue(!listCitizensNames.isEmpty());
	}

	@Test
	void getCitizenByIdUserNotFound() {

		Exception exceptionCitizenrNull = assertThrowsExactly(Exception.class, () -> citizenService.getById(null));
		assertEquals("Cidadão não encontrado.", exceptionCitizenrNull.getMessage());

	}

	@Test
	void getCitizenById() throws Exception {
		Citizen citizen = new Citizen();
		when(citizenRepository.findAllById(1)).thenReturn(citizen);

		CitizenDTO citizenDTO = citizenService.getById(1);

		assertNotNull(citizenDTO);
	}

	@Test
	void getCitizensByNameNull() {
		Exception exceptionCitizenrNull = assertThrowsExactly(Exception.class,
				() -> citizenService.getCitizensByName(null));
		assertEquals("Cidadão está nulo.", exceptionCitizenrNull.getMessage());
	}

	@Test
	void getCitizensByName() throws Exception {

		List<Citizen> citizens2 = new ArrayList<>();
		citizens2.add(new Citizen());

		when(citizenRepository.findByName("nome")).thenReturn(citizens2);
		List<CitizenDTO> citizensByName = citizenService.getCitizensByName("nome");

		assertTrue(!citizensByName.isEmpty());

	}

	@Test
	void getCitizensByMonthCitizenNull() {
		Exception exceptionCitizenrNull = assertThrowsExactly(Exception.class,
				() -> citizenService.getCitizensByMonth(null));
		assertEquals("Cidadão está nulo.", exceptionCitizenrNull.getMessage());
	}

	@Test
	void getCitizensByMonth() throws Exception {

		List<Citizen> citizens = new ArrayList<>();
		citizens.add(new Citizen());

		when(citizenRepository.findByMonth(1)).thenReturn(citizens);
		List<CitizenDTO> citizensByMonth = citizenService.getCitizensByMonth(1);

		assertTrue(!citizensByMonth.isEmpty());

	}

	@Test
	void getCitizensByAgeCitizenNull() {
		Exception exceptionCitizenrNull = assertThrowsExactly(Exception.class,
				() -> citizenService.getCitizensByAge(null));
		assertEquals("Cidadão está nulo.", exceptionCitizenrNull.getMessage());
	}

	@Test
	void getCitizensByAge() throws Exception {

		List<Citizen> citizens = new ArrayList<>();
		citizens.add(new Citizen());

		when(citizenRepository.findByAge(1)).thenReturn(citizens);
		List<CitizenDTO> citizensByAge = citizenService.getCitizensByAge(1);

		assertTrue(!citizensByAge.isEmpty());

	}

	@Test
	void createCitizenExpecteErrors() {
		Citizen citizen = new Citizen();
		CreateCitizenAndUserDTO createCitizenAndUserDTO = new CreateCitizenAndUserDTO();
		IllegalArgumentException exceptionUserNull = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(null));
		assertEquals("O cidadão está nulo!", exceptionUserNull.getMessage());

		IllegalArgumentException exceptionEmail = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("Nome inválido", exceptionEmail.getMessage());

		createCitizenAndUserDTO.setName("");
		IllegalArgumentException exceptionEmailEmpyt = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("Nome inválido", exceptionEmailEmpyt.getMessage());

		createCitizenAndUserDTO.setName(" ");
		IllegalArgumentException exceptionEmailEmpyt1 = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("Nome inválido", exceptionEmailEmpyt1.getMessage());

		createCitizenAndUserDTO.setName("5");
		IllegalArgumentException exceptionEmailEmpyt2 = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("Nome inválido", exceptionEmailEmpyt2.getMessage());

		createCitizenAndUserDTO.setName("Vilvert");

		IllegalArgumentException exceptionlastname = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("Sobrenome inválido", exceptionlastname.getMessage());

		createCitizenAndUserDTO.setLastname("");
		IllegalArgumentException exceptionlastname1 = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("Sobrenome inválido", exceptionlastname1.getMessage());

		createCitizenAndUserDTO.setLastname(" ");
		IllegalArgumentException exceptionlastname2 = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("Sobrenome inválido", exceptionlastname2.getMessage());

		createCitizenAndUserDTO.setLastname("5");
		IllegalArgumentException exceptionlastname3 = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("Sobrenome inválido", exceptionlastname3.getMessage());

		createCitizenAndUserDTO.setLastname("Rafael");

		IllegalArgumentException exceptionCPF1 = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("CPF inválido", exceptionCPF1.getMessage());

		createCitizenAndUserDTO.setCpf("cpf");
		IllegalArgumentException exceptionCPF2 = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("CPF inválido", exceptionCPF2.getMessage());

		createCitizenAndUserDTO.setCpf("123CPF5");
		IllegalArgumentException exceptionCPF3 = assertThrowsExactly(IllegalArgumentException.class,
				() -> citizenService.create(createCitizenAndUserDTO));
		assertEquals("CPF inválido", exceptionCPF3.getMessage());

		createCitizenAndUserDTO.setCpf("65478987456");


	}

	@Test
	void createCitizenSuccesses() {

		Citizen citizen = new Citizen();
		citizen.setName("Teste");
		citizen.setLastname("Silva");
		citizen.setCPF("65478987456");
		citizen.setExpense(3000.00);
		citizen.setIncome(10000.00);
		citizen.setDataNascimento(LocalDate.now());
		
		CreateCitizenAndUserDTO createCitizenAndUserDTO = new CreateCitizenAndUserDTO();
		createCitizenAndUserDTO.setName(citizen.getName());
		createCitizenAndUserDTO.setLastname(citizen.getLastname());
		createCitizenAndUserDTO.setCpf(citizen.getCPF());
		createCitizenAndUserDTO.setExpense(citizen.getExpense());
		createCitizenAndUserDTO.setIncome(citizen.getIncome());
		createCitizenAndUserDTO.setBirthDate(citizen.getDataNascimento());

		when(citizenRepository.save(citizen)).thenReturn(citizen);

		Citizen citizen2 = citizenService.create(createCitizenAndUserDTO);

		assertNotNull(citizen2);

	}

	@Test
	void deleteCitizen() {

	}

	@Test
	void getReport() {

	}

}
