package edu.iastate.graysonc.fastfood.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class FoodViewModel extends ViewModel {
    private String TAG = "FoodViewModel";
    private Repository repo;
    private LiveData<List<Food>> favoriteFoods;
    private LiveData<List<Food>> searchResults;
    private MutableLiveData<Food> selectedFood;

    private String mUserEmail;

    @Inject
    public FoodViewModel(Repository repo) {
        this.repo = repo;
        selectedFood = new MutableLiveData<Food>();
    }

    public void initFavoriteFoods(String userEmail) {
        mUserEmail = userEmail;
    }

    public void doSearch(String query) {
        loadSearchResults(query);
    }

    public LiveData<List<Food>> getSearchResults() {
        if (searchResults == null) {
            searchResults = new MutableLiveData<List<Food>>();
            //loadSearchResults(query);
        }
        return searchResults;
    }

    public LiveData<List<Food>> getFavoriteFoods() {
        if (favoriteFoods == null) {
            favoriteFoods = new MutableLiveData<List<Food>>();
            loadFavoriteFoods(mUserEmail);
        }
        return favoriteFoods;
    }

    public void loadSearchResults(String query) {
        searchResults = repo.getFoodMatches(query);
    }

    public void loadFavoriteFoods(String userEmail) {
        favoriteFoods = repo.getFavoriteFoodsForUser(userEmail);
    }

    public MutableLiveData<Food> getSelectedFood() {
        return selectedFood;
    }

    public void setSelectedFood(Food food) {
        selectedFood.setValue(food);
    }
}