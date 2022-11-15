package com.superhero.mvp.activity_login_presenter;


import com.superhero.models.UserModel;

public interface ActivityLoginView {
    void onSuccess(UserModel userModel);
    void onFailed(String msg);

    void onUserNoFound();
}
