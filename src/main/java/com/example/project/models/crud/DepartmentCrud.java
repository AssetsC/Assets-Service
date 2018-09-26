package com.example.project.models.crud;

import org.springframework.data.repository.CrudRepository;

import com.example.project.models.Department;

public interface DepartmentCrud extends CrudRepository<Department, Integer> {
	
}
