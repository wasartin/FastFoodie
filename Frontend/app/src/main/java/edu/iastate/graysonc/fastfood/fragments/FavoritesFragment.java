package edu.iastate.graysonc.fastfood.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.activities.RatingActivity;
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

    private WebSocketClient mSocketClient;

    public FavoritesFragment() {
    }

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

        // Websocket stuff
        String w = "http://cs309-bs-1.misc.iastate.edu:8080/websocket/" + App.account.getEmail();
        try {
            Log.d("Socket:", "Trying socket");
            mSocketClient = new WebSocketClient(new URI(w)) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    // TODO: Update food rating
                }
                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }
                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    private void removeItem(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "removeItem: " + selectedItem.getName());
        mViewModel.removeFavorite(App.account.getEmail(), selectedItem.getId());
        mAdapter.notifyItemRemoved(position);
    }

    private void openRatingPopup(int position) {
        Log.d(TAG, "openRatingPopup: Called");
        Intent intent = new Intent(App.context, RatingActivity.class);
        startActivityForResult(intent, 1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            if (resultCode == 0) {
                Log.d(TAG, "onActivityResult: rating canceled");
            } else {
                int r = data.getIntExtra("rating", 1);
                Log.d(TAG, "onActivityResult: received rating of " + r);
            }
        }
    }
}