package com.example.business.data.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.User;
import com.example.business.data.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserController {
	@Autowired
	UserService userService;
	
	/**
	 * get a specific user
	 * @param user_email
	 * @return specific user
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}")
	@ResponseBody
	public Optional<User> getUser(@PathVariable String user_email){
		return userService.getEntityByID(user_email);
	}

	/**
	 * get all users as optional 
	 * @param user_email
	 * @return optional<users>
	 */
	@GetMapping("/all")
	public Iterable<User> getAllUsers() {
		return userService.getAllEntities();
	}

	/**
	 * 
	 * @param newUser
	 * @return response entity
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createUser(@RequestBody User newUser) {
		return userService.createEntity(newUser, newUser.getUser_email());
	}
	
	/**
	 * Deletes the user given their unique id
	 * @param user_email
	 * @return response entity
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<?>  deleteUser(@PathVariable String user_email) {
		return userService.deleteEntityById(user_email);
	}
	
	/**
	 * @param userToEdit
	 * @return a json object response
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?>  editUser(@RequestBody User newUserInfo, @PathVariable String user_email) {
		return userService.editEntity(newUserInfo, user_email);
	}
	
}