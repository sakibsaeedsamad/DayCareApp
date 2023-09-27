package com.example.daycareapp.network.service;

import com.example.daycareapp.models.Caregiver;
import com.example.daycareapp.models.Review;
import com.example.daycareapp.models.User;
import com.example.daycareapp.models.Baby;
import com.example.daycareapp.models.Order;
import com.example.daycareapp.network.response.AccountVerifyResponse;
import com.example.daycareapp.network.response.AddBabyResponse;
import com.example.daycareapp.network.response.AddCaregiverResponse;
import com.example.daycareapp.network.response.AllBabyResponse;
import com.example.daycareapp.network.response.AllCaregiverResponse;
import com.example.daycareapp.network.response.AllOrderResponse;
import com.example.daycareapp.network.response.AllReviewResponse;
import com.example.daycareapp.network.response.CreateOrderResponse;
import com.example.daycareapp.network.response.DefaultResponse;
import com.example.daycareapp.network.response.RegisterResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @GET("findAllCaregiver")
    Call<AllCaregiverResponse> findAllCaregiver();

    @GET("findAllBaby")
    Call<AllBabyResponse> findAllBaby();

    @GET("findAllOrder")
    Call<AllOrderResponse> findAllOrder();

    @POST("addBaby")
    Call<AddBabyResponse> addBaby(@Body Baby baby);

    @POST("addOrder")
    Call<CreateOrderResponse> createOrder(@Body Order order);

    @GET("verifypin/{pin}")
    Call<AccountVerifyResponse> veryfyPin(@Path("pin") String pin);

    @POST("addCaregiver")
    Call<AddCaregiverResponse> addCaregiver(@Body Caregiver caregiver);

    @POST("deleteCaregiver/{id}")
    Call<DefaultResponse> deleteCaregiver(@Path("id") Long id);

    @POST("deleteBaby/{id}")
    Call<DefaultResponse> deleteBaby(@Path("id") Long id);

    @GET("findAllReviewCaregiver/{id}")
    Call<AllReviewResponse> findAllReviewByCaregiverId(@Path("id") Long id);

    @POST("addReview")
    Call<DefaultResponse> addReview(@Body Review review);
}
