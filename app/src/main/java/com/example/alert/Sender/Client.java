package com.example.alert.Sender;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    private Activity activity = null;
    private static Client client;
    public ClientSender sender;

    public Client() {
        client = this;
    }

    public static Client getClientInstance() {
        if(client == null)
            Log.d("d1" , "client is null");
        return client;
    }

    @Override
    public void run() {
        try {
            Log.d("d1", "Client starting at the port ");
            Socket socket = new Socket("192.168.7.28", 60122);
            Log.d("d1", "Client connected to server! ");

            this.sender = new ClientSender(this, socket);
            this.sender.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
