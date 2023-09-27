package com.example.daycareapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.daycareapp.R;
import com.example.daycareapp.adapters.ReviewsListAdapter;
import com.example.daycareapp.models.Review;
import com.example.daycareapp.network.RetrofitAPIClient;
import com.example.daycareapp.network.response.AllReviewResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    List<Review> reviewList;
    Button addReviewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reviewList = new ArrayList<>();
        Long caregiverId = this.getIntent().getLongExtra("caregiver_id", -1);

        addReviewButton = findViewById(R.id.addReviewBt);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddReviewActivity.class);
                intent.putExtra("caregiver_id", caregiverId);
                startActivity(intent);
                finish();
            }
        });

        getReviews(caregiverId);
    }

    private void updateRecyclearView(List<Review> reviewListData) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reviewerRecyclerView);
        ReviewsListAdapter adapter = new ReviewsListAdapter(reviewListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void getReviews(Long caregiverId){
        Call<AllReviewResponse> call = RetrofitAPIClient
                .getInstance()
                .getAPI()
                .findAllReviewByCaregiverId(caregiverId);

        call.enqueue(new Callback<AllReviewResponse>() {
            @Override
            public void onResponse(Call<AllReviewResponse> call, Response<AllReviewResponse> response) {
                AllReviewResponse allReviewResponse = response.body();
                Toast.makeText(ReviewActivity.this, allReviewResponse.getMessage() +", Status: "+allReviewResponse.getStatus() + " " + allReviewResponse.getReviews().size(), Toast.LENGTH_SHORT).show();
                reviewList = allReviewResponse.getReviews();
                updateRecyclearView(reviewList);

            }

            @Override
            public void onFailure(Call<AllReviewResponse> call, Throwable t) {
                Toast.makeText(ReviewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}