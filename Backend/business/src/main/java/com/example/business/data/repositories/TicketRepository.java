package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Ticket;

/**
 * The repository layer interacts with the DB on the TicketsDAO
 */
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

	/**
	 * This query finds all the tickets made by a user
	 * @param user_id
	 * @return
	 */
	@Query(value ="SELECT * FROM ticket t WHERE t.user_id =?1", nativeQuery = true)
	List<Ticket> getAllTicketsForUser(String user_id);
	
	/**
	 * This query finds all tickets assigned to a specific admin (user).
	 * @param admin_id
	 * @return
	 */
	@Query(value ="SELECT * FROM ticket t WHERE t.admin_id =?1", nativeQuery = true)
	List<Ticket> getAllTicketsForAdmin(String admin_id);
	
}
