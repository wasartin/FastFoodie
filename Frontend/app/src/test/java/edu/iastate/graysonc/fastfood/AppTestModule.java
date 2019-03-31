package edu.iastate.graysonc.fastfood;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.mockito.Mockito;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.MyDatabase;
import edu.iastate.graysonc.fastfood.database.dao.FavoriteDao;
import edu.iastate.graysonc.fastfood.database.dao.FoodDao;
import edu.iastate.graysonc.fastfood.database.dao.UserDao;
import edu.iastate.graysonc.fastfood.di.module.AppModule;
import edu.iastate.graysonc.fastfood.repositories.Repository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppTestModule extends AppModule {

    // --- DATABASE INJECTION ---

    @Override
    public MyDatabase provideDatabase(Application application) { return Mockito.mock(MyDatabase.class); }

    @Override
    public UserDao provideUserDao(MyDatabase database) { return Mockito.mock(UserDao.class); }

    @Override
    public FoodDao provideFoodDao(MyDatabase database) { return Mockito.mock(FoodDao.class); }

    @Override
    public FavoriteDao provideFavoriteDao(MyDatabase database) { return Mockito.mock(FavoriteDao.class); }

    // --- REPOSITORY INJECTION ---

    @Override
    public Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Override
    public Repository provideRepository(Webservice webservice, UserDao userDAO, FoodDao foodDAO, FavoriteDao favoriteDao, Executor executor) {
        return Mockito.mock(Repository.class);
    }

    // --- NETWORK INJECTION ---

    private static String BASE_URL = "http://cs309-bs-1.misc.iastate.edu:8080/";

    @Override
    public Gson provideGson() { return new GsonBuilder().create(); }

    @Override
    public Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Override
    public Webservice provideApiWebservice(Retrofit restAdapter) {
        return Mockito.mock(Webservice.class);
    }
}
