package com.example.alert;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.alert.Listener.Dispatcher;
import com.example.alert.Listener.Server;
import com.example.alert.Sender.Client;

import packets.ClientPacket;

import static java.lang.Thread.sleep;

public class FirstFragment extends Fragment {
    Intent intent;
    String message;
    String interval;
    ClientPacket packet;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Dispatcher
        Dispatcher dispatcher = new Dispatcher(getActivity());
        dispatcher.start();

       // Client listener
        Server server = new Server(dispatcher);

        //Start client sender
        Client client = new Client();

        //start background service
        intent = new Intent(getActivity(), BackgroundService.class);
        getActivity().startService(intent);

        Button alertButton = view.findViewById(R.id.alert_button);

        EditText messageField = view.findViewById(R.id.messageId);
        message = messageField.getText().toString();

        EditText intervalField = view.findViewById(R.id.intervalId);
        interval = intervalField.getText().toString();

        if(message.isEmpty()) {
            message = "stove";
        }
        packet = new ClientPacket(message, 1);

        view.findViewById(R.id.alert_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        alertButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Client client = Client.getClientInstance();
                client.sender.sendMessage(packet);
            }
        });
    }

    @Override
    public void onDestroy() {

        Client.getClientInstance().sender.sendMessage(new ClientPacket("bye", 0 ));
        getActivity().stopService(intent);
        Log.d("d1", "From main destroy");
        try {
            sleep(5000);
            Log.d("d1", "sleepingggggg");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();
        Log.d("d1", "Main destroy over");

    }
}
