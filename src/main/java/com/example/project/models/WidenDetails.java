package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_widen_detail")
@Data
public class WidenDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int widen_detail_id;
	private int widen_id;
	private String item_code;
	
}
