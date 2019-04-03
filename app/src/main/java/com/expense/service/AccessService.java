package com.expense.service;

import com.expense.model.TokenResponse;
import com.expense.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccessService {

    @POST("api/signup")
    @Headers("Content-Type: application/json")
    Call<Void> signUp(@Body User user);

    @FormUrlEncoded
    @POST("api/oauth/token")
    Call<TokenResponse> requestAccessToken(
            @Field("client_id") String client,
            @Field("username") String email,
            @Field("password") String password,
            @Field("grant_type") String grantType
    );

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/oauth/token?grant_type=refresh_token&refresh_token={refreshToken}")
    Call<Object> requestRefreshToken(@Path("refreshToken") String refreshToken);

}
