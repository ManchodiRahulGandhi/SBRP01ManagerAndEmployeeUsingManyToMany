package com.nt.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.model.Employee;
import com.nt.model.Role;
import com.nt.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerService mService;
	@GetMapping("/home")
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("rahul", HttpStatus.OK);
	}
	
	@GetMapping("/getAllRoles")
	public ResponseEntity<String> getAllRoles(){
		return mService.getAllRolesDetails();
	}
	
	@GetMapping("/getOnlyRoles01")
	public ResponseEntity<String> getallName01(){
		//give only Name means roles of roles table using Streams
		return mService.getAllRolesDetails01();
	}
	@GetMapping("/getOnlyRoles")
	public ResponseEntity<String> getallName(){
		return mService.findallName();
	}
	/*
	 difference between '/getOnlyRoles' and '/getOnlyRoles01'?
	 -'/getOnlyRoles' uses @Query method to get data. it is not database Independent. only for sql
	 -'/getOnlyRoles01' uses Steams to get data. it is database Independent.
	 */
	
	// addiing and deleting role not possible nee to find answer
	 @DeleteMapping("/deleteRoleById/{id}")
	public ResponseEntity<String> deleteRoleById(@PathVariable Long id){
		return mService.deleteRoleById(id);
	}
	
	@PutMapping("/addRole")
	public ResponseEntity<String> addRole(@RequestBody Role r){
		return mService.addRole(r);
	}
	
	
	
	
	@GetMapping("/getAllEmployes")
	public  ResponseEntity<List<Employee>> getAllEmployes(){
		return mService.getAllEmployes();
	}
	
	@GetMapping("/getAllEmployes01")
	public  ResponseEntity<List<Employee>> getAllEmployes01(){
		return mService.getAllEmployes01();
	}
	@GetMapping("/getEmployee/{id}")
	public  ResponseEntity<String> getEmployesbyId(@PathVariable Long id){
		return mService.getEmployeeById(id);
	}
	@PostMapping("/addEmployee")
	public String addEmployee(@RequestBody Employee emp) {
	  	System.out.println("addingEmployee from EmployeeController"+emp.toString());
	   	return mService.addEmployee(emp);
	 }
	 
	 @DeleteMapping("/deleteEmployeeById/{id}")
	 public ResponseEntity<String> deleteEmployeByRole(@PathVariable Long id){
		 return mService.deleteEmployeeById(id);
	 }
	
}