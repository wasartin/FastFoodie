package com.example.business.data.entities;

import javax.persistence.Column; 
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id //specifies that this is a primary key
	@Column(name="user_email")
	private String user_email;//These names should exactly match the names of the db columns
	@Column(name="user_type")
	private String user_type;
	
	/**
	 * email getter
	 * @return string email 
	 */
	public String getUser_email() {
		return user_email;
	}
	/**
	 * email setter
	 * @param user_email
	 */
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	/**
	 * user type getter
	 * @return string user type
	 */
	public String getUser_type() {
		return user_type;
	}
	/**
	 * user type setter
	 * @param user_type
	 */
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	/**
	 * default constructor
	 */
	public User() {//No arg constructor required by JPA for building properly
		super();
	}
	/**
	 * constructor for users
	 * @param user_email
	 * @param user_type
	 */
	public User(String user_email, String user_type) {
		super();
		this.user_email = user_email;
		this.user_type = user_type;
	}
	
}