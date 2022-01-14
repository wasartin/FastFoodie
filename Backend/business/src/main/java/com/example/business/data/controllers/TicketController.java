package com.example.business.data.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Ticket;
import com.example.business.data.services.TicketService;

/**
 * A (REST Api) Controller class that "receives" HTTP requests from the front end for interacting with the ticket repository.
 */
@RestController
@RequestMapping(value="/tickets")
public class TicketController{

	@Autowired
	TicketService ticketService;
	
	/**
	 * Uses Repo class to return a rating in the DB
	 * @return aFood Rating
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{ticket_id}")
	@ResponseBody
	public Optional<Ticket> getSpecific(@PathVariable int ticket_id){
		return ticketService.getEntityByID(ticket_id);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/all")
	@ResponseBody
	public Iterable<Ticket> getAll(){
		return (List<Ticket>) ticketService.getAllEntities();
	}

	@RequestMapping(method = RequestMethod.GET, path = "all/user/{user_email}")
	@ResponseBody
	public List<Ticket> getAllForUser(@PathVariable String user_email){
		return ticketService.getAllticketsByUser(user_email);
	}

	@RequestMapping(method = RequestMethod.GET, path = "all/admin/{admin_email}")
	@ResponseBody
	public List<Ticket> getAllForAdmin(@PathVariable String admin_email){
		return ticketService.getAllTicketsForAdmin(admin_email);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createTicketByJSON(@RequestBody Ticket newTicket) {
		return ticketService.createEntity(newTicket, newTicket.getTicket_id());
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{ticket_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<?> deleteTicket(@PathVariable int ticket_id) {
		return ticketService.deleteEntityById(ticket_id);
	}

}