package com.superhero.ui.activity_login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import com.superhero.R;
import com.superhero.databinding.ActivityLoginBinding;
import com.superhero.language.Language;
import com.superhero.models.LoginModel;
import com.superhero.models.UserModel;
import com.superhero.mvp.activity_login_presenter.ActivityLoginPresenter;
import com.superhero.mvp.activity_login_presenter.ActivityLoginView;
import com.superhero.preferences.Preferences;
import com.superhero.ui.activity_orders.OrdersActivity;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements ActivityLoginView {
    private ActivityLoginBinding binding;
    private LoginModel model;
    private ActivityLoginPresenter presenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }


    private void initView() {
        model = new LoginModel();
//        binding.tv1.setText(Html.fromHtml(getString(R.string.login2)));
//        binding.tvSignUp.setText(Html.fromHtml(getString(R.string.donot_have_account)));
        binding.setModel(model);
        presenter = new ActivityLoginPresenter(this, this);
        binding.btnLogin.setOnClickListener(view -> {
            presenter.checkData(model);
        });


    }



    @Override
    public void onSuccess(UserModel userModel) {
        Preferences.getInstance().create_update_userdata(this,userModel);
        Intent intent = new Intent(this, OrdersActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNoFound() {
        Toast.makeText(this,getResources().getString(R.string.phone_pass_inc),Toast.LENGTH_LONG).show();
    }
}