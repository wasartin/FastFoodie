package edu.iastate.graysonc.fastfood;

import android.arch.lifecycle.LiveData;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Date;
import java.util.concurrent.Executor;

import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.dao.FavoriteDao;
import edu.iastate.graysonc.fastfood.database.dao.FoodDao;
import edu.iastate.graysonc.fastfood.database.dao.UserDao;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.repositories.Repository;

import static junit.framework.TestCase.assertEquals;

public class RepositoryTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock private Webservice webservice;
    @Mock private UserDao userDao;
    @Mock private FoodDao foodDao;
    @Mock private FavoriteDao favoriteDao;
    @Mock private Executor executor;

    @InjectMocks Repository repo;

    @Test
    public void testGetUser() {
        
    }

    @Test
    public void testGetFood() {

    }

    @Test
    public void testGetFavoritesForUser() {

    }

}
