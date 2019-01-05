package com.example.zane.jeopardygame;

import com.example.zane.jeopardygame.model.Categories;
import com.example.zane.jeopardygame.model.ClueResults;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface CluesInterface {
    @Headers("Content-Type: application/json")
    @GET
    Call<ClueResults> getData(@Url String url);
}
