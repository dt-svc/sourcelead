package com.bis.sourcelead.rest;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.web.AutoCompleteController;

@RestController
@RequestMapping("/EmployeesController")
public class EmployeesRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeesRestController.class);
	
	@Autowired
	public EmployeesService employeesService;
	
	@Autowired
	public UserService userService;
	
	@RequestMapping(value = "/addEmployee",  method = RequestMethod.GET)
	public @ResponseBody Employees addEmployee() throws Exception
	{	
		Employees employee  = new Employees();
		Employees  emp=	employeesService.addEmployee(employee);
		return emp;
	}
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.GET)
    public @ResponseBody  Employees updateEmployee() throws Exception
                    
    {
		    Employees employee = new Employees();
			Employees updateEmployee = employeesService.updateEmployee(employee);
            return updateEmployee;
    }
	@RequestMapping(value = "/deleteEmployee/{id}", method = RequestMethod.GET)
    public @ResponseBody  Boolean deleteEmployee(@PathVariable String id) throws Exception
                    
    {
		    Boolean flag = employeesService.deleteEmployee(id);
            return flag;
    }
	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)
    public @ResponseBody Collection<Employees> getAllEmployees() throws Exception
    {
              Collection<Employees> employeesList = employeesService.getAllEmployees();
              return employeesList;
    }
}
