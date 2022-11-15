package com.superhero.ui.activity_orders.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.google.gson.Gson;
import com.superhero.R;
import com.superhero.adapters.MyOrdersAdapter;
import com.superhero.databinding.FragmentOrdersBinding;
import com.superhero.models.OrderDataModel;
import com.superhero.models.OrderModel;
import com.superhero.models.SendOrderBody;
import com.superhero.models.UserModel;
import com.superhero.preferences.Preferences;
import com.superhero.remote.Api;
import com.superhero.tags.Tags;
import com.superhero.ui.activity_orders.OrdersActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Current_Order extends Fragment {
    private OrdersActivity activity;
    private FragmentOrdersBinding binding;
    private UserModel userModel;
    private Preferences preferences;
    private int current_page = 1;
    private boolean isLoading = false;
    private MyOrdersAdapter adapter;
    private List<OrderModel> orderModelList;


    public static Fragment_Current_Order newInstance()
    {
        return new Fragment_Current_Order();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        orderModelList = new ArrayList<>();
        activity = (OrdersActivity) getActivity();
        userModel = preferences.getUserData(activity);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new MyOrdersAdapter(orderModelList,activity,this);
        binding.recView.setAdapter(adapter);


        getOrder();
    }


    public void getOrder()
    {
        SendOrderBody sendOrderBody=new SendOrderBody();
        sendOrderBody.setId(userModel.getResult().getUser_id().get(0));
        sendOrderBody.getParams().setSales_id(Integer.parseInt(userModel.getResult().getUser_id().get(0)));
        Gson gson = new Gson();
        String user_data = gson.toJson(sendOrderBody);
        Log.e("jjj",user_data);
        orderModelList.clear();
        //orderModelList.addAll(response.body().getData());
        adapter.notifyDataSetChanged();

        Api.getService(Tags.base_url)
                .getnewOrders(sendOrderBody)
                .enqueue(new Callback<OrderDataModel>() {
                    @Override
                    public void onResponse(Call<OrderDataModel> call, Response<OrderDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null ) {
                            orderModelList.clear();
                            orderModelList.addAll(response.body().getResult().getData());
                            adapter.notifyDataSetChanged();

                            if (orderModelList.size() > 0) {
                                binding.tvNoOrder.setVisibility(View.GONE);
                            } else {
                                binding.tvNoOrder.setVisibility(View.VISIBLE);

                            }
                        } else {

                            try {

                                Log.e("error", response.code() + "_" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (response.code() == 500) {
                                Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDataModel> call, Throwable t) {
                        try {
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                   // Toast.makeText(activity, R.string.something, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                });
    }


    public void DisplayOrderDetials(OrderModel id) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(id.getLatitude()), Double.parseDouble(id.getLongitude()));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        startActivity(intent);
//        Intent intent = new Intent(activity, OrderDetialsActivity.class);
//        intent.putExtra("orderid",id+"");
//
//        startActivity(intent);
     //   activity.finish();
    }

}
