package com.example.daycareapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daycareapp.R;
import com.example.daycareapp.models.Review;
import com.example.daycareapp.network.RetrofitAPIClient;
import com.example.daycareapp.network.response.DefaultResponse;
import com.example.daycareapp.network.service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReviewActivity extends AppCompatActivity {

    private EditText reviewTitleEt, reviewBodyEt;
    private Button reviewSubmitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        Long caregiverId = this.getIntent().getLongExtra("caregiver_id", -1);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reviewTitleEt = findViewById(R.id.reviewTitleEt);
        reviewBodyEt = findViewById(R.id.reviewBodyEt);
        reviewSubmitButton = findViewById(R.id.addReview);

        reviewSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isValid = checkValidity(reviewTitleEt, reviewBodyEt);
                if(isValid){
                    Review review = new Review();
                    review.setTitle(reviewTitleEt.getText().toString());
                    review.setBody(reviewBodyEt.getText().toString());
                    review.setCaregiverId(caregiverId);
                    addReview(review);
                }
            }
        });
    }

    private void addReview(Review review) {
        Call<DefaultResponse> call = RetrofitAPIClient
                .getInstance()
                .getAPI().addReview(review);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        Toast.makeText(AddReviewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ProtectedActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(AddReviewActivity.this, "Unknown problem occours", Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(AddReviewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean checkValidity(EditText reviewTitleEt, EditText reviewBodyEt) {
        if(reviewTitleEt.getText().toString().equals("")){
            reviewTitleEt.setError("Enter first");
            reviewTitleEt.requestFocus();
            return false;
        }
        if(reviewBodyEt.getText().toString().equals("")){
            reviewBodyEt.setError("Enter body");
            reviewBodyEt.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}