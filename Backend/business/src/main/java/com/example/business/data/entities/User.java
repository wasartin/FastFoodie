package com.example.business.data.entities;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * An entity (or dao) that maps to the table 'user' in the Database. This represents the model of the object.
 * Users can either be general users (allowed some functions and abilities), regisitered users (more functions) or admin
 * (they can do whatever).
 */
@Entity
@Table(name="user")
public class User {
	
	/**
	 * User emails are used as ID's since they are unique
	 */
	@Id //specifies that this is a primary key
	@Column(name="user_email")
	private String user_email;//These names should exactly match the names of the db columns
	
	/**
	 * User type determines the abilities of the user
	 */
	@Column(name="user_type")
	private String user_type;
	
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public User() {//No arg constructor required by JPA for building properly
		super();
	}
	public User(String user_email, String user_type) {
		super();
		this.user_email = user_email;
		this.user_type = user_type;
	}
	
}