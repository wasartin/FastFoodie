package edu.iastate.graysonc.fastfood.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import edu.iastate.graysonc.fastfood.database.converter.DateConverter;
import edu.iastate.graysonc.fastfood.database.dao.FavoriteDao;
import edu.iastate.graysonc.fastfood.database.dao.FoodDao;
import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.database.dao.UserDao;

@Database(entities = {User.class, Food.class}, version = 12)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {
    // --- SINGLETON ---
    private static volatile MyDatabase INSTANCE;

    // --- DAO ---
    public abstract UserDao userDao();
    public abstract FoodDao foodDao();
}