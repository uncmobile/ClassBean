package edu.unc.nirjon.classbean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.ScratchBank;

public class MainActivity extends AppCompatActivity {

    Bean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BeanManager.getInstance().startDiscovery(bdl);
    }

    BeanDiscoveryListener bdl = new BeanDiscoveryListener() {
        @Override
        public void onBeanDiscovered(Bean bean, int rssi) {
            Log.v("TT", "" + bean.getDevice() + ", " + rssi);
            b = bean;
        }

        @Override
        public void onDiscoveryComplete() {
            b.connect(getApplicationContext(), blsnr);
        }
    };

    BeanListener blsnr = new BeanListener() {
        @Override
        public void onConnected() {

            Log.v("TT", "We are connected to: " + b.getDevice().getName());

            b.readAcceleration(new Callback<Acceleration>() {
                @Override
                public void onResult(Acceleration result) {
                    Log.v("TT", "" + result.x() + ", " + result.y() + ", " + result.z());
                }
            });
        }

        @Override
        public void onConnectionFailed() {

        }

        @Override
        public void onDisconnected() {

        }

        @Override
        public void onSerialMessageReceived(byte[] data) {

        }

        @Override
        public void onScratchValueChanged(ScratchBank bank, byte[] value) {

        }

        @Override
        public void onError(BeanError error) {

        }
    };

}
