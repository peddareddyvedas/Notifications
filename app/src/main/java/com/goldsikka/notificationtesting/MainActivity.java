package com.goldsikka.notificationtesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

/*
    <Coffee App: An example app of a Coffee Application.>
    Copyright (C) <2022>  <Cromega>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

* */

public class MainActivity extends AppCompatActivity {

    final float COFFEE_COST = 2.50f,SWEET_COST = 1.00f, SPICED_COST=1.25f,CREAMY_COST = 1.75f;
    float total = 0;
    String client, toppings = "Toppings: ", lastOrder = "";
    String[] notificationChannelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationChannelData = getResources().getStringArray(R.array.notification_channel);
        createNotificationChannel();
    }

    public void check(View view) {
        CheckBox checkBox = (CheckBox) view;
        int checkBoxId = checkBox.getId();
        boolean checked = checkBox.isChecked();
        String text = checkBox.getText().toString(),
                type;
        if (checkBoxId == -1) {
            LinearLayout layout = (LinearLayout) checkBox.getParent().getParent();
            type = getResources().getResourceEntryName(layout.getId());
        }
        else {type = getResources().getResourceEntryName(checkBoxId);}

        float toppingValue = type.startsWith("sweet_")? SWEET_COST:
                type.startsWith("spiced_")? SPICED_COST:CREAMY_COST;

        if (checked) {
            toppings += String.format("%s, ", text);
            total += toppingValue;
        }
        else {
            toppings = toppings.replaceAll(String.format("%s, ", text), "");
            total -= toppingValue;
        }
    }

    public void order(View view) {
        EditText username = findViewById(R.id.username);
        String usernameInput = username.getText().toString();
        client = usernameInput.equals("")? "Customer":usernameInput;
        total += COFFEE_COST;
        toppings = toppings.equals("Toppings: ")? String.format("%sNone.", toppings):
                String.format("%s.", toppings.substring(0, toppings.length() - 2));
        createNotification();
        resetOrder();
    }

    private void resetOrder() {
        total -= COFFEE_COST;
        toppings = toppings.equals("Toppings: None.")? "Toppings: ":String.format("%s, ", toppings.substring(0, toppings.length() - 1));
        lastOrder = toppings;
    }

    private void createNotification() {
        @SuppressLint("DefaultLocale")
        String toPrint = String.format(notificationChannelData[3], client, total, toppings);
        @SuppressLint("DefaultLocale")
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, notificationChannelData[0])
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(notificationChannelData[1])
                .setContentText(toPrint)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(toPrint));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel
                    (notificationChannelData[0],
                            notificationChannelData[1],
                            NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            channel.setDescription(notificationChannelData[2]);
            notificationManager.createNotificationChannel(channel);
        }
    }
}


/*
package com.goldsikka.notificationtesting;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.goldsikka.notificationtesting.PushNotification.Config;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity {
    String mToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      */
/*  Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tkn = FirebaseInstanceId.getInstance().getToken();
                Toast.makeText(MainActivity.this, "Current token ["+tkn+"]", Toast.LENGTH_LONG).show();
                Log.d("App", "Token ["+tkn+"]");
            }
        });*//*


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                mToken = instanceIdResult.getToken();
                Log.e("Token", "" + mToken);
            }
        });


    }

    public void noto2() // paste in activity
    {
        Notification.Builder notif;
        NotificationManager nm;
        notif = new Notification.Builder(getApplicationContext());
        notif.setSmallIcon(R.drawable.appicon);
        notif.setContentTitle("");
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notif.setSound(path);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent yesReceive = new Intent();
        yesReceive.setAction(Config.YES_ACTION);
        PendingIntent pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.appicon, "Yes", pendingIntentYes);


        Intent yesReceive2 = new Intent();
        yesReceive2.setAction(Config.STOP_ACTION);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.addAction(R.drawable.appicon, "No", pendingIntentYes2);


        nm.notify(10, notif.getNotification());
    }

}*/
