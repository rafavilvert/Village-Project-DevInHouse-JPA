package com.api.villagedevin.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.villagedevin.model.persistence.Citizen;


@Repository
public interface CitizenRepository extends CrudRepository<Citizen, Integer> {

	@Query(value = "SELECT name FROM citizen", nativeQuery = true)
	public List<String> listCitizensName();

	public Citizen findAllById(Integer id);

	@Query(value = "SELECT * FROM citizen WHERE name LIKE %:name%", nativeQuery = true)
	public Iterable<Citizen> findByName(String name);

	@Query(value = "SELECT * FROM citizen WHERE date_part('month', (dataNascimento)) = ?", nativeQuery = true)
	public List<Citizen> findByMonth(Integer month);

	@Query(value = "SELECT * FROM citizen WHERE date_part('year', age(dataNascimento)) >= ?", nativeQuery = true)
	public List<Citizen> findByAge(Integer age);

}
