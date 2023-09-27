package com.example.daycareapp.repositories;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.daycareapp.util.AuthToken;
import com.example.daycareapp.configs.Config;
import com.example.daycareapp.network.AuthAPIClient;
import com.example.daycareapp.network.request.LoginRequestModel;
import com.example.daycareapp.network.request.RegisterRequestModel;
import com.example.daycareapp.network.response.GoogleLoginResponseModel;
import com.example.daycareapp.network.response.RegisterResponseModel;
import com.example.daycareapp.network.response.UserDetailsResponseModel;
import com.example.daycareapp.network.response.UserResponseModel;
import com.example.daycareapp.network.service.AuthService;
import com.example.daycareapp.network.response.GoogleOAuthLoginResponseModel;
import com.example.daycareapp.util.SharedRefs;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthRepository {
    private AuthService authService;
    SharedRefs sharedRefs;

    public AuthRepository(Context context) {
        authService = AuthAPIClient.getInstance().create(AuthService.class);
        sharedRefs = new SharedRefs(context);
    }

    public LiveData<String> login(String username, String password) {
        MutableLiveData<String> isLoginSuccessful = new MutableLiveData<>();
        if (Config.isLoginOffline) {
            UserResponseModel user =  new UserResponseModel(100, "Alice", "alice@gmail.com");
            sharedRefs.putString(SharedRefs.ACCESS_TOKEN, "DUMMY_ACCESS_TOKEN");
            sharedRefs.putString(SharedRefs.USER_NAME, user.getName());
            sharedRefs.putString(SharedRefs.USER_EMAIL, user.getEmail());
            sharedRefs.putString(SharedRefs.USER_ID, String.valueOf(user.getId()));
            sharedRefs.putString(SharedRefs.USER_ROLE, user.getUserRole());
            isLoginSuccessful.setValue("success");
            return isLoginSuccessful;
        }
        authService.login(new LoginRequestModel(username, password)).enqueue(new Callback<retrofit2.Response<Void>>() {
            @Override
            public void onResponse(Call<retrofit2.Response<Void>> call, retrofit2.Response<retrofit2.Response<Void>> response) {
                Log.d(TAG, "*** Response code =" + response.code());
                Log.d(TAG, "****************************");

                if (response.code() == 200) {
                    String accessToken = response.headers().get(SharedRefs.AUTHORIZATION);
                    authService.getUserDetails(accessToken).enqueue(new Callback<UserDetailsResponseModel>() {
                        @Override
                        public void onResponse(Call<UserDetailsResponseModel> call, retrofit2.Response<UserDetailsResponseModel> response) {
                            if (response.code() == 200) {
                                UserDetailsResponseModel userDetailsResponseModel = response.body();
                                UserResponseModel user = userDetailsResponseModel.getUser();
                                sharedRefs.putString(SharedRefs.ACCESS_TOKEN, accessToken);
                                sharedRefs.putString(SharedRefs.USER_NAME, user.getName());
                                sharedRefs.putString(SharedRefs.USER_EMAIL, user.getEmail());
                                sharedRefs.putString(SharedRefs.USER_ID, String.valueOf(user.getId()));
                                sharedRefs.putString(SharedRefs.IS_VERIFIED, user.isVerified()+"");
                                AuthToken.authToken = accessToken;
                                isLoginSuccessful.setValue("success");
                                sharedRefs.putString(SharedRefs.USER_ROLE, user.getUserRole());

                            }
                            else {
                                isLoginSuccessful.setValue("failed");
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDetailsResponseModel> call, Throwable t) {
                            isLoginSuccessful.setValue("error: " + t);
                        }
                    });
                }
                else {
                    isLoginSuccessful.setValue("Unknown problem!");
                }
            }

            @Override
            public void onFailure(Call<retrofit2.Response<Void>> call, Throwable t) {
                t.printStackTrace();
                isLoginSuccessful.setValue("error: " + t);
            }
        });
        return isLoginSuccessful;
    }

    public LiveData<Boolean> googleOAuthLogin(GoogleLoginResponseModel googleLoginResponseModel) {
        MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();
        authService.googleOAuthLogin(googleLoginResponseModel.getIdToken()).enqueue(new Callback<GoogleOAuthLoginResponseModel>() {
            @Override
            public void onResponse(Call<GoogleOAuthLoginResponseModel> call, retrofit2.Response<GoogleOAuthLoginResponseModel> response) {
                GoogleOAuthLoginResponseModel googleOAuthLoginResponseModel = response.body();
                String status = googleOAuthLoginResponseModel.getStatus();
                if (status.equals("OK")) {
                    String accessToken = googleOAuthLoginResponseModel.getAccessToken();
                    authService.getUserDetails(accessToken).enqueue(new Callback<UserDetailsResponseModel>() {
                        @Override
                        public void onResponse(Call<UserDetailsResponseModel> call, retrofit2.Response<UserDetailsResponseModel> response) {
                            if (response.code() == 200) {
                                UserDetailsResponseModel userDetailsResponseModel = response.body();
                                UserResponseModel user = userDetailsResponseModel.getUser();
                                sharedRefs.putString(SharedRefs.ACCESS_TOKEN, accessToken);
                                sharedRefs.putString(SharedRefs.USER_NAME, user.getName());
                                sharedRefs.putString(SharedRefs.USER_EMAIL, user.getEmail());
                                sharedRefs.putString(SharedRefs.USER_ID, String.valueOf(user.getId()));
                                sharedRefs.putString(SharedRefs.IS_VERIFIED, user.isVerified()+"");
                                isLoginSuccessful.setValue(true);
                            }
                            else {
                                isLoginSuccessful.setValue(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDetailsResponseModel> call, Throwable t) {
                            isLoginSuccessful.setValue(false);
                        }
                    });
                } else {
                    isLoginSuccessful.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<GoogleOAuthLoginResponseModel> call, Throwable t) {
                isLoginSuccessful.setValue(false);
            }
        });
        return isLoginSuccessful;
    }

    public LiveData<String> register(String name, String email, String password) {
        MutableLiveData<String> isRegistrationSuccessful = new MutableLiveData<>();
        String message = "";
        authService.register(new RegisterRequestModel(name, email, password)).enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, retrofit2.Response<RegisterResponseModel> response) {
                RegisterResponseModel registerResponseModel = response.body();
                String status = registerResponseModel.getStatus();
                if (status.equals("OK")) {
                    isRegistrationSuccessful.setValue("success");
                } else {
                    isRegistrationSuccessful.setValue(status + " " + registerResponseModel.getError());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                isRegistrationSuccessful.setValue("Error: " + t.getMessage());
            }
        });
        return isRegistrationSuccessful;
    }
}
