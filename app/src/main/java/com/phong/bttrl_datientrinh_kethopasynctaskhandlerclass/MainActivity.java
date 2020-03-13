package com.phong.bttrl_datientrinh_kethopasynctaskhandlerclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    EditText edtNumber;
    MyAsyncTask myAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStart();
            }
        });
    }

    private void doStart() {
        //lấy số lượng từ EditText
        int n = Integer.parseInt(edtNumber.getText().toString());
        myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute(n);
    }

    private void addControls() {
        btnStart = findViewById(R.id.btnStart);
        edtNumber = findViewById(R.id.edtNumber);
    }
}
