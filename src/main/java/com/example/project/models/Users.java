package com.example.project.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bf_users")
@Data
public class Users {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int role_id;
	private String email;
	private String username;
	private String password_hash;
	private String firstname;
	private String lastname;
	private int depart_id;

	private String reset_hash;
	private String last_login;
	private String last_ip;
	private String created_on;
	private int deleted;
	private String reset_by;
	private int banned;
	private String ban_message;
	private String display_name;
	private String display_name_changed;
	private String timezone;
	private String language;
	private int force_password_reset;
	private int active;
	private String activate_hash;
}
