package com.nosmurfs.lightgaro.view.activity;

import com.nosmurfs.lightgaro.R;
import com.nosmurfs.lightgaro.presenter.Presenter;
import com.nosmurfs.lightgaro.view.activity.RootActivity;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsActivity extends RootActivity {



    @Override
    public int getLayoutId() {
        return R.layout.activity_things;
    }

    @Override
    public Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initializeInjector() {

    }

    @Override
    protected void initializeUI() {

    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void registerListeners() {

    }
}