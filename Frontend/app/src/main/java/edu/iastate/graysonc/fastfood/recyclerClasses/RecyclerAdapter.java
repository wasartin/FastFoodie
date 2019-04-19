package edu.iastate.graysonc.fastfood.recyclerClasses;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.iastate.graysonc.fastfood.R;

public class RecyclerAdapter extends  android.support.v7.widget.RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<recycler_card> mList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onFaveClick(int position);
    }

    /**
     * Assign an on click listener to this Adapter
     * @param listener onClick listener to be used
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public  class ViewHolder extends  android.support.v7.widget.RecyclerView.ViewHolder{
        public CheckBox mDeleteImage;
        public TextView mLine1;
        public TextView mLine2;
        public ViewHolder(@NonNull View itemView, OnItemClickListener  listener) {
            super(itemView);
            mLine1 = itemView.findViewById(R.id.name_text);
            mLine2 = itemView.findViewById(R.id.restaurant_text);
            mDeleteImage = itemView.findViewById(R.id.favorite_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onFaveClick(pos);
                            /*if(mList.get(pos).isFavored()){
                                mDeleteImage.setImageResource(R.drawable.twotone_favorite_black_18dp_2x);
                            }else{
                                mDeleteImage.setImageResource(R.drawable.twotone_favorite_border_black_18dp_2x);
                            }*/
                        }
                    }
                }
            });
        }
    }

    /**
     * Creates an Adaptor given a list
     * @param list List of data to be used
     */
    public RecyclerAdapter(ArrayList<recycler_card> list){
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        recycler_card currentCard = mList.get(pos);
        viewHolder.mLine1.setText(currentCard.getFood());
        viewHolder.mLine2.setText(currentCard.getData());
        viewHolder.mDeleteImage.setChecked(mList.get(pos).isFavored());
        /*if(mList.get(pos).isFavored()){
            viewHolder.mDeleteImage.setImageResource(R.drawable.twotone_favorite_black_18dp_2x);
        }else{
            viewHolder.mDeleteImage.setImageResource(R.drawable.twotone_favorite_border_black_18dp_2x);
        }*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }



}
