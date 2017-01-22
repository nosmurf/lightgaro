package com.nosmurfs.lightgaro.view.activity;

import com.nosmurfs.lightgaro.R;
import com.nosmurfs.lightgaro.presenter.Presenter;
import com.nosmurfs.lightgaro.presenter.ThingsPresenter;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsActivity extends RootActivity implements ThingsPresenter.View {

    // TODO: 11/01/2017 DI
    private ThingsPresenter thingsPresenter;

    private ArrayList<TextView> relays;

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
        TextView relay1 = (TextView) findViewById(R.id.relay_1);
        TextView relay2 = (TextView) findViewById(R.id.relay_2);
        TextView relay3 = (TextView) findViewById(R.id.relay_3);
        TextView relay4 = (TextView) findViewById(R.id.relay_4);
        TextView relay5 = (TextView) findViewById(R.id.relay_5);
        TextView relay6 = (TextView) findViewById(R.id.relay_6);
        TextView relay7 = (TextView) findViewById(R.id.relay_7);
        TextView relay8 = (TextView) findViewById(R.id.relay_8);

        relays = new ArrayList<>();
        relays.add(relay1);
        relays.add(relay2);
        relays.add(relay3);
        relays.add(relay4);
        relays.add(relay5);
        relays.add(relay6);
        relays.add(relay7);
        relays.add(relay8);
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
    public void showConnectionInformation(List<String> relayNames) {
        for (int index = 0; index < this.relays.size(); index++) {
            this.relays.get(0).setText(relayNames.get(index));
        }
    }
}
