package com.example.menstrualapp.retrofit;

import com.example.menstrualapp.retrofit.database.PredictionModel;
import com.example.menstrualapp.retrofit.database.UserModel;
import com.example.menstrualapp.retrofit.database.UserPeriodModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET("test/")
    Call<String> testConnet();
    @POST("createuser/")
    Call<UserModel> createUser(@Body UserModel userModel);
    @GET("listuser/")
    Call<List<UserModel>> listUser();
    @GET("oneuser/{pk}")
    Call<UserModel> oneUser(@Path("pk")int pk);

    @PUT("updateuser/{pk}")
    Call<UserModel> updateUser(@Path("pk")int pk,@Body UserModel userModel);

    @POST("createuserperiod/")
    Call<UserPeriodModel> createUserPeriod(@Body UserPeriodModel userPeriodModel);

    @GET("listuserperiod/")
    Call<List<UserPeriodModel>> listUserPeriod(@Query("userid") int userid);

    @GET("predictuserperiod/{pk}")
    Call <PredictionModel> getPredictUserPeriod(@Path("pk") int userid);
}
