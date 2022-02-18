package com.api.villagedevin.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.villagedevin.model.persistence.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM \"user\" WHERE email LIKE %:email%", nativeQuery = true)
	public Iterable<User> findByEmail(String email);
	
	@Query(value = "SELECT * FROM \"user\" WHERE email LIKE %:email%", nativeQuery = true)
	public User findUserByEmail(String email);
	
//	@Query(value = "INSERT INTO \"user\" (email, password) VALUES (?, ?)", nativeQuery = true)
//	public User create(String name, String password);
	
	

}
