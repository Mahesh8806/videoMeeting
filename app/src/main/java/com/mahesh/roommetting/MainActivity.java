package com.mahesh.roommetting;


import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity{

    EditText edtCode;
    Button btnJoin, btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCode = findViewById(R.id.edtCode);
        btnJoin = findViewById(R.id.btnJoin);
        btnShare = findViewById(R.id.btnShare);

        URL serverURL;

        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setRoom(edtCode.getText().toString())
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .setWelcomePageEnabled(false)
                    .build();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String edtString = edtCode.getText().toString();
                if(edtString.isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter/Create Room Code!!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                                .setServerURL(new URL("https://meet.jit.si"))
                                .setRoom(edtString)
                                .setAudioMuted(false)
                                .setVideoMuted(false)
                                .setAudioOnly(false)
                                .setWelcomePageEnabled(false)
                                .build();
                        JitsiMeetActivity.launch(MainActivity.this, options);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edtString = edtCode.getText().toString().trim();
                if(edtString.isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter/Create Room Code!!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    String shareCode = edtCode.getText().toString();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, shareCode);
                    intent.setType("text/plain");
                    startActivity(intent);
                }
            }
        });

    }
}