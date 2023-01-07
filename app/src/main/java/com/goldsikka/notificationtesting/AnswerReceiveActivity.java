package com.goldsikka.notificationtesting;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnswerReceiveActivity extends AppCompatActivity {

    private TextView tvAnswerReceiveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_receive);

        tvAnswerReceiveText = (TextView) findViewById(R.id.tvAnswerReceiveText);
        Log.d("Main", getIntent().getAction());

        tvAnswerReceiveText.setText(getIntent().getAction());
    }
}
