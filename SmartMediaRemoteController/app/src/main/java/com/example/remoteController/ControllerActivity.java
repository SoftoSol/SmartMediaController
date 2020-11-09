package com.example.remoteController;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Locale;

public class ControllerActivity extends AppCompatActivity implements Thread.UncaughtExceptionHandler {

    //text field
    TextView txtView, txtResponse;
    //voice recording button
    ImageButton voiceBtn;
    Button btnMute, btnPlayPause, btnRecSave;
    //Speech Recognizer object
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    String serverResponse;

    private enum CommandList{
        PLAY_PAUSE,STOP,
        PREV,NEXT,MUTE,
        VOL_DOWN,VOL_UP, UNMUTE,
        OPEN_CAM, CAPTURE,CLOSE_CAM,
        OPEN_VIDEO,
        NULL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        //First we have to check permissions
        checkPermission();

        //binding fields
        txtView=findViewById(R.id.editText);
        txtResponse=findViewById(R.id.txtReponse);
        voiceBtn=findViewById(R.id.btnVoice);
        btnMute=findViewById(R.id.btnMute);
        btnPlayPause=findViewById(R.id.btnPlay);

        //Initialize fields
        mSpeechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        //Speech recognizer
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                //System.out.println(matches.get(0));
                if(matches!=null){
                    serverResponse=sendCommand(filterCommand(matches.get(0)));
                    txtResponse.setText(serverResponse!=null ? serverResponse: "No response!");
                    txtView.setText(matches.get(0));
                    txtResponse.setText(serverResponse);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        //add listener to voice record button
        findViewById(R.id.btnVoice).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        //voiceBtn.setBackgroundColor(Color.TRANSPARENT);
                        mSpeechRecognizer.stopListening();
                        txtView.setHint("You will see input here");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        //voiceBtn.setBackgroundColor(Color.GRAY);
                        try{
                            txtView.setText("");
                            txtView.setHint("Listening...");
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        }catch(Exception e){
                            txtView.setText(e.getLocalizedMessage());
                            e.printStackTrace();
                        }
                        break;
                }
                return false;
            }
        });

        //add listener to close app button
        findViewById(R.id.btnCloseApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand("close<EOF>");
                MainActivity.connector.disconnect(getApplicationContext());
                finish();
            }
        });

        //add listener to play button
        findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnPlayPause.getText().toString().equalsIgnoreCase("play")){
                    serverResponse=sendCommand(filterCommand("play"));
                    txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
                    btnPlayPause.setText("Pause");
                }else {
                    serverResponse=sendCommand(filterCommand("pause"));
                    txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
                    btnPlayPause.setText("Play");
                }
            }
        });

        //add listener to stop button
        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Stop"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to prev button
        findViewById(R.id.btnPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Previous"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to next button
        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Next"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to mute button
        findViewById(R.id.btnMute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(btnMute.getText());
                if(btnMute.getText().toString().equalsIgnoreCase("mute")){
                    serverResponse=sendCommand(filterCommand("Mute"));
                    txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
                    btnMute.setText("Unmute");
                }else{
                    serverResponse=sendCommand(filterCommand("Unmute"));
                    txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
                    btnMute.setText("Mute");
                }
            }
        });

        //add listener to vol down button
        findViewById(R.id.btnVolDown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Volume down"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to vol up button
        findViewById(R.id.btnVolUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Volume Up"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to open camera button
        findViewById(R.id.btnCamOpen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Open Camera"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to capture photo button
        findViewById(R.id.btnCapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Capture"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to close camera button
        findViewById(R.id.btnCamClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Close Camera"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to open video button
        findViewById(R.id.btnVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverResponse=sendCommand(filterCommand("Open Video"));
                txtResponse.setText(serverResponse!=null ? serverResponse : "No response!");
            }
        });

        //add listener to help button
        findViewById(R.id.btnHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpDialog exampleDialog = new HelpDialog();
                exampleDialog.show(getSupportFragmentManager(), "help dialog");
            }
        });
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        sendCommand("close<EOF>");
        MainActivity.connector.disconnect(getApplicationContext());
        finish();
    }

    @Override
    protected void onDestroy() {
        sendCommand("close<EOF>");
        MainActivity.connector.disconnect(getApplicationContext());
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        sendCommand("close<EOF>");
        MainActivity.connector.disconnect(getApplicationContext());
        finish();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        sendCommand("close<EOF>");
        MainActivity.connector.disconnect(getApplicationContext());
        finish();
        super.onBackPressed();
    }

    //Method for checking permissions of audio recording
    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)){
                Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    //Filter command
    private String filterCommand(String cmd){
        switch (cmd.toLowerCase()){
            case "play":
            case "pause":
                return CommandList.PLAY_PAUSE.toString()+"<EOF>";
            case "stop":
                return CommandList.STOP.toString()+"<EOF>";
            case "previous":
                return CommandList.PREV.toString()+"<EOF>";
            case "next":
                return CommandList.NEXT.toString()+"<EOF>";
            case "mute":
                return CommandList.MUTE.toString()+"<EOF>";
            case "unmute":
                return CommandList.UNMUTE.toString()+"<EOF>";
            case "volume down":
                return CommandList.VOL_DOWN.toString()+"<EOF>";
            case "volume up":
                return CommandList.VOL_UP.toString()+"<EOF>";
            case "open camera":
                return CommandList.OPEN_CAM.toString()+"<EOF>";
            case "capture":
                return CommandList.CAPTURE.toString()+"<EOF>";
            case "close camera":
                return CommandList.CLOSE_CAM.toString()+"<EOF>";
            case "open video":
                return CommandList.OPEN_VIDEO.toString()+"<EOF>";
            default:
                return null;
        }
    }

    //Send command to desktop app
    private String sendCommand(String msg){
        return MainActivity.connector.sendCommand(msg);
    }

}
