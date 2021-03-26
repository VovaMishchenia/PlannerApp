package com.example.plannerapp.network;

import com.example.plannerapp.dto.LoginDTO;
import com.example.plannerapp.dto.LoginResultDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiAccount {
    @POST("/api/account/login")
    public Call<LoginResultDTO> login(@Body LoginDTO loginDTO);
}
