package com.superhero.services;


import com.superhero.models.LoginBody;
import com.superhero.models.OrderDataModel;
import com.superhero.models.SendOrderBody;
import com.superhero.models.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {


    @POST("web/session/authenticate")
    Call<UserModel> login(

            @Body LoginBody loginBody


    );
    @POST("get_sales_pipline")
    Call<OrderDataModel> getnewOrders(

            @Body SendOrderBody sendOrderBody


    );


}
