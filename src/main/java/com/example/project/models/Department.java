package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_department")
@Data
public class Department {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int depart_id;	
	private String depart_name;
}
