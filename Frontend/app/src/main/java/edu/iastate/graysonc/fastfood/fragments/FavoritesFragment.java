package edu.iastate.graysonc.fastfood.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recyclerClasses.FavoritesListAdapter;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;

public class FavoritesFragment extends Fragment {
    private static final String TAG = "FavoritesFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoritesViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private FavoritesListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private PopupWindow mPopupWindow;
    private View mPopupView;

    public FavoritesFragment() {}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Configure ViewModel
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel.class);
        mViewModel.init(App.account.getEmail());
        mViewModel.getFavorites().observe(this, f -> {
            if (f != null) {
                buildRecyclerView();
                mAdapter.notifyDataSetChanged();
            }
        });

        initRatingPopup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    private void initRatingPopup() {
        mPopupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_rating, null);
        mPopupWindow = new PopupWindow(mPopupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.submit_rating_button) {
                    //TODO: submit rating
                    mPopupWindow.dismiss();
                    return;
                }
                ((ImageView)mPopupView.findViewById(R.id.five_stars)).setImageResource(R.drawable.ic_star);
                ((ImageView)mPopupView.findViewById(R.id.four_stars)).setImageResource(R.drawable.ic_star);
                ((ImageView)mPopupView.findViewById(R.id.three_stars)).setImageResource(R.drawable.ic_star);
                ((ImageView)mPopupView.findViewById(R.id.two_stars)).setImageResource(R.drawable.ic_star);
                switch (v.getId()) {
                    case R.id.five_stars:
                        ((ImageView)v).setImageResource(R.drawable.ic_star_filled);
                    case R.id.four_stars:
                        ((ImageView)mPopupView.findViewById(R.id.four_stars)).setImageResource(R.drawable.ic_star_filled);
                    case R.id.three_stars:
                        ((ImageView)mPopupView.findViewById(R.id.three_stars)).setImageResource(R.drawable.ic_star_filled);
                    case R.id.two_stars:
                        ((ImageView)mPopupView.findViewById(R.id.two_stars)).setImageResource(R.drawable.ic_star_filled);
                    case R.id.one_star:
                        ((ImageView)mPopupView.findViewById(R.id.one_star)).setImageResource(R.drawable.ic_star_filled);
                        ((Button)mPopupView.findViewById(R.id.submit_rating_button)).setEnabled(true);
                        break;
                }
            }
        };
        mPopupView.findViewById(R.id.five_stars).setOnClickListener(listener);
        mPopupView.findViewById(R.id.four_stars).setOnClickListener(listener);
        mPopupView.findViewById(R.id.three_stars).setOnClickListener(listener);
        mPopupView.findViewById(R.id.two_stars).setOnClickListener(listener);
        mPopupView.findViewById(R.id.one_star).setOnClickListener(listener);
        mPopupView.findViewById(R.id.submit_rating_button).setOnClickListener(listener);
    }

    private void removeItem(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "removeItem: " + selectedItem.getName());
        mViewModel.removeFavorite(App.account.getEmail(), selectedItem.getId());
        mAdapter.notifyItemRemoved(position);
    }

    private void openRatingPopup(int position) {
        Log.d(TAG, "openRatingPopup: Called");
        mPopupWindow.showAsDropDown(getView(), 0, 0);
        // Continue here
        // TODO: Get rating from star images
    }

    private void openFoodPage(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "openFoodPage: " + selectedItem.getName());
        mAdapter.notifyItemChanged(position);
    }

    public void buildRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter = new FavoritesListAdapter(mViewModel.getFavorites().getValue());

        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FavoritesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openFoodPage(position);
            }

            @Override
            public void onRatingClick(int position) {
                openRatingPopup(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }


}