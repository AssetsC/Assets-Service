package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_tableupdate")
@Data
public class TableUpdate {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int table_id;	
	private String table_name;	
	private String table_value;

}
