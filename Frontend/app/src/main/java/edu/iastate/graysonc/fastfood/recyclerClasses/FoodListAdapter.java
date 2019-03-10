package edu.iastate.graysonc.fastfood.recyclerClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView restaurantTextView;

        private FoodViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text);
            restaurantTextView = itemView.findViewById(R.id.restaurant_text);
        }
    }

    private final LayoutInflater mInflater;
    private List<Food> mFoods; // Cached copy of foods

    public FoodListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_card, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        if (mFoods != null) {
            Food current = mFoods.get(position);
            holder.nameTextView.setText(current.getName());
            holder.restaurantTextView.setText("" + current.getLocation());
        } else {
            // Covers the case of data not being ready yet.
            holder.nameTextView.setText("Loading...");
            holder.restaurantTextView.setText("Loading...");
        }
    }

    public void setFoods(List<Food> foods) {
        mFoods = foods;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mFoods has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mFoods != null) {
            return mFoods.size();
        } else {
            return 0;
        }
    }
}
