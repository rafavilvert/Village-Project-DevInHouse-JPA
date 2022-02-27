package com.api.villagedevin.model.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.villagedevin.model.persistence.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

//	@Query(value = "SELECT * FROM \"user\" WHERE email LIKE %:email%", nativeQuery = true)
	public Iterable<User> findByEmailContaining(String email);

	@Query(value = "SELECT u FROM User u WHERE u.id = :id")
	public User findUserById(Integer id);

	@Query(value = "SELECT roles FROM user_roles WHERE user_id = ?", nativeQuery = true)
	public List<String> fingRolesByUserId(Integer userId);

	@Query(value = "SELECT * FROM user_roles WHERE user_id = ?", nativeQuery = true)
	public Set<String> fingUserRolesById(Integer userId);

	@Query(value = "SELECT * FROM \"user\" WHERE email LIKE %:email%", nativeQuery = true)
	public User findUserByEmail(String email);

}
