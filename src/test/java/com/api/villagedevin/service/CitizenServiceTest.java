package com.api.villagedevin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.api.villagedevin.model.persistence.Citizen;
import com.api.villagedevin.model.repository.CitizenRepository;
import com.api.villagedevin.model.transport.CitizenDTO;

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
		
		Exception exceptionCitizenrNull = assertThrowsExactly(Exception.class, () -> citizenService.listCitizensNames());
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
	void createCitizenNull() {

	}

	@Test
	void createCitizen() {

	}

	@Test
	void deleteCitizen() {

	}

	@Test
	void getReport() {

	}

}
