package mx.itesm.lab3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Context context;
    public int NOTIFICATION_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        //make the phone vibrate and output a toast notification
        Button button_vib = findViewById(R.id.button_vibrate);
        button_vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long pattern[] = {0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500};
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(pattern, -1);
                //toast notification
                Toast vibrateToast = new Toast(context);
                vibrateToast.makeText(context, "Your phone is vibrating MF!", Toast.LENGTH_LONG).show();
            }
        });

        //activate the proximity sensor and after reaching a certain distance open a dialogue: "You're close"
        Button button_prox = findViewById(R.id.button_proximity);
        button_prox.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent proxi = new Intent(context, proximity_activity.class);
                startActivity(proxi);
            }
        });

        final NotificationCompat.Builder NotifBuilder = new NotificationCompat.Builder(this);

        Button button_barNot = findViewById(R.id.button_bar);
        button_barNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch a bar notification 
                NotifBuilder.setSmallIcon(R.mipmap.ic_launcher);
                NotifBuilder.setContentTitle("Bar notification");
                NotifBuilder.setContentText("This is your bar notification MF!");
                NotifBuilder.setChannelId("notify");        //for API 26

                NotificationManager MyNotification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                //for API 26
                NotificationChannel channel = new NotificationChannel("notify","Channel human readable title", MyNotification.IMPORTANCE_DEFAULT);
                MyNotification.createNotificationChannel(channel);
                //until here
                MyNotification.notify(NOTIFICATION_ID, NotifBuilder.build());
            }
        });
    }
}
