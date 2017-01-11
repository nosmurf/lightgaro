package com.nosmurfs.lightgaro.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nosmurfs.lightgaro.presenter.Presenter;

public abstract class RootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        initializeInjector();
        initializeUI();
        initializePresenter();
        registerListeners();
    }

    public abstract int getLayoutId();

    public abstract Presenter getPresenter();

    protected abstract void initializeInjector();

    protected abstract void initializeUI();

    protected abstract void initializePresenter();

    protected abstract void registerListeners();

//    TODO: 11/01/2017 implement DI!
//    protected AppComponent getAppComponent() {
//        return ((SecurityHomeKeyApplication) getApplication()).getAppComponent();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().destroy();
    }
}