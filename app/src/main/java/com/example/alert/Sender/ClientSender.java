package com.example.alert.Sender;

import android.util.Log;

import packets.ClientPacket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ClientSender extends Thread {

    private Client client;
    private Socket socket;
    private Queue<ClientPacket> messageQueue;


    public ClientSender(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
        this.messageQueue = new LinkedList<ClientPacket>();
    }

    public synchronized void sendMessage(ClientPacket message) {
        this.messageQueue.add(message);
        notify();
    }

    public synchronized ClientPacket getNextMessageFromQueue() throws InterruptedException {
        while(this.messageQueue.isEmpty())
            wait();
        return this.messageQueue.poll();
    }

    @Override
    public void run() {
        try {
            Log.d("d1", "Client Sender : sending.. ");
            //Send message to the server
            //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            ObjectOutputStream objectOutStream = new ObjectOutputStream(socket.getOutputStream());

            while(!isInterrupted()) {
                ClientPacket packet = this.getNextMessageFromQueue();
                objectOutStream.writeObject(packet);
                //objectOutStream.newLine();
                objectOutStream.flush();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
