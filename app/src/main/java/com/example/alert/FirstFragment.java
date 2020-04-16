package com.example.alert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.net.Socket;

public class FirstFragment extends Fragment {
    Thread serverThread;
    Socket socket;

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

        //Server listening
        Server server = new Server(getActivity());
        Thread serverThread = new Thread(server);
        serverThread.start();



        Button alertButton = view.findViewById(R.id.alert_button);

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
                Client client1 = new Client(getActivity(), socket);
                client1.execute();
            }
        });
    }
}
