package net.javaguides.springbootthymleafcrudwebapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springbootthymleafcrudwebapp.model.Employee;
import net.javaguides.springbootthymleafcrudwebapp.service.EmployeeService;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	//display list of employees
	@RequestMapping("/")
	public String viewHomePage(Model model) {
//		model.addAttribute("listEmployees", employeeService.getAllEmployees());
//		return "index";
		return findPaginated(1, "firstName", "asc", model);  
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") long id) {
		employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo
			, @RequestParam(value = "sortField", defaultValue = "firstName") String sortField //, @RequestParam(value = "sortField", required = false) String sortField
			, @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir //, @RequestParam(value = "sortDir", required = false) String sortDir
			, Model model) {
		int pageSize = 5;
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> employees = page.getContent();
		model.addAttribute("currentPage", pageNo);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalItems", page.getTotalElements());
	    
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortDir", "desc".equalsIgnoreCase(sortDir) ? "desc" : "asc");
	    model.addAttribute("reverseSortDir", "desc".equalsIgnoreCase(sortDir) ? "asc" : "desc");
	    
	    model.addAttribute("listEmployees", employees);
	    return "index";
	}
}
