package com.example.remoteController;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etIpAddress,etPort;
    private Button btnConnect;
    public static ConnectorClass connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //These two commands solve the issue of server response
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etIpAddress=findViewById(R.id.etIP);
        etPort=findViewById(R.id.etPort);
        btnConnect=findViewById(R.id.btnConnect);

        //Port text
        etPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0 || etIpAddress.getText().toString().trim().length()==0){
                    btnConnect.setEnabled(false);
                } else {
                    btnConnect.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Ip Text
        etIpAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0 || etPort.getText().toString().trim().length()==0){
                    btnConnect.setEnabled(false);
                } else {
                    btnConnect.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connector=new ConnectorClass();
                try {
                    connector.connect(getApplicationContext(),etIpAddress.getText().toString(),Integer.parseInt(etPort.getText().toString()));
//Intent intent = new Intent(getApplicationContext(), ControllerActivity.class);
//startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}