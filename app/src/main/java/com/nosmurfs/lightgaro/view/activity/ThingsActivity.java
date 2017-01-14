package com.nosmurfs.lightgaro.view.activity;

import android.widget.TextView;

import com.nosmurfs.lightgaro.R;
import com.nosmurfs.lightgaro.presenter.Presenter;
import com.nosmurfs.lightgaro.presenter.ThingsPresenter;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsActivity extends RootActivity implements ThingsPresenter.View{

    // TODO: 11/01/2017 DI
    private ThingsPresenter thingsPresenter;

    TextView relay1;

    TextView relay2;

    TextView relay3;

    TextView relay4;

    TextView relay5;

    TextView relay6;

    TextView relay7;

    TextView relay8;

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
        relay1 = (TextView) findViewById(R.id.relay_1);
        relay2 = (TextView) findViewById(R.id.relay_2);
        relay3 = (TextView) findViewById(R.id.relay_3);
        relay4 = (TextView) findViewById(R.id.relay_4);
        relay5 = (TextView) findViewById(R.id.relay_5);
        relay6 = (TextView) findViewById(R.id.relay_6);
        relay7 = (TextView) findViewById(R.id.relay_7);
        relay8 = (TextView) findViewById(R.id.relay_8);
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

    @Override
    public void showConnectionInformation(String relay1, String relay2, String relay3, String relay4, String relay5, String relay6, String relay7, String relay8) {
        this.relay1.setText(relay1);
        this.relay2.setText(relay2);
        this.relay3.setText(relay3);
        this.relay4.setText(relay4);
        this.relay5.setText(relay5);
        this.relay6.setText(relay6);
        this.relay7.setText(relay7);
        this.relay8.setText(relay8);
    }
}
