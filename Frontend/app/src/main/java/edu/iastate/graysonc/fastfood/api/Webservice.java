package edu.iastate.graysonc.fastfood.api;

import java.util.List;

import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Webservice {

    @GET("users/old/{user_email}")
    Call<User> getUser(@Path("user_email") String userEmail);

    @GET("users/old/all")
    Call<List<User>> getAllUsers();

    @POST("users/create")
    Call<User> createUser(@Body User user);

    @DELETE("users/delete/{user_email}")
    Call<User> deleteUser(@Path("user_email") String userEmail);

    @PUT("users/edit/{user_email}")
    Call<User> editUser(@Path("user_email") String userEmail);

    @GET("foods/old/{food_id}")
    Call<Food> getFood(@Path("food_id") int foodId);

    @GET("foods/old/all")
    Call<List<Food>> getAllFoods();

    @GET("users/old/{user_email}/favorites")
    Call<List<Food>> getFavoritesForUser(@Path("user_email") String userEmail);

    @GET("favorites/old/all")
    Call<List<Favorite>> getAllFavorites();

    @GET("users/old/{user_email}/favorites/create/{food_id}")
    Call<Food> createFavorite(@Path("user_email") String userEmail, @Path("food_id") int foodId);

    @DELETE("users/old/{user_email}/favorites/delete/{food_id}")
    Call<Food> deleteFavorite(@Path("user_email") String userEmail, @Path("food_id") int foodId);
}
