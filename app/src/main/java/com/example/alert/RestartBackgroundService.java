package com.example.alert;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.alert.Listener.Dispatcher;
import com.example.alert.Listener.Server;
import com.example.alert.Sender.Client;

public class RestartBackgroundService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("d1", "service stopped. Restarting....");

        //Dispatcher
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.start();

        // Client listener
        Server server = new Server(dispatcher);

        //Start client sender
        Client client = new Client();

        context.startService(new Intent(context, BackgroundService.class));
    }
}
