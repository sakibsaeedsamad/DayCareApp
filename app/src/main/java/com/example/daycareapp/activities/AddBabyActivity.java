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
import com.example.daycareapp.fragments.BabyFragment;
import com.example.daycareapp.network.RetrofitAPIClient;
import com.example.daycareapp.models.Baby;
import com.example.daycareapp.network.response.AddBabyResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBabyActivity extends AppCompatActivity {
    EditText babyNameEditText, babyAgeEditText, motherNameEditText, fatherNameEditText, contactNumberEditText, addressEditText, fevoriteFoodEditText;
    Button addBaby;
    Long caregiverId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);

        caregiverId = this.getIntent().getLongExtra("caregiver_id", -1);


        babyNameEditText = findViewById(R.id.editTextBabyName);
        babyAgeEditText = findViewById(R.id.editTextAge);
        motherNameEditText = findViewById(R.id.editTextMotherName);
        fatherNameEditText = findViewById(R.id.editTextFatherName);
        contactNumberEditText = findViewById(R.id.editTextMobile);
        addressEditText = findViewById(R.id.editTextAddress);
        fevoriteFoodEditText = findViewById(R.id.editTextfevoriteFood);

        addBaby = findViewById(R.id.circularRegisterButton);
        addBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Baby baby = new Baby();
                boolean isValidForm = checkValidity(babyNameEditText, babyAgeEditText, motherNameEditText, fatherNameEditText, contactNumberEditText, addressEditText, fevoriteFoodEditText);

                if(isValidForm){
                    baby.setBabyName(babyNameEditText.getText().toString());
                    baby.setAddress(addressEditText.getText().toString());
                    baby.setFevoriteFood(fevoriteFoodEditText.getText().toString());
                    baby.setBabyAge(Integer.parseInt(babyAgeEditText.getText().toString()));
                    baby.setFatherName(fatherNameEditText.getText().toString());
                    baby.setMotherName(motherNameEditText.getText().toString());
                    baby.setContactNumber(contactNumberEditText.getText().toString());
                }
                addBaby(baby);
            }
        });
    }

    private boolean checkValidity(EditText babyNameEditText, EditText babyAgeEditText, EditText motherNameEditText, EditText fatherNameEditText, EditText contactNumberEditText, EditText addressEditText, EditText fevoriteFoodEditText) {
        if(babyNameEditText.getText().toString().equals("") ){
            babyNameEditText.setError("Enter Name first!");
            babyNameEditText.requestFocus();
            return false;
        }
        if(babyAgeEditText.getText().toString().equals("")){
            babyAgeEditText.setError("Baby should not empty");
            babyAgeEditText.requestFocus();
            return false;
        }else {
            int babyAge = Integer.parseInt(babyAgeEditText.getText().toString());
            if(babyAge<2 && babyAge>=4){
                babyAgeEditText.setError("Baby should be 2-4 years");
                babyAgeEditText.requestFocus();
                return false;
            }
        }

        if(motherNameEditText.getText().toString().equals("") ){
            motherNameEditText.setError("Enter mother name first!");
            motherNameEditText.requestFocus();
            return false;
        }
        if(fatherNameEditText.getText().toString().equals("") ){
            fatherNameEditText.setError("Enter Father Name first!");
            fatherNameEditText.requestFocus();
            return false;
        }

        if(contactNumberEditText.getText().toString().equals("") ){
            contactNumberEditText.setError("Enter contact number first!");
            contactNumberEditText.requestFocus();
            return false;
        }

        if(addressEditText.getText().toString().equals("") ){
            addressEditText.setError("Enter address first!");
            addressEditText.requestFocus();
            return false;
        }

        if(fevoriteFoodEditText.getText().toString().equals("") ){
            fevoriteFoodEditText.setError("Enter fevorite food first!");
            fevoriteFoodEditText.requestFocus();
            return false;
        }
        return true;
    }

    private void addBaby(Baby baby) {
//        progressBar.setVisibility(View.VISIBLE);
        Call<AddBabyResponse> call = RetrofitAPIClient
                .getInstance()
                .getAPI()
                .addBaby(baby);

        call.enqueue(new Callback<AddBabyResponse>() {
            @Override
            public void onResponse(Call<AddBabyResponse> call, Response<AddBabyResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    String message = response.body().getStatus();
                    Toast.makeText(getApplicationContext(), "Successfully added baby! status: "+message, Toast.LENGTH_LONG).show();
                    if(caregiverId==-1){
                        Intent intent = new Intent(getApplicationContext(), BabyActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), DateActivity.class);
                        Long babyId = response.body().getBaby().getId();
                        intent.putExtra("caregiver_id", caregiverId);
                        intent.putExtra("baby_id", babyId);
                        startActivity(intent);
                    }

                } else {
//                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Some unknown problem occurred!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddBabyResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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