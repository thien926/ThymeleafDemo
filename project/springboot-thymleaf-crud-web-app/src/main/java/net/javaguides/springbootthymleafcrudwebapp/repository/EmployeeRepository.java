package net.javaguides.springbootthymleafcrudwebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springbootthymleafcrudwebapp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
