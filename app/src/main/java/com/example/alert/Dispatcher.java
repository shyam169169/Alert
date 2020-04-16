package com.example.alert;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

public class Dispatcher extends Thread {
    Queue<String> messageQueue;
    private Activity activity = null;

    public Dispatcher(Activity activity) {
        this.messageQueue = new LinkedList<String>();
        this.activity = activity;
    }

    public synchronized void addMesssage(String message) {
        this.messageQueue.add(message);
        Log.d("d1", "dispatcher: Message added. Notifying.. ");
        notify();
    }

    public synchronized String getNextMessageFromQueue() throws InterruptedException {

        while(this.messageQueue.isEmpty()) {
            Log.d("d1", "dispatcher: wait... ");
            wait();
        }
        Log.d("d1", "dispatcher: Notification received... ");
        if(!this.messageQueue.isEmpty()) {
            Log.d("d1", "dispatcher: returning message... ");
            return this.messageQueue.poll().toString();
        }
        return "";
    }

    @Override
    public void run() {
        Log.d("d1", "dispatcher: started.. ");
        while(true) {
            while (!isInterrupted()) {
                String message = null;
                try {
                    message = this.getNextMessageFromQueue();
                    Log.d("d1", "dispatcher: GOT THE MESSAGE !!!! ");
                    if (!message.isEmpty() && message != null) {
                        CreateNotification.notify(message.toString(), this.activity);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
