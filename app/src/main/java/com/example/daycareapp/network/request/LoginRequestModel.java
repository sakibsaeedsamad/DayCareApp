package com.example.daycareapp.network.request;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LoginRequestModel(String email, String password) {
        Log.d(TAG, "*** log req mod " + email);

        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
