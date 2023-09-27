package com.example.daycareapp.network.service;

import com.example.daycareapp.network.request.LoginRequestModel;
import com.example.daycareapp.network.request.RegisterRequestModel;
import com.example.daycareapp.network.response.GoogleOAuthLoginResponseModel;
import com.example.daycareapp.network.response.RegisterResponseModel;
import com.example.daycareapp.network.response.UserDetailsResponseModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthService {
    @POST("login")
    @Headers({"Content-Type: application/json"})
    Call<Response<Void>> login(@Body LoginRequestModel loginRequestModel);

    @GET("me")
    @Headers({"Content-Type: application/json"})
    Call<UserDetailsResponseModel> getUserDetails(@Header("Authorization") String accessToken);

    @GET("google-oauth-login")
    @Headers({"Content-Type: application/json"})
    Call<GoogleOAuthLoginResponseModel> googleOAuthLogin(@Query("idToken") String googleIdToken);

    @POST("register")
    @Headers({"Content-Type: application/json"})
    Call<RegisterResponseModel> register(@Body RegisterRequestModel registerRequestModel);
}
