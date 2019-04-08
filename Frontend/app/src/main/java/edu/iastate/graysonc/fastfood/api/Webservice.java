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

    @GET("users/{user_email}")
    Call<User> getUser(@Path("user_email") String userEmail);

    @GET("users/all")
    Call<List<User>> getAllUsers();

    @POST("users/create")
    Call<User> createUser(@Body User user);

    @DELETE("users/delete/{user_email}")
    Call<User> deleteUser(@Path("user_email") String userEmail);

    @PUT("users/edit/{user_email}")
    Call<User> editUser(@Path("user_email") String userEmail);

    @GET("foods/{food_id}")
    Call<Food> getFood(@Path("food_id") int foodId);

    @GET("foods/all")
    Call<List<Food>> getAllFoods();

    @GET("api/favorites/{user_email}")
    Call<List<Food>> getFavoriteFoodsForUser(@Path("user_email") String userEmail);

    @GET("favorites/all") // Might be deleted soon
    Call<List<Favorite>> getAllFavorites();

    @POST("favorites/create/{user_email}/{food_id}")
    Call<Favorite> createFavorite(@Path("user_email") String userEmail, @Path("food_id") int foodId);

    @DELETE("favorites/delete/{user_email}/{food_id}")
    Call<Favorite> deleteFavorite(@Path("user_email") String userEmail, @Path("food_id") int foodId);

    @GET("foodRatings/average/{food_id}")
    Call<String> getAverageFoodRating(@Path("food_id") int foodId);

    @POST("foodRatings/create/{user_email}/{food_id}/{rating}")
    Call<String> createFoodRating(@Path("user_email") String userEmail, @Path("food_id") int foodId, @Path("rating") int rating);

    @PUT("foodRatings/edit/{user_email}/{food_id}/{rating}")
    Call<String> editFoodRating(@Path("user_email") String userEmail, @Path("food_id") int foodId, @Path("rating") int rating);

}
