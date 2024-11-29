package com.nt.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nt.model.Employee;
import com.nt.model.Role;
import com.nt.repo.EmployeeRepository;
import com.nt.repo.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class ManagerService {

	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	//Getting allRoles in Role Table
	public ResponseEntity<String> getAllRolesDetails(){
		List<Role> roles=roleRepo.findAll();
		//System.out.println("role are  ::"+roles.toString());
		if(roles.isEmpty() || roles== null) {
			return new ResponseEntity<String>("Currently no roles created.. /n please create roles",HttpStatus.OK);
		}
		return new ResponseEntity<String>(roles.toString(),HttpStatus.OK);
	}
	
	public ResponseEntity<String> getAllRolesDetails01(){
		List<Role> roles=roleRepo.findAll();
		//System.out.println("role are  ::"+roles.toString());
		if(roles.isEmpty() || roles== null) {
			return new ResponseEntity<String>("Currently no roles created.. /n please create roles",HttpStatus.OK);
		}
		//Steams  -- map() uses function-> funtion means with param and return type
		List<String> nameList=roles.stream().map((p)->p.getName()).collect(Collectors.toList());
		
		return new ResponseEntity<String>(nameList.toString(),HttpStatus.OK);
	}
	
	public ResponseEntity<String> findallName(){
		List<String> nameList=roleRepo.findAllName();
		return new ResponseEntity<String>(nameList.toString(),HttpStatus.OK);
	}
	
	
	public ResponseEntity<String> deleteRoleById(Long id){
		String msg="role not found";
		var opt=roleRepo.findById(id);
		if(opt.isPresent()){
			roleRepo.deleteById(id);
			msg=opt.get().getName()+"  is succesfully deleted";
		}
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	 
	public ResponseEntity<String> deleteRoleById01(Long id){
		String msg=roleRepo.findById(id).map(p->{
			roleRepo.delete(p);
			return p.getName()+"  is succefully deleted";
		}).orElse("role not found");
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	
	public ResponseEntity<String> addRole(Role r){
		var emp=r.getEmploye();
		if(!emp.isEmpty()) {
			
		}
		roleRepo.save(r);
		return new ResponseEntity<String>(r.getName()+"  role is register sucessfully", HttpStatus.ACCEPTED);
	}
	

	
	//Employee table operations
	public ResponseEntity<List<Employee>> getAllEmployes(){
		//java 10 feature var can use in method level. here var means List<Employee>
		var empList=empRepo.findAll(); 
		
		//here we getting password of Employee to UI. so, this not recommanded
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}
	
	public ResponseEntity<List<Employee>> getAllEmployes01(){
		//java 10 feature var can use in method level. here var means List<Employee>
		var empList=empRepo.findAll(); 
		
		/*forEach is using consumer. consumer means with param and with void return type.
		  here we are hiding password from manager/ UI.
		  if we set password as null. there is a chance to getting of java.lang.NullPointerException.
		  for avaiding java.lang.NullPointerException we using '-1'
		*/
			empList.stream().forEach(emp->{
			emp.setPassword("-1");
			System.out.println(emp);
		});
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}
	
	//Adding new employee with out cascade = CascadeType.PERSIST/*
	@Transactional
    public String addEmployee1(Employee emp) {
    	
    	
    	/*//checking weather employee register or not
    	 //checking name and mail two time. it send request database two times
    	if(empRepo.findByName(emp.getName()).isPresent()) {
    		return emp.getName()+" :: is already present ";
    	}
    	
    	if (empRepo.findByEmail(emp.getName()).isPresent()) {
            return emp.getEmail() + "  :: is already present";
        }
    	*/
		
		/*
		 //It will check Name and Email at a time
		 if (empRepo.findByNameOrEmail(emp.getName(), emp.getEmail()).isPresent()) {
		    return "Employee with name or email already exists.";
		}*/
		//checking name and mail two time. it send request database one time
		var optEm1=empRepo.findByName(emp.getName());
		 if(optEm1.isPresent()) {
			 var emp1=optEm1.get();
			 if(emp1.getName().equalsIgnoreCase(emp.getName())){
				 return emp1.getName()+"  is already Registered";
			 }
			 else if(emp1.getEmail().equalsIgnoreCase(emp.getEmail())) {
				 return emp1.getEmail()+"  is already Registered";
			 }
		 }
		
    	Set<Role> managedRoles=null;
    	//There is chance of emp.getRole()=null
    	if(emp.getRole()!= null) {
           managedRoles = emp.getRole().stream()
                .map(role -> roleRepo.findByName(role.getName())
                        .orElseGet(() -> roleRepo.save(role)))
                .collect(Collectors.toSet());
    	}
        
        // Set the managed roles back to the employee
        emp.setRole(managedRoles);
        System.out.println("Non ecripted password is  ::"+emp.getPassword());
        //emp.setPassword(pe.encode(emp.getPassword()));//it for encript the password
        System.out.println("Ecripted password is  ::"+emp.getPassword());
        // Adding employee into DB table
        empRepo.save(emp);
        return emp.getId()+ " :: Employee added successfully!";
    }
	
	//Adding new employee with cascade = CascadeType.PERSIST/
	// Something is error
	 public String addEmployee(Employee emp) {
	    	
	    	
	    	//chechiki weather employee register or not
	    	if(empRepo.findByName(emp.getName()).isPresent()) {
	    		return emp.getName()+" :: is already present ";
	    	}
	    	
	    	if (empRepo.findByName(emp.getName()).isPresent()) {
	            return emp.getEmail() + "  :: is already present";
	        }

	        System.out.println("Non ecripted password is  ::"+emp.getPassword());
	        //emp.setPassword(pe.encode(emp.getPassword()));//it for encript the password
	        System.out.println("Ecripted password is  ::"+emp.getPassword());
	        // Adding employee into DB table
	        empRepo.save(emp);
	        return emp.getId()+ " :: Employee added successfully!";
	    }
    
	 public ResponseEntity<String> getEmployeeById(Long id){
		 var emp = empRepo.findById(id)
				    .map(p -> {
				    	p.setPassword("-1");
				    	return p.toString();
				    	})
				    .orElse("User Not Found");
		 System.out.println(emp);
		 return new ResponseEntity<String>(emp,HttpStatus.ACCEPTED);
	 }
	 
	public ResponseEntity<String> deleteEmployeeById1(Long id){
		//this method not effect the rbac01employee_roles.
		//If we use  cascade = CascadeType.ALL-> if we delete employee then Role alse delete. not Good practies
		
		/*
		 empRepo.findById(id):Returns an Optional<Employee>.
		If an employee with the given id is found, it contains the employee (p). Otherwise, it is empty.
		 */
		 String msg=empRepo.findById(id).map(p->{
			empRepo.delete(p);
			return p.getName()+"  is succefully deleted";
		}).orElse("Employee not found");
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<String> deleteEmployeeById(Long id){
		String msg=empRepo.findById(id).map(e->{
			/*
			 
			 e.getRole().stream().forEach(r->{
				roleRepo.delete(r);//intracting db multiple time base on number of roles
			});*/
			/*
			 //In below two case we have chance to get java.lang.NullPointerException
			 if(!e.getRole().equals(null)) {
				roleRepo.deleteAll(e.getRole());
			}
			if(!e.getRole().isEmpty()) {
				roleRepo.deleteAll(e.getRole());
			}
			if(emp.getRole() != null) {
				//no chance to get java.lang.NullPointerException
			}
			*/
			// Better to take both 
			if(e.getRole() != null && !e.getRole().isEmpty()) {
				roleRepo.deleteAll(e.getRole());
			}
			empRepo.delete(e);
			return e.getName()+"  is succefully deleted";
		}).orElse("Employee not found");
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
}
