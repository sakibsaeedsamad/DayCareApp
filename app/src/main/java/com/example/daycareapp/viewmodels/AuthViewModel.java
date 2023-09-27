package com.example.daycareapp.viewmodels;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.daycareapp.network.response.GoogleLoginResponseModel;
import com.example.daycareapp.repositories.AuthRepository;


public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public LiveData<String> login(String username, String password) {
        Log.d(TAG, "*****viewmodel " + username +" "+ password);
        return authRepository.login(username, password);
    }

    public LiveData<Boolean> googleOAuthLogin(GoogleLoginResponseModel googleLoginResponseModel) {
        return authRepository.googleOAuthLogin(googleLoginResponseModel);
    }

    public LiveData<String> register(String name, String username, String password) {
        return authRepository.register(name, username, password);
    }
}
