package com.example.business.data.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.business.data.entities.User;
import com.example.business.data.repositories.UserRepository;

@Service
public class UserService {

	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";
	@SuppressWarnings("unused")
	private final String JSON_OBJECT_RESPONSE_KEY2 = "info";
	@SuppressWarnings("unused")
	private final String JSON_OBJECT_RESPONSE_KEY3 = "favoritesList";
		
	@Autowired
	UserRepository userRepository;
	
	/**
	 * get a specific user
	 * @param user_email
	 * @return specific user
	 */
	public Optional<User> getUser(String user_email){
		return userRepository.findById(user_email);
	}
	/**
	 * get all users as optional 
	 * @param user_email
	 * @return optional<users>
	 */
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	/**
	 * returns json object of specific user
	 * @param user_email
	 * @return a json object of the user
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getUserJSONObject(String user_email) {//TODO will just be changed to getUser once conversion is complete
		Optional<User> temp = userRepository.findById(user_email);
		JSONObject response = new JSONObject();
		response.put(JSON_OBJECT_RESPONSE_KEY1, temp.get());
		return response;
	}
	
	/**
	 * This private helper method is used to pull all the users from the Data base so it is easier to parse into a JSONObject
	 * @return List<User>
	 */
	private List<User> getUsers(){
		Iterable<User> uIters = userRepository.findAll();
		List<User> uList = new ArrayList<User>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	/**
	 * This is a private helper method that parses the Backend's version of a user into a JSON object, 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONObject parseUserIntoJSONObject(User user) {
		final String USER_EMAIL_KEY = "user_email";
		final String USER_TYPE_KEY = "user_type";
		JSONObject userAsJSONObj = new JSONObject();
		userAsJSONObj.put(USER_EMAIL_KEY, user.getUser_email());
		userAsJSONObj.put(USER_TYPE_KEY, user.getUser_type());
		return userAsJSONObj;
	}
	/**
	 * gets all users as json objects
	 * @return JSONObject that has key1-> "Users": value1->JSONArray of users in System
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getAllUsersJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = JSON_OBJECT_RESPONSE_KEY1;
		JSONArray listOfUsers = new JSONArray();
		List<User> uList = getUsers();
		for(User user : uList) {
			listOfUsers.add(parseUserIntoJSONObject(user));
		}
		toReturn.put(key1, listOfUsers);
		return toReturn;
	}
	
	/**
	 * private helper to generate json response
	 * @param status
	 * @param input
	 * @param message
	 * @return json response
	 */
	@SuppressWarnings("unchecked")
	private JSONObject generateResponse(int status, HttpStatus input, String message) {
		JSONObject response = new JSONObject();
		response.put("status", status);
		response.put("HttpStatus", input);
		response.put("message", message);
		return response;
	}
	/**
	 * creates a new user
	 * @param newUser
	 * @return a json object response
	 */
	public JSONObject createUser(User newUser) {
		JSONObject response;
		try {
			if(userRepository.existsById(newUser.getUser_email())) {//User already exists
				throw new IllegalArgumentException();
			}
			userRepository.save(newUser);
			response = generateResponse(204, HttpStatus.OK, "User has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "User might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	/**
	 * Deletes the user given their unique id
	 * @param user_email
	 * @return a json object response
	 */
	public JSONObject deleteUser(String user_email) {
		JSONObject response;
		try {
			if(!userRepository.existsById(user_email)) {//Checks to see if User is even in the DB
				throw new IllegalArgumentException();
			}
			userRepository.deleteById(user_email);
			response = generateResponse(204, HttpStatus.OK, "User has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that user in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	/**
	 * edits a user
	 * @param userToEdit
	 * @return a json object response
	 */
	public JSONObject editUser(User newUserInfo, String user_email) {
		JSONObject response;
		try {
			if(!userRepository.existsById(user_email)) {//pretty sure that is how I want to do this.
				throw new IllegalArgumentException();
			}
			userRepository.save(newUserInfo);//this will edit the user
			response = generateResponse(200, HttpStatus.OK, "User has been edited");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that user in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
}
