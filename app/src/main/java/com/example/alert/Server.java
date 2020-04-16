package com.example.alert;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    ServerSocket serverSocket = null;
    private Activity activity = null;
    public static final int LISTENING_PORT = 8080;

    public Server(Activity activity){
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            Log.d("d1", "Server: Server starting at the port " + LISTENING_PORT);
            serverSocket = new ServerSocket(LISTENING_PORT);

            Socket socket = null;
            while(true) {
                socket = serverSocket.accept();
                Log.d("d1", "Server: Socket accepted !!!! ");
                Dispatcher dispatcher = new Dispatcher(activity);
                dispatcher.start();
                ClientListener listener = new ClientListener(socket, this.activity, dispatcher);
                listener.start();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
