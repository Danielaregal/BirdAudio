package com.example.birdsappcali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.birdsappcali.audioBackend.BirdBackendActivity;
import com.example.birdsappcali.audioFrontend.BirdExplorerActivity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
   public void onGotoBirdSoundIdentifier(View view){
        // Se instancia Audio Helper Activity
       Intent intent = new Intent(this, BirdBackendActivity.class);
       startActivity(intent);
    }
    public void onGotoBirdExplorer(View view){
        // Se instancia Audio Helper Activity
        Intent intent = new Intent(this, BirdExplorerActivity.class);
        startActivity(intent);
    }

}