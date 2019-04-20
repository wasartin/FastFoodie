package com.example.business.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.business.data.entities.Food;
import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;
import com.example.business.data.repositories.FoodRepository;

@ServerEndpoint("/websocket/{user_email}")
@Component
public class WebSocketServer {
	
	@Autowired
	FoodRatingRepository foodRatingRepo;
	
	@Autowired 
	FoodRepository foodRepo;

	private static Map<Session, String> sessionUserEmailMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();
	
	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	
	/**
	 * On open, opens a new session with the specific user email
	 * @param session
	 * @param user_email
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("user_email") String user_email) throws IOException {
		sessionUserEmailMap.put(session, user_email);
		usernameSessionMap.put(user_email, session);
	}
	
	/**
	 * sends a message (string) and updates the session and message
	 * @param session
	 * @param message
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(Session session, String message) throws IOException{
		String user_email = sessionUserEmailMap.get(session);
		String result = updateDataBase(user_email, message);
		sendMessage(user_email, result);
		update(message);
	}
	
	/**
	 * updates the database
	 * @param user_email
	 * @param message
	 * @return String
	 */
	private String updateDataBase(String user_email, String message) {
		String result = "@";
		//"299, 5"
		String[] parsedMessage = message.split(",");
		int food_id = Integer.valueOf(parsedMessage[0]);
		int rating = Integer.valueOf(parsedMessage[1]);
		
		FoodRating newRating = new FoodRating();
		newRating.setFood_id(food_id);
		newRating.setRating(rating);
		newRating.setUser_email(user_email);

		try {
			foodRatingRepo.save(newRating);
		}catch(Exception e) {
			logger.info("There was an error saving the rating in the food_rating table");
			logger.info(e.getMessage());
		}
		Food foundFood = foodRepo.findById(food_id).get();
		try {
			double newRatingValue = getRating(food_id);
			foundFood.setRating(newRatingValue);
			foodRepo.save(foundFood);
		}catch(Exception e) {
			logger.info("There was an error saving the new food rating in the food table");
			logger.info(e.getMessage());
			result += e.getMessage();
		}
		result += "success";
		return "@ The database has been updated";
	}
	
	/**
	 * gets specific rating from a specific food id
	 * @param food_id
	 * @return double food rating 
	 */
	private double getRating(int food_id) {
		List<Integer> ratingList = foodRatingRepo.findAllRatingsForFood(food_id);
		double sum = 0;
		if(!ratingList.isEmpty()) {
			for (int curr : ratingList) {
				if (curr > 0 && curr < 6) {
					sum += curr;
				}
			}
		}
		return sum / ratingList.size();
	}
	
	/**
	 * closes the session on close
	 * @param session
	 * @throws IOException
	 */
	@OnClose
	public void onClose(Session session) throws IOException{
		String user_email = sessionUserEmailMap.get(session);
		sessionUserEmailMap.remove(session);
    	usernameSessionMap.remove(user_email);
	}
	
	/**
	 * handles errors
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(Session session, Throwable throwable){
		logger.info("Entered into Error");
	}
	
	/**
	 * sends string as message
	 * @param user_email
	 * @param result
	 */
	private void sendMessage(String user_email, String result) {	
		String message = result;
    	try {
    		usernameSessionMap.get(user_email).getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

	/**
	 * sends the message through the remote
	 * @param message
	 * @throws IOException
	 */
	private static void update(String message) throws IOException{
		sessionUserEmailMap.forEach((session, username) -> {
    		synchronized (session) {
	            try {
	                session.getBasicRemote().sendText(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
}