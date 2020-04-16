package com.example.alert;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client extends AsyncTask<String, Void, Void> {

    private Activity activity = null;
    private Socket socket;


    public Client(Activity activity, Socket socket) {
        this.activity = activity;
        this.socket = socket;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            Log.d("d1", "Client starting at the port ");
            socket = new Socket("192.168.7.28", 60122);
            //Send message to the server
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("this is a message from android to eclipse");
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
