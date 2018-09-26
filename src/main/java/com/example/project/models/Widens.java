package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="bf_widen")
@Data
public class Widens {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int widen_id;
	private int user_id;
	private String widen_date;
	private int widen_status;
	private String date_res;
}
