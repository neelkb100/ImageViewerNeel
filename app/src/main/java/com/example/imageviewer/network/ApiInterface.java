package com.example.imageviewer.network;

import com.example.imageviewer.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiInterface {


    @GET("list/")
    Call<List<ResponseModel>> getList();

//    @GET("list/")
//    Call<List<ResponseModel>> getSingleImageList();
}
