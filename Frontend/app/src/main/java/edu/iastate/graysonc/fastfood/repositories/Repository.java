package edu.iastate.graysonc.fastfood.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.dao.FavoriteDao;
import edu.iastate.graysonc.fastfood.database.dao.FoodDao;
import edu.iastate.graysonc.fastfood.database.dao.UserDao;
import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class Repository {
    private String TAG = "Repository";

    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

    private final Webservice webservice;
    private final UserDao userDao;
    private final FoodDao foodDao;
    private final Executor executor;

    @Inject
    public Repository(Webservice webservice, UserDao userDao, FoodDao foodDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.foodDao = foodDao;
        this.executor = executor;
    }



    /**
     * Fetches all users from the server and puts them in the Database
     * This is temporary until we have a way to get just foods in a specific user's favorites.
     */
    private void fetchAllUsers() {
        executor.execute(() -> {
            webservice.getAllUsers().enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    Log.d(TAG, "ALL USERS FETCHED FROM NETWORK");
                    executor.execute(() -> {
                        userDao.insert(response.body());
                    });
                }
                @Override
                public void onFailure(Call<List<User>> call, Throwable t) { t.printStackTrace(); }
            });
        });
    }

    /**
     * Gets the User object for the specified user
     * @param userEmail The email address of the user to fetch
     * @return The LiveData<User> object with the specified email address
     */
    public LiveData<User> getUser(String userEmail) {
        refreshUser(userEmail); // Refresh if possible
        return userDao.load(userEmail); // Returns a LiveData object directly from the database.
    }

    private void refreshUser(final String userEmail) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean userExists = (userDao.hasUser(userEmail, getMaxRefreshTime(new Date())) != null);
            // If user have to be updated
            if (!userExists) {
                webservice.getUser(userEmail).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d(TAG, "DATA REFRESHED FROM NETWORK");
                        Toast.makeText(App.context, "Data refreshed from network", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            User user = response.body();
                            if (user == null) {
                                Log.e(TAG,"Grayson your code doesn't work <3 - refreshUser");
                            } else {
                                user.setLastRefresh(new Date());
                                userDao.insert(user);
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

    /**
     * Adds all food objects from the server to the local database
     */
    private void fetchAllFoods() {
        executor.execute(() -> {
            webservice.getAllFoods().enqueue(new Callback<List<Food>>() {
                @Override
                public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                    Log.d(TAG, "ALL FOODS FETCHED FROM NETWORK");
                    executor.execute(() -> {
                        foodDao.insert(response.body());
                    });
                }
                @Override
                public void onFailure(Call<List<Food>> call, Throwable t) { t.printStackTrace(); }
            });
        });
    }

    /**
     * Refreshed the specified food object in the loacl database and returns it as a LiveData<Food> object
     * @param foodId The id of the food object to retrieve
     * @return The requested food object
     */
    public LiveData<Food> getFood(int foodId) {
        refreshFood(foodId); // Refresh if possible
        return foodDao.load(foodId); // Returns a LiveData object directly from the database.
    }

    private void refreshFood(final int foodId) {
        executor.execute(() -> {
            // Check if food was fetched recently
            boolean foodExists = (foodDao.hasFood(foodId, getMaxRefreshTime(new Date())) != null);
            // If food have to be updated
            if (!foodExists) {
                webservice.getFood(foodId).enqueue(new Callback<Food>() {
                    @Override
                    public void onResponse(Call<Food> call, Response<Food> response) {
                        Log.d(TAG, "DATA REFRESHED FROM NETWORK");
                        Toast.makeText(App.context, "Data refreshed from network", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            Food food = response.body();
                            if (food == null) {
                                Log.e(TAG,"Grayson your code doesn't work <3 - refreshFood");
                            } else {
                                food.setLastRefresh(new Date());
                                foodDao.insert(food);
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

    /**
     * Retrieves a list of Food objects that the specified user has "favorited"
     * @param userEmail The email address of the User whose favorite Food objects are being requested
     * @return The list of favorite Food objects
     */
    public LiveData<List<Food>> getFavoriteFoodsForUser(String userEmail) {
        refreshFavoriteFoodsForUser(userEmail);
        return foodDao.loadFavorites();
    }

    /**
     * Adds the specified Food to the specified User's favorites list
     * @param userEmail The User's email address
     * @param foodId The Food's id
     */
    public void createFavorite(String userEmail, int foodId) {
        executor.execute(() -> {
            webservice.createFavorite(userEmail, foodId).enqueue(new Callback<Favorite>() {
                @Override
                public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                    Log.d(TAG, "FAVORITE ADDED");
                    //Toast.makeText(App.context, "Added to favorites", Toast.LENGTH_LONG).show();
                    refreshFavoriteFoodsForUser(userEmail);
                }
                @Override
                public void onFailure(Call<Favorite> call, Throwable t) { t.printStackTrace(); }
            });
        });
    }

    /**
     * Removes the specified Food object from the specified User's favorites list
     * @param userEmail The User's email address
     * @param foodId The Food's id
     */
    public void deleteFavorite(String userEmail, int foodId) {
        executor.execute(() -> {
            //foodDao.delete(foodId);
            webservice.deleteFavorite(userEmail, foodId).enqueue(new Callback<Favorite>() {
                @Override
                public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                    Log.d(TAG, "FAVORITE REMOVED");
                    //Toast.makeText(App.context, "Removed from favorites", Toast.LENGTH_LONG).show();
                    refreshFavoriteFoodsForUser(userEmail);
                }
                @Override
                public void onFailure(Call<Favorite> call, Throwable t) { t.printStackTrace(); }
            });
        });
    }

    private void refreshFavoriteFoodsForUser(String userEmail) {
        executor.execute(() -> {
            webservice.getFavoriteFoodsForUser(userEmail).enqueue(new Callback<List<Food>>() {
                @Override
                public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                    Log.d(TAG, "FAVORITES REFRESHED FROM NETWORK");
                    Toast.makeText(App.context, "Data refreshed from network", Toast.LENGTH_LONG).show();
                    executor.execute(() -> {
                        List<Food> favorites = response.body();
                        if (favorites == null) {
                            Log.e(TAG,"Grayson your code doesn't work <3 - refreshFood");
                        } else {
                            for (Food f : favorites) {
                                f.setIsFavorite(1);
                                foodDao.delete(f.getId());
                            }
                            foodDao.insert(favorites);
                        }
                    });
                }
                @Override
                public void onFailure(Call<List<Food>> call, Throwable t) {
                        t.printStackTrace();
                }
            });
        });
    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }
}
