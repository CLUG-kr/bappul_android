package com.example.clug_bobple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class EmailAuthenticationActivity  extends AppCompatActivity {
    TextView email_address, auth_number, time;
    EditText email_address_text, auth_number_text;
    ImageView email_confirm, auth_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_auth);

        email_address = (TextView)findViewById(R.id.email_address);
        auth_number = (TextView)findViewById(R.id.auth_number);
        time = (TextView)findViewById(R.id.time);
        email_address_text = (EditText)findViewById(R.id.email_address_text);
        auth_number_text = (EditText)findViewById(R.id.auth_number_text);
        email_confirm = (ImageView)findViewById(R.id.email_confirm);
        auth_button = (ImageView)findViewById(R.id.auth_button);

        email_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = email_address_text.getText().toString();
                if (Pattern.matches("^[a-zA-Z0-9]+@[cau.ac.kr]+$", address)) {
                    auth_number_text.setVisibility(View.VISIBLE);
                    auth_number.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    auth_button.setVisibility(View.VISIBLE);
                } else{
                    Toast.makeText(EmailAuthenticationActivity.this, "중앙대학교 이메일 주소가 아닙니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        auth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sign_up = new Intent(EmailAuthenticationActivity.this, Sign_upActivity.class);
                startActivity(intent_sign_up);
            }
        });
    }
}