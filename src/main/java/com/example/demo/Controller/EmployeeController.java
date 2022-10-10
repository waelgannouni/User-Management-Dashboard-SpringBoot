package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1")
public class EmployeeController {
	
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/Employees")
	public List <Employee> getAllEmployees(){

		return employeeRepository.findAll();
	}
		
// hors syntaxe		
		
		@GetMapping("/Employees/{id}")
		public ResponseEntity <Employee> getEmployeeById(@PathVariable (value="id") Long employeeId)
				throws ResourceNotFoundException{  
			
		      Employee employee= employeeRepository.findById(employeeId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not Found for this id :: " +employeeId));
			return  ResponseEntity.ok().body(employee);	
	}
		@PostMapping("/Employees")
	    public Employee createEmployee(@RequestBody Employee employee) {
	        return employeeRepository.save(employee);
	    }
		 @PutMapping("/Employees/{id}")
		    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
		         @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		        Employee employee = employeeRepository.findById(employeeId)
		        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		        employee.setemail(employeeDetails.getemail());
		        employee.setLastName(employeeDetails.getLastName());
		        employee.setFirstName(employeeDetails.getFirstName());
		        final Employee updatedEmployee = employeeRepository.save(employee);
		        return ResponseEntity.ok(updatedEmployee);
		    }

		    @DeleteMapping("/Employees/{id}")
		    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
		         throws ResourceNotFoundException {
		        Employee employee = employeeRepository.findById(employeeId)
		       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		        employeeRepository.delete(employee);
		        Map<String, Boolean> response = new HashMap<>();
		        response.put("deleted", Boolean.TRUE);
		        return response;
		    }
		

}
