package edu.iastate.graysonc.fastfood.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.iastate.graysonc.fastfood.R;

public class RatingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView star1, star2, star3, star4, star5, closeButton;

    private Button submitButton;

    private int rating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        star1 = findViewById(R.id.one_star);
        star2 = findViewById(R.id.two_stars);
        star3 = findViewById(R.id.three_stars);
        star4 = findViewById(R.id.four_stars);
        star5 = findViewById(R.id.five_stars);
        closeButton = findViewById(R.id.close_button);
        submitButton = findViewById(R.id.submit_rating_button);

        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);
        star4.setOnClickListener(this);
        star5.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_button) {
            setResult(0);
            finish();
        } else if (v.getId() == R.id.submit_rating_button) {
            Intent i = new Intent();
            i.putExtra("rating", rating);
            i.putExtra("fid", getIntent().getExtras().getInt("fid"));
            setResult(1, i);
            finish();
        } else {
            rating = 0;
            star5.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            switch (v.getId()) {
                case R.id.five_stars:
                    star5.setImageResource(R.drawable.ic_star_filled);
                    rating++;
                case R.id.four_stars:
                    star4.setImageResource(R.drawable.ic_star_filled);
                    rating++;
                case R.id.three_stars:
                    star3.setImageResource(R.drawable.ic_star_filled);
                    rating++;
                case R.id.two_stars:
                    star2.setImageResource(R.drawable.ic_star_filled);
                    rating++;
                case R.id.one_star:
                    star1.setImageResource(R.drawable.ic_star_filled);
                    rating++;
                    submitButton.setEnabled(true);
                    break;
            }
        }
    }
}
