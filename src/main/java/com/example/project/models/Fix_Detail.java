package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_fix_detail")
@Data
public class Fix_Detail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int fix_id;
	private String item_code;
	private int fix_detail_status;
	private String fix_description;
}
