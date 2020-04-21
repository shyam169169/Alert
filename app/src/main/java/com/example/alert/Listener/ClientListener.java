package com.example.alert.Listener;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * Listens to all client messages and forwards it to dispatcher
 */
public class ClientListener extends Thread {
    Socket socket = null;
    Dispatcher dispatcher = null;

    public ClientListener(Socket socket, Dispatcher dispatcher){
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    public void run(){
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = null;
            Log.d("d1", "Client: client listener started..");
            //Until interrupted, read messages from client
            while(!isInterrupted()){
                String message = br.readLine();
                if(message != null)
                {
                    if(!this.socket.isClosed() && this.socket.isConnected()) {
                        Log.d("d1", "Client: client calling dispatcher .. ");
                        this.dispatcher.addMesssage(message);

                    }
                }
            }
        }
        catch(Exception ex){}

    }
}
