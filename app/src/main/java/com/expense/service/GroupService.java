package com.expense.service;

import com.expense.model.Group;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GroupService {

    @POST("api/group")
    @Headers("Content-Type: application/json")
    Call<Void> create(@Body Group group);

}
