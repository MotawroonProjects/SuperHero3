package com.superhero.mvp.activity_login_presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import com.superhero.R;
import com.superhero.models.LoginBody;
import com.superhero.models.LoginModel;
import com.superhero.models.UserModel;
import com.superhero.preferences.Preferences;
import com.superhero.remote.Api;
import com.superhero.share.Common;
import com.superhero.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLoginPresenter {
    private Context context;
    private ActivityLoginView view;
    private Preferences preferences;

    public ActivityLoginPresenter(Context context, ActivityLoginView view) {
        this.context = context;
        this.view = view;
        preferences  =Preferences.getInstance();
    }

    public void checkData(LoginModel model) {
        if (model.isDataValid(context)) {
            login(model);
        }
    }

    private void login(LoginModel model) {
        try {
            LoginBody loginBody=new LoginBody();
            loginBody.getParams().setLogin(model.getEmail());
            loginBody.getParams().setPassword(model.getPassword());
            ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .login(loginBody)
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            dialog.dismiss();
                            if (response.isSuccessful() &&response.body()!=null) {
                               // Log.e("cccccccc",response.body().getData().getToken()+"---");
                                view.onSuccess(response.body());
                            }
//                            else if (response.isSuccessful() &&response.body()!=null&&response.body().getStatus()==404) {
//                                view.onUserNoFound();
//                            }
                            else {
                                try {
                                    Log.e("mmmmmmmmmm", response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                if (response.code() == 500) {

                                }  else {
                                    view.onFailed(context.getString(R.string.failed));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            try {
                                dialog.dismiss();
                                if (t.getMessage() != null) {
                                    Log.e("msg_category_error", t.getMessage() + "__");

                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        view.onFailed(t.getMessage().toLowerCase());
                                        //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                    } else {
                                        view.onFailed(context.getString(R.string.failed));
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Error", e.getMessage() + "__");
                            }
                        }
                    });
        }catch (Exception e){}
    }
}
