package com.example.project.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.models.Department;
import com.example.project.models.crud.DepartmentCrud;
import com.example.project.models.crud.WidenCrud;

@RestController
public class Department_Controller {	
	
	@Autowired private DepartmentCrud depart;
	@Autowired private WidenCrud widen;

	@CrossOrigin
	@GetMapping("/getNumAllDepartment")
	public List<Integer> getNumAllDepartment() throws InterruptedException  {
		Thread.sleep(500);
		var numDepart = new ArrayList<Integer>();
		var dep = depart.findAll();

		dep.forEach((t)->{
			var temp = widen.findAllDepartment(t.getDepart_id());
			numDepart.add(temp.size());
		});

		return numDepart;
	}
	
	@CrossOrigin
	@GetMapping("/getDepartment")
	public Iterable<Department> getAllDepartment() throws InterruptedException  {
		Thread.sleep(500);
		return depart.findAll();
	}
}
