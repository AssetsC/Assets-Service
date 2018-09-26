package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_item_partf")
@Data
public class Item_Part {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int item_part_id;	
	private String item_code;	
	private String name;	
	private String model;	
	private String value;	
	private String unit;
}
