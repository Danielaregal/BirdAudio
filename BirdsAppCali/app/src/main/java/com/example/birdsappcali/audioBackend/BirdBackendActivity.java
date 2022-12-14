package com.example.birdsappcali.audioBackend;

import android.media.AudioRecord;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.birdsappcali.audioFrontend.AudioFrontendActivity;

import org.tensorflow.lite.support.audio.TensorAudio;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.audio.classifier.AudioClassifier;
import org.tensorflow.lite.task.audio.classifier.Classifications;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BirdBackendActivity extends AudioFrontendActivity {

    private String model = "my_birds_model.tflite";

    private AudioRecord audioRecord;
    private TimerTask timerTask;

    private AudioClassifier audioClassifier;
    private TensorAudio tensorAudio;


    public BirdBackendActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            audioClassifier = AudioClassifier.createFromFile(this, model);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error aqui");
        }

        tensorAudio = audioClassifier.createInputTensorAudio();
    }

    @Override
    public void startRecording(View view) {
        super.startRecording(view);

        TensorAudio.TensorAudioFormat format = audioClassifier.getRequiredTensorAudioFormat();
        String specs = "Número de Canales: " + format.getChannels() + "\n"
                + "Frecuencia de Muestreo: " + format.getSampleRate();
        specsTextView.setText(specs);

        audioRecord = audioClassifier.createAudioRecord();
        audioRecord.startRecording();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                tensorAudio.load(audioRecord);
                List<Classifications> output = audioClassifier.classify(tensorAudio);

                List<Category> finalOutput = new ArrayList<>();

                for(Category category : output.get(0).getCategories()){
                    if (category.getScore() > 0.3f && category.getLabel().equals("Bird")){
                        finalOutput.add(category);
                    }
                }

                if (finalOutput.isEmpty()){
                    return;
                }

                finalOutput = new ArrayList<>();

                for(Category category : output.get(1).getCategories()){
                    if(category.getScore() > 0.3f){
                        finalOutput.add(category);
                    }
                }

                StringBuilder outputStr = new StringBuilder();

                for (Category category : finalOutput){
                    outputStr .append(category.getLabel())
                            .append(": ").append(category.getScore()).append("\n");
                }

                Log.d("Audio", "classify list size: " + finalOutput.size());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        outputTextView.setText(outputStr.toString());
                    }
                });
            }
        };
        new Timer().scheduleAtFixedRate(timerTask, 1, 500);
    }

    @Override
    public void stopRecording(View view) {
        super.stopRecording(view);

        timerTask.cancel();
        audioRecord.stop();
    }
}

