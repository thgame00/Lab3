package mx.itesm.lab3;

import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class proximity_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_activity);

        SensorManager sMgr =  (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor proximitySensor = sMgr.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        final AlertDialog.Builder DialogConf = new AlertDialog.Builder(this);
        DialogConf.setTitle("Warning");
        DialogConf.setMessage("You are too close MF!");
        DialogConf.setIcon(R.mipmap.ic_launcher);
        DialogConf.setNeutralButton("Sorry, didn't know that", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do whatever here
            }
        });

        //if no proximity sensor is available
        if(proximitySensor == null) {}

        SensorEventListener proxSenList = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {    //event from here used in if
                if(event.values[0] < proximitySensor.getMaximumRange()) {
                    //in case something is near
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    AlertDialog myDialog = DialogConf.create();
                    myDialog.show();
                }
                else {
                    //nothing is near
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sMgr.registerListener(proxSenList, proximitySensor, sMgr.SENSOR_DELAY_NORMAL);
    }
}
