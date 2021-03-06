package com.example.clug_bobple;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class ReviewWriteActivity extends AppCompatActivity {

    ImageView review_write_button, back_button;
    EditText review_content;
    RatingBar star_bar;
    int star_num;
    String content, lat, lon, restaurant_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        Intent intent = getIntent();
        restaurant_name = intent.getStringExtra("restaurant");
        lat = Double.toString(intent.getDoubleExtra("lat", 0));
        lon = Double.toString(intent.getDoubleExtra("lon", 0));

        review_content = findViewById(R.id.review_content);
        star_bar = findViewById(R.id.star_bar);
        back_button = findViewById(R.id.back_button_write);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        review_write_button = findViewById(R.id.review_write_button);
        review_write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = review_content.getText().toString();
                star_num = (int)star_bar.getRating();
                System.out.println("김재훈은 " + star_num);

                JSONObject locationJson = new JSONObject();
                try{
                    locationJson.put("lat", lat);
                    locationJson.put("long", lon);
                } catch (JSONException e){
                    e.printStackTrace();
                }

                JSONObject reviewContentJson = new JSONObject();
                try {
                    reviewContentJson.put("commentContent", content);
                    reviewContentJson.put("rating", Integer.toString(star_num));
                } catch (JSONException e){
                    e.printStackTrace();
                }

                JSONObject reviewJson = new JSONObject();
                try{
                    reviewJson.put("location", locationJson);
                    reviewJson.put("review", reviewContentJson);
                } catch (JSONException e){
                    e.printStackTrace();
                }

                SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
                String token = sharedPreferences.getString("Authorization", "");

                RequestQueue queue = Volley.newRequestQueue(ReviewWriteActivity.this);
                String url = getString(R.string.url)+"/restaurant/"+restaurant_name+"/review";
                //Toast.makeText(ReviewWriteActivity.this, reviewJson.toString(), Toast.LENGTH_SHORT).show();

                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, reviewJson,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(ReviewWriteActivity.this, "오류입니다.", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> heads = new HashMap<String, String>();
                        heads.put("Authorization", "Bearer " + token);
                        return heads;
                    }
                };

                queue.add(jsonObjectRequest);

                Intent intent1 = new Intent();
                intent1.putExtra("name", restaurant_name);
                intent1.putExtra("lat", lat);
                intent1.putExtra("lon", lon);
                setResult(0, intent1);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}