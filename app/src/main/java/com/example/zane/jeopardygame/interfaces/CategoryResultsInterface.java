package com.example.zane.jeopardygame.interfaces;

import com.example.zane.jeopardygame.model.Categories;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface CategoryResultsInterface {

    @Headers("Content-Type: application/json")
    @GET
    Call<ArrayList<Categories>> getData(@Url String url);
}
