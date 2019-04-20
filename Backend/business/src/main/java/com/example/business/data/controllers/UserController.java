package com.example.business.data.controllers;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.User;
import com.example.business.data.repositories.UserRepository;
import com.example.business.data.services.UserService;

/**
 * @author Will and Jon
 *
 */
@RestController
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
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
		return userService.getUser(user_email);
	}

	/**
	 * get all users as optional 
	 * @param user_email
	 * @return optional<users>
	 */
	@GetMapping("/all")
	public Iterable<User> getAllUsers() {
		return userService.getAllUsers();
	}

	/**
	 * returns json object of specific user
	 * @param user_email
	 * @return a json object of the user
	 */
	@RequestMapping(method = RequestMethod.GET, path = "json/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getUserJSONObject(@PathVariable String user_email) {//TODO will just be changed to getUser once conversion is complete
		return userService.getUserJSONObject(user_email);
	}

	/**
	 * gets all users as json objects
	 * @return JSONObject that has key1-> "Users": value1->JSONArray of users in System
	 */
	@RequestMapping(value = "json/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllUsersJSONObject()  {
		return userService.getAllUsersJSONObject();
	}

	/**
	 * creates a new user
	 * @param newUser
	 * @return a json object response
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject createUser(@RequestBody User newUser) {
		return userService.createUser(newUser);
	}
	
	/**
	 * Deletes the user given their unique id
	 * @param user_email
	 * @return a json object response
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public JSONObject deleteUser(@PathVariable String user_email) {
		return userService.deleteUser(user_email);
	}
	
	/**
	 * edits a user
	 * @param userToEdit
	 * @return a json object response
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject editUser(@RequestBody User newUserInfo, @PathVariable String user_email) {
		return userService.editUser(newUserInfo, user_email);
	}
	
}