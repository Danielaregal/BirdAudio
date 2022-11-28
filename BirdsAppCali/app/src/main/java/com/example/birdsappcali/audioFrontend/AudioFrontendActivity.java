package com.example.birdsappcali.audioFrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.birdsappcali.R;

public class AudioFrontendActivity extends AppCompatActivity {

    protected TextView outputTextView;
    protected TextView specsTextView;
    protected Button startRecordingButton;
    protected Button stopRecordingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_helper);

        outputTextView = findViewById(R.id.AudioOutputTextview);
        specsTextView = findViewById(R.id.AudioSpecificationsTextview);
        startRecordingButton = findViewById(R.id.AudioButtonStart);
        stopRecordingButton = findViewById(R.id.AudioButtonStop);

        stopRecordingButton.setEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
            }
        }
        
    }
    public void startRecording(View view) {
        startRecordingButton.setEnabled(false);
        stopRecordingButton.setEnabled(true);
    }

    public void stopRecording(View view) {
        startRecordingButton.setEnabled(true);
        stopRecordingButton.setEnabled(false);
    }
}