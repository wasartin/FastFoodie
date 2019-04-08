package edu.iastate.graysonc.fastfood.view_models;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;
import java.util.function.Predicate;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.fragments.FavoritesFragment;
import edu.iastate.graysonc.fastfood.recyclerClasses.FavoritesListAdapter;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class FavoritesViewModel extends ViewModel {
    private String TAG = "FavoritesViewModel";
    private Repository repo;
    private LiveData<List<Food>> favoriteFoods;

    @Inject
    public FavoritesViewModel(Repository repo) {
        this.repo = repo;
    }

    public void init(String userEmail) {
        if (this.favoriteFoods != null) {
            return;
        }
        favoriteFoods =  repo.getFavoriteFoodsForUser(userEmail);
    }

    public LiveData<List<Food>> getFavorites() {
        return favoriteFoods;
    }

    public void updateFavorites(LifecycleOwner owner, FavoritesListAdapter adapter) {
        LiveData<List<Food>> tmp = repo.getFavoriteFoodsForUser(App.account.getEmail());
        tmp.observe(owner, f -> {
            favoriteFoods = tmp;
            ((FavoritesFragment)owner).buildRecyclerView();
            adapter.notifyDataSetChanged();
        });
    }

    public void submitFoodRating(String userEmail, int foodId, int rating) {
        repo.submitFoodRating(userEmail, foodId, rating);
    }

    public void removeFavorite(String userEmail, int foodId) {
        repo.deleteFavorite(userEmail, foodId);
    }
}
