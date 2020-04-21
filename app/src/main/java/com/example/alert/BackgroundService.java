package com.example.alert;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.alert.Listener.Server;
import com.example.alert.Sender.Client;

import java.net.ServerSocket;

public class BackgroundService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("d1", "It is destroyed !!");
        Intent broadcastIntent = new Intent(this, RestartBackgroundService.class);
        sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d("d1", "Server starting from background");
        Server server = Server.getServerInstance();
        server.start();
        Log.d("d1", "client starting from background");
        Client client = Client.getClientInstance();
        client.start();
        return START_STICKY;
    }
}

