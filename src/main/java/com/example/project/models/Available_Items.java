package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_available_item")
@Data
public class Available_Items {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int available_Item_id;
	private int user_id;
	private String item_code;
	private String start_date;
	private int available_state;
}
