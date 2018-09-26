package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_userupdate")
@Data
public class UserUpdate {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;	
	private String table_name;	
	private int user_id;
	private String table_value;
}
