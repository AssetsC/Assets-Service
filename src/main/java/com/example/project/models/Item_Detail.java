package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_item_detail")
@Data
public class Item_Detail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int item_detail_id;
	private String item_code;
	private String detail_name;
	private String detail_value;

}
