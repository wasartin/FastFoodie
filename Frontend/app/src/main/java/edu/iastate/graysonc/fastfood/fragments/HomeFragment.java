package edu.iastate.graysonc.fastfood.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.view_models.HomeViewModel;
import edu.iastate.graysonc.fastfood.recyclerClasses.RecyclerAdapter;
import edu.iastate.graysonc.fastfood.recyclerClasses.recycler_card;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeViewModel viewModel;
    private SearchView mSearchView;
    private RecyclerView mainList;
    private ArrayList<recycler_card> foodList;
    private RadioGroup mSortBy;
    private RequestQueue requestQueue;
    private RecyclerAdapter mAdapter;
    private boolean CustomSearchExpanded;
    private Animation fINAnim,fOUTAnim;
    private Spinner mSpinner1, mSpinner2;

    public enum macrosENUM{
        FAT("Fat"),
        CARBS("Carbs"),
        PROTEIN("Protein"),
        SUGAR("Sugar");

        private String name;

        macrosENUM(String name){
            this.name = name;
        }
        @Override public String toString(){
            return name;
        }
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Configure ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        foodList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        mAdapter = new RecyclerAdapter(foodList);
        mSortBy = getView().findViewById(R.id.searchByRadioGroup);
        mSortBy.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.searchByCustom){
                updateSearchOptions(true);
            }else{
                updateSearchOptions(false);
            }
            Log.v("SearchByRadio", "Changed to " + checkedId);
        });
        CustomSearchExpanded = false;
        mSearchView = getView().findViewById(R.id.SearchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                updateFaves();
                buildList(query);
                buildView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setFocusable(true);
        //mSearchView.setIconified(false);
        //mSearchView.requestFocusFromTouch();

        fINAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fINAnim.setDuration(300);
        fOUTAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        fOUTAnim.setDuration(300);

        mSpinner1 = getView().findViewById(R.id.CustomParameterSpinner1);
        mSpinner2 = getView().findViewById(R.id.CustomParameterSpinner2);
        populateSpinners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroy() {
        //Logs foods to remove
        //TODO Remove this food from Favorites
        super.onDestroy();
        updateFaves();
    }

    private void buildList(String query) {
        RadioGroup mSortBy = getView().findViewById(R.id.searchByRadioGroup);
        String url="";
        if (mSortBy.getCheckedRadioButtonId() == R.id.searchByFood || mSortBy.getCheckedRadioButtonId() == R.id.searchByCustom) {
            url = "http://cs309-bs-1.misc.iastate.edu:8080/foods/all";
        }else if (mSortBy.getCheckedRadioButtonId() == R.id.searchByRes){
            url = "http://cs309-bs-1.misc.iastate.edu:8080/restaurants/all";
        }else{
            Log.v("HomeFragCatERR","Invalid Selection on RadioGroup");
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                foodList.clear();
                JSONArray resArr = response.getJSONArray("data");
                for (int i = 0; i < resArr.length(); i++) {
                    JSONObject food = resArr.getJSONObject(i);
                    if (mSortBy.getCheckedRadioButtonId() == R.id.searchByRes) { //if filter by restaurant or macros
                        if (food.getString("restaurant_name").toLowerCase().contains(query.toLowerCase())) {
                            foodList.add(new recycler_card(new Food(food.getInt("restaurant_id"),food.getString("restaurant_name"),0,0,0,0,0)));
                        }
                    } else if (mSortBy.getCheckedRadioButtonId() == R.id.searchByFood || mSortBy.getCheckedRadioButtonId() == R.id.searchByCustom ) { //filter by food
                        if (food.getString("food_name").toLowerCase().contains(query.toLowerCase())) {
                            foodList.add(new recycler_card(food.getInt("food_id"), food.getString("food_name"), "Calories = " + food.getInt("calorie_total"), false,food.getInt("food_id")));
                        }
                    } else {
                        Log.v("FilterJsonErr", "Invalid selection of radio button");
                    }

                } mAdapter.notifyDataSetChanged();
                Log.v("HomeRecycle", "Updated data Set");
            } catch (JSONException e) {
                Log.v("JSO to JSA Err", e.toString());
            }
        }, error -> Log.v("JSRQError", error.toString()));

        requestQueue.add(jsonObjectRequest);

        if(mSortBy.getCheckedRadioButtonId() == R.id.searchByCustom){
            sortList();
        }
    }

    /**
     * Creates a recycler view in as many lines as possible
     */
    private void buildView() {
        mainList = Objects.requireNonNull(getView()).findViewById(R.id.HomeRecyclerView);
        mainList.setHasFixedSize(true); //Prevents dynamic resizing, improves performance
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mainList.setLayoutManager(mLayoutManager);
        mainList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO actually open this
                Context context = getContext();
                Toast.makeText(context, "Open Data About " + foodList.get(position).getFood(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFaveClick(int position) {
                //removeItem(position);
                recycler_card temp = foodList.get(position);
                if (temp.isFavored()) {
                    viewModel.removeFavorite(getArguments().getString("USER_EMAIL"), temp.getFoodId());
                    temp.setFavored(false);
                } else {
                    viewModel.addFavorite(getArguments().getString("USER_EMAIL"), temp.getFoodId());
                    temp.setFavored(true);
                }
            }
        });
    }

    /**
     * Foods Added to favorites
     * @return A list of foods removed
     */
    private ArrayList<Integer> checkList(){
        ArrayList<Integer> removed = new ArrayList<>();
        for(recycler_card item : foodList){
            if(item.isFavored()) removed.add(item.getFoodId());
        }
        return removed;
    }

    public void updateFaves(){
        //TODO Add this food from Favorites
        Log.v("AddToFavoritesDebug",checkList().toString());
        Context context = getContext();
        Toast.makeText(context, "Added " + checkList().toString(), Toast.LENGTH_LONG).show();
    }

    /**
     *
     * @param open Should we expand the menu?
     */
    private void updateSearchOptions(boolean open){
        CardView viewer = getView().findViewById(R.id.CustomSearchDisplay);
        if(open){//open
            CustomSearchExpanded = true;
            viewer.setVisibility(View.VISIBLE);
            viewer.startAnimation(fINAnim);
        }else{
            if(CustomSearchExpanded){  //close
                CustomSearchExpanded = false;;
                viewer.startAnimation(fOUTAnim);
                Handler handler = new Handler();
                handler.postDelayed(() -> viewer.setVisibility(View.GONE), 310);
            }else{
                //nothing
            }
        }
    }

    /**
     * Loads the spinners in the pop up menu with all the values in the enumerator
     */
    private void populateSpinners(){
        mSpinner2.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, macrosENUM.values()));
        mSpinner1.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, macrosENUM.values()));

    }

    /**
     * Sorts the list comparing the values of the two spinners
     */
    private void sortList(){
        Collections.sort(foodList, (o1, o2) -> {
            int vala1,vala2,valb1,valb2;
            vala1 = getMacro(o1, (macrosENUM) mSpinner1.getSelectedItem());
            vala2 = getMacro(o1, (macrosENUM) mSpinner1.getSelectedItem());
            valb1 = getMacro(o2, (macrosENUM) mSpinner2.getSelectedItem());
            valb2 = getMacro(o2, (macrosENUM) mSpinner2.getSelectedItem());
            o1.setmLine2("Comparator = " + vala1/vala2);
            o2.setmLine2("Comparator = " + valb1/valb2);
            return vala1/vala2 - valb1/valb2;
        });
    }

    public int getMacro(recycler_card card, macrosENUM type){
       Log.v("GetMacroLog","Sorting by macro");
        switch(type){
            case FAT:
                return card.getFoodObj().getFatTotal();
            case CARBS:
                return card.getFoodObj().getCarbTotal();
            case SUGAR:
                Toast.makeText(getContext(), "Sorting by sugar not yet implemented",Toast.LENGTH_LONG).show();
                return 1;
            case PROTEIN:
                return card.getFoodObj().getProteinTotal();
            default:
                Log.v("GetMacroERR", "Invalid State");
                return 1;
        }
    }
}
