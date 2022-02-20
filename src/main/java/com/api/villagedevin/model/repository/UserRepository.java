package com.api.villagedevin.model.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.villagedevin.model.persistence.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM \"user\" WHERE email LIKE %:email%", nativeQuery = true)
	public Iterable<User> findByEmail(String email);
	
	@Query(value = "SELECT * FROM \"user\" WHERE id = ?", nativeQuery = true)
	public User findUserById(Integer id);
	
	@Query(value = "SELECT roles FROM user_roles WHERE user_id = ?", nativeQuery = true)
	public List<String> fingRolesByUserId(Integer userId);
	
	@Query(value = "SELECT * FROM user_roles WHERE user_id = ?", nativeQuery = true)
	public Set<String> fingUserRolesById(Integer userId);
	
	@Modifying
	@Query(value = "INSERT INTO user_roles (user_id, roles) VALUES(:user_id, :roles);", nativeQuery = true)
	@Transactional
	public void createRoles(@RequestBody Integer user_id, @RequestBody List<String> roles);
	
//	@Query(value = "INSERT INTO \"user\" (email, password) VALUES (?, ?)", nativeQuery = true)
//	public User create(String name, String password);
	
	

}
