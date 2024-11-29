package com.nt.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	// Custom query to fetch all 'name' values
    @Query("SELECT r.name FROM Role r")
	public List<String> findAllName();
    public Optional<Role> findByName(String name);

}
