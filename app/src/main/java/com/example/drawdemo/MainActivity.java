package com.example.drawdemo;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int  AUDIO_SAMPLE_RATE;
    private final static int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private final static int CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_IN_MONO;
    private final static int AUDIO_ENCODEFORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private int BufferSize;

    private int rateY;

    private Context context;
    private AudioRecord audioRecord;
    private AudioProcess audioProcess;

    SurfaceView surfaceView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioProcess = new AudioProcess();

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        button = (Button) findViewById(R.id.button);

        AUDIO_SAMPLE_RATE = 44100;
        BufferSize = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE,CHANNEL_CONFIGURATION,AUDIO_ENCODEFORMAT);

        context = getApplicationContext();
        rateY = 100;
        audioProcess.initDraw(rateY,surfaceView.getHeight(),context,AUDIO_SAMPLE_RATE);

        button.setOnClickListener(onClickListener);
    }

    private Button.OnClickListener onClickListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(button.getText().toString().equals("start")){
                audioRecord = new AudioRecord(AUDIO_SOURCE,AUDIO_SAMPLE_RATE,CHANNEL_CONFIGURATION,AUDIO_ENCODEFORMAT,BufferSize);

                audioProcess.baseLine = surfaceView.getHeight() - 50;
                audioProcess.frequence = AUDIO_SAMPLE_RATE;
                audioProcess.start(audioRecord,BufferSize,surfaceView);

                button.setText(R.string.stop_button_name);
            }
            else{
                audioProcess.stop(surfaceView);
                button.setText(R.string.start_button_name);
            }
        }
    };
}
