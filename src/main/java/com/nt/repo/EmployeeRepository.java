package com.nt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.Employee;
import com.nt.model.Role;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	//Employee findByName(String username);
	Optional<Employee> findByName(String name);
	Optional<Employee> findByEmail(String name);
	Optional<Role> findByNameOrEmail(String name, String email);
	
}
