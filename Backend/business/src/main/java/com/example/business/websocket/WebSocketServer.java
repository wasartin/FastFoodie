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
	
	@OnOpen
	public void onOpen(Session session, @PathParam("user_email") String user_email) throws IOException {
		sessionUserEmailMap.put(session, user_email);
		usernameSessionMap.put(user_email, session);
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws IOException{
		String user_email = sessionUserEmailMap.get(session);
		boolean success = updateDataBase(user_email, message);
		sendMessage(user_email, success);
		update(message);
	}
	
	private boolean updateDataBase(String user_email, String message) {
		//"299, 5"
		String[] parsedMessage = message.split(",");
		int food_id = Integer.valueOf(parsedMessage[0]);
		int rating = Integer.valueOf(parsedMessage[1]);
		FoodRating newRating = new FoodRating();
		newRating.setFood_id(food_id);
		newRating.setRating(rating);
		newRating.setUser_email(user_email);
		
		Food foundFood = foodRepo.findById(food_id).get();
		try {
			foodRatingRepo.save(newRating);
			foundFood.setRating(getRating(food_id));
			foodRepo.save(foundFood);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
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
	
	
	@OnClose
	public void onClose(Session session) throws IOException{
		String user_email = sessionUserEmailMap.get(session);
		sessionUserEmailMap.remove(session);
    	usernameSessionMap.remove(user_email);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable){
		logger.info("Entered into Error");
	}
	
	private void sendMessage(String user_email, boolean success) {	
		String message = (success) ? "@ The database has been updated" : "@ There was an error";
    	try {
    		usernameSessionMap.get(user_email).getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

	
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