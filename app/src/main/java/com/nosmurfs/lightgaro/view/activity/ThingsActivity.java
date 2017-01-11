package com.nosmurfs.lightgaro.view.activity;

import android.content.Context;

import com.nosmurfs.lightgaro.R;
import com.nosmurfs.lightgaro.presenter.Presenter;
import com.nosmurfs.lightgaro.presenter.ThingsPresenter;
import com.nosmurfs.lightgaro.view.activity.RootActivity;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsActivity extends RootActivity implements ThingsPresenter.View{

    // TODO: 11/01/2017 DI
    private ThingsPresenter thingsPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_things;
    }

    @Override
    public Presenter getPresenter() {
        return thingsPresenter;
    }

    @Override
    protected void initializeInjector() {
        // TODO: 11/01/2017
    }

    @Override
    protected void initializeUI() {
        // TODO: 11/01/2017
    }

    @Override
    protected void initializePresenter() {
        thingsPresenter = new ThingsPresenter();
        thingsPresenter.start(this);
    }

    @Override
    protected void registerListeners() {
        // TODO: 11/01/2017
    }
}
