package net.javaguides.springbootthymleafcrudwebapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.javaguides.springbootthymleafcrudwebapp.model.Employee;
import net.javaguides.springbootthymleafcrudwebapp.repository.EmployeeRepository;
import net.javaguides.springbootthymleafcrudwebapp.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		List<Employee> res = employeeRepository.findAll();
		return res;
	}

	@Override
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		Employee res = null;
		if(optional.isPresent()) {
			res = optional.get();
		} else {
			throw new RuntimeException("Employee not found for id: " + id);
		}
		return res;
	}

	@Override
	public void deleteEmployeeById(long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = null;
		if(sortField != null) {
			sort = Sort.Direction.DESC.name().equalsIgnoreCase(sortDirection) ? Sort.by(sortField).descending() :
				Sort.by(sortField).ascending();
//			sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//	            Sort.by(sortField).descending();
		}
		 
		Pageable pageable = sort == null ? PageRequest.of(pageNo-1, pageSize) : PageRequest.of(pageNo-1, pageSize, sort);
		return employeeRepository.findAll(pageable);
	}

}
