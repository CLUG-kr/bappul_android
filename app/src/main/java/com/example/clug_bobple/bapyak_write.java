package com.example.clug_bobple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class bapyak_write extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private ImageView back_button_write;
    private ImageView review_write_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bapyak_write);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);

        review_write_button = findViewById(R.id.review_write_button);
        review_write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendToServer(title.getText().toString(), content.getText().toString());
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void sendToServer(String title, String content) throws JSONException {
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("Authorization", "");
        String url = getString(R.string.url) + "/profile";
        RequestQueue queue = Volley.newRequestQueue(bapyak_write.this);

        JSONObject posting = new JSONObject();
        posting.put("title", title);
        posting.put("content", content);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, posting,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent posting = new Intent(bapyak_write.this, HomeActivity.class);
                        startActivity(posting);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(bapyak_write.this, "오류입니다", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> heads = new HashMap<String, String>();
                heads.put("Authorization", "Bearer "+token);
                return heads;
            }
        };
        queue.add(jsonObjectRequest);
    }
}