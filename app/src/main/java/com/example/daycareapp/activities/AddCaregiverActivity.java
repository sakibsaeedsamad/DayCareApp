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

import com.example.daycareapp.constants.CaregiverConstant;
import com.example.daycareapp.R;
import com.example.daycareapp.models.Caregiver;
import com.example.daycareapp.network.RetrofitAPIClient;
import com.example.daycareapp.network.response.AddCaregiverResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCaregiverActivity extends AppCompatActivity {

    EditText caregiverMotherName, address, speciality, adminFeedBack;
    Button addCareGiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_caregiver);

        caregiverMotherName = findViewById(R.id.caregiverMotherName);
        address = findViewById(R.id.address);
        speciality = findViewById(R.id.speciality);
        adminFeedBack = findViewById(R.id.adminFeedBack);

        addCareGiver = findViewById(R.id.addCaregiver);

        addCareGiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = checkValidity(caregiverMotherName, address, speciality, adminFeedBack);
                if(isValid){
                    Caregiver caregiver = new Caregiver();
                    caregiver.setCaregiverMotherName(caregiverMotherName.getText().toString());
                    caregiver.setAddress(address.getText().toString());
                    caregiver.setSpeciality(speciality.getText().toString());
                    caregiver.setAdminFeedBack(adminFeedBack.getText().toString());
                    addCaregiverCall(caregiver);
                }
            }
        });

    }

    private void addCaregiverCall(Caregiver caregiver) {
        Call<AddCaregiverResponse> call = RetrofitAPIClient
                .getInstance()
                .getAPI()
                .addCaregiver(caregiver);

        call.enqueue(new Callback<AddCaregiverResponse>() {
            @Override
            public void onResponse(Call<AddCaregiverResponse> call, Response<AddCaregiverResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    AddCaregiverResponse addCaregiverResponse = response.body();
                    Toast.makeText(getApplicationContext(), addCaregiverResponse.getMessage() + "Status: " + addCaregiverResponse.getStatus() , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ProtectedActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Some unknown problem occurred!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddCaregiverResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkValidity(EditText caregiverMotherName, EditText address, EditText speciality, EditText adminFeedBack) {
        if(caregiverMotherName.getText().toString().equals("")){
            caregiverMotherName.setError("Enter First");
            caregiverMotherName.requestFocus();
            return false;
        }if(address.getText().toString().equals("")){
            address.setError("Enter address First");
            address.requestFocus();
            return false;
        }if(speciality.getText().toString().equals("")){
            speciality.setError("Enter speciality first");
            speciality.requestFocus();
            return false;
        }if(adminFeedBack.getText().toString().equals("")){
            speciality.setError("Enter admin feedback");
            speciality.requestFocus();
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