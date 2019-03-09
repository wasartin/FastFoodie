package edu.iastate.graysonc.fastfood.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.dao.FoodDAO;
import edu.iastate.graysonc.fastfood.database.dao.UserDAO;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class Repository {

    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

    private final Webservice webservice;
    private final UserDAO userDAO;
    private final FoodDAO foodDAO;
    private final Executor executor;

    @Inject
    public Repository(Webservice webservice, UserDAO userDAO, FoodDAO foodDAO, Executor executor) {
        this.webservice = webservice;
        this.userDAO = userDAO;
        this.foodDAO = foodDAO;
        this.executor = executor;
    }

    public LiveData<User> getUser(String email) {
        refreshUser(email); // Refresh if possible
        return userDAO.load(email); // Returns a LiveData object directly from the database.
    }

    private void refreshUser(final String email) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean userExists = (userDAO.hasUser(email, getMaxRefreshTime(new Date())) != null);
            // If user have to be updated
            if (!userExists) {
                webservice.getUser(email).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            User user = response.body();
                            if(user == null) {
                                Log.v("RepositoryEr","Grayson your code doesn't work <3");
                            } else {
                                user.setLastRefresh(new Date());
                                userDAO.save(user);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public LiveData<Food> getFood(int id) {
        refreshFood(id); // Refresh if possible
        return foodDAO.load(id); // Returns a LiveData object directly from the database.
    }

    private void refreshFood(final int id) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean foodExists = (foodDAO.hasFood(id, getMaxRefreshTime(new Date())) != null);
            // If user have to be updated
            if (!foodExists) {
                webservice.getFood(id).enqueue(new Callback<Food>() {
                    @Override
                    public void onResponse(Call<Food> call, Response<Food> response) {
                        Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            Food food = response.body();
                            if(food == null) {
                                Log.v("RepositoryEr","Grayson your code doesn't work <3");
                            } else {
                                food.setLastRefresh(new Date());
                                foodDAO.save(food);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Food> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }
}
