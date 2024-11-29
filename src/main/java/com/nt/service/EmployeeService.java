package com.nt.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.model.Employee;
import com.nt.model.Role;
import com.nt.repo.EmployeeRepository;
import com.nt.repo.RoleRepository;

@Service
public class EmployeeService{
    @Autowired
    private EmployeeRepository empRepo;
    
    @Autowired
    private RoleRepository roleRepo;

   
    
    //this for save employe from exteranal
    public String addEmployee1(Employee emp) {
    	//emp.setPassword(pe.encode(emp.getPassword()));
    	String s= empRepo.save(emp).getName();
    	return s + " is save succefully";
    }
    
    //Adding new employee
    public String addEmployee(Employee emp) {
    	
    	
    	//chechiki weather employee register or not
    	if(empRepo.findByName(emp.getName()).isPresent()) {
    		return emp.getName()+":: is already present ";
    	}
        // Fetch roles from the database
        Set<Role> managedRoles = emp.getRole().stream()
                .map(role -> roleRepo.findByName(role.getName())
                        .orElse(roleRepo.save(role)))
                .collect(Collectors.toSet());
        

        // Set the managed roles back to the employee
        emp.setRole(managedRoles);
        System.out.println("Non ecripted password is  ::"+emp.getPassword());
       
        //emp.setPassword(pe.encode(emp.getPassword()));//it for encript the password
        System.out.println("Ecripted password is  ::"+emp.getPassword());
        // Persist the employee
        empRepo.save(emp);
        return emp.getId()+ "Employee added successfully!";
    }
    
   
	
	}


