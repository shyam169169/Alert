package com.example.alert.Listener;

import android.app.Activity;
import android.util.Log;

import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    ServerSocket serverSocket = null;
    static Server server;
    public static final int LISTENING_PORT = 8080;
    ClientListener listener;
    Dispatcher dispatcher;

    public Server(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.server = this;
    }

    public static Server getServerInstance() {
        if(server == null)
            Log.d("d1", "Server: server object is null !!");

        Log.d("d1", "Server created");
        return server;
    }

    @Override
    public void run() {
        try {
            Log.d("d1", "Server: Server starting at the port    " + LISTENING_PORT);
            serverSocket = new ServerSocket(LISTENING_PORT);

            Socket socket = null;
            while(true) {
                socket = serverSocket.accept();
                Log.d("d1", "Server: Socket accepted !!!! ");

                this.listener = new ClientListener(socket, dispatcher);
                this.listener.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
