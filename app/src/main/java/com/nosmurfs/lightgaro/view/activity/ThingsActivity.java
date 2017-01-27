package com.nosmurfs.lightgaro.view.activity;

import com.bumptech.glide.Glide;
import com.nosmurfs.lightgaro.R;
import com.nosmurfs.lightgaro.model.Relay;
import com.nosmurfs.lightgaro.presenter.Presenter;
import com.nosmurfs.lightgaro.presenter.ThingsPresenter;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 11/01/2017.
 */

public class ThingsActivity extends RootActivity implements ThingsPresenter.View {

    // TODO: 11/01/2017 DI
    private ThingsPresenter thingsPresenter;

    private ArrayList<TextView> relays;

    private ArrayList<ImageView> relayLamps;

    private ImageView qrCode;

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

        ImageView relay1Lamp = (ImageView) findViewById(R.id.relay_1_lamp);
        ImageView relay2Lamp = (ImageView) findViewById(R.id.relay_2_lamp);
        ImageView relay3Lamp = (ImageView) findViewById(R.id.relay_3_lamp);
        ImageView relay4Lamp = (ImageView) findViewById(R.id.relay_4_lamp);
        ImageView relay5Lamp = (ImageView) findViewById(R.id.relay_5_lamp);
        ImageView relay6Lamp = (ImageView) findViewById(R.id.relay_6_lamp);
        ImageView relay7Lamp = (ImageView) findViewById(R.id.relay_7_lamp);
        ImageView relay8Lamp = (ImageView) findViewById(R.id.relay_8_lamp);

        relayLamps = new ArrayList<>();
        relayLamps.add(relay1Lamp);
        relayLamps.add(relay2Lamp);
        relayLamps.add(relay3Lamp);
        relayLamps.add(relay4Lamp);
        relayLamps.add(relay5Lamp);
        relayLamps.add(relay6Lamp);
        relayLamps.add(relay7Lamp);
        relayLamps.add(relay8Lamp);

        qrCode = (ImageView) findViewById(R.id.qr_code);
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
    public void showConnectionInformation(List<Relay> relayNames) {
        try {
            for (int index = 0; index < this.relays.size(); index++) {
                Relay relay = relayNames.get(index);
                this.relays.get(index).setText(relay.getLabel());
                this.relayLamps.get(index).setBackgroundResource(relay.getGpio().getValue() ? R.drawable.on : R.drawable.off);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayUniqueId(String uniqueId) {
        Glide.with(this)
                .load("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + uniqueId)
                .into(qrCode);
    }
}
