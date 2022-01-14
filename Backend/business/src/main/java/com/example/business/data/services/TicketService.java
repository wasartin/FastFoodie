package com.example.business.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.Ticket;
import com.example.business.data.repositories.TicketRepository;

/**
 * The favorites service class is where the bulk of the business logic is. 
 * This is the layer that will interface with its repository.
 */
@Service
public class TicketService extends AbstractService<Ticket, Integer>{
	@Autowired
	TicketRepository repo;

	/**
	 * A function for geting all tickets that a specific user has created.
	 * @param user_email
	 * @return list of tickets made by user.
	 */
	public List<Ticket> getAllticketsByUser(String user_email){
		return repo.getAllTicketsForUser(user_email);
	}
	
	/**
	 * A function for getting all tickets assigned to an admin.
	 * @param admin_email
	 * @return list of tickets for admin
	 */
	public List<Ticket> getAllTicketsForAdmin(String admin_email){
		return repo.getAllTicketsForAdmin(admin_email);
	}
}
