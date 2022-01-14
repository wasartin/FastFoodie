package com.example.business.data.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * An entity (or dao) that maps to the table 'ticket' in the Database. This represents the model of the object.
 */
@Entity
@Table(name="ticket")
public class Ticket {
	
	/**
	 * Unique id to find this ticket.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ticket_id")
	private int ticket_id; 
	
	/**
	 * The user that submitted the ticket
	 */
	@Column(name="user_id")
	private String user_id;
	
	/**
	 * the admin this ticket has been assigned to.
	 */
	@Column(name="admin_id")
	private String admin_id;
	
	/**
	 * The date this ticket was assigned.
	 */
	@Column(name="date_assigned")
	private Timestamp date_assigned;
	
	/**
	 * The text body of the message
	 */
	@Column(name="text")
	private String text;
	
	/**
	 * Category of the issue
	 */
	@Column(name="category")
	private String category;
	
	public Ticket() {
		super();
	}

	public Ticket(int ticket_id, String user_id, String admin_id, Timestamp date, String text, String category) {
		super();
		this.ticket_id = ticket_id;
		this.user_id = user_id;
		this.admin_id = admin_id;
		this.date_assigned = date;
		this.text = text;
		this.category = category;
	}
	
	public int getTicket_id() {
		return ticket_id;
	}
	
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}

	public Timestamp getDate() {
		return date_assigned;
	}

	public void setDate(Timestamp date) {
		this.date_assigned = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}