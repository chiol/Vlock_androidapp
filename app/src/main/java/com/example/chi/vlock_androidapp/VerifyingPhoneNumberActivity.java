package com.example.chi.vlock_androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyingPhoneNumberActivity extends AppCompatActivity {

    TextView candidate_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifying_phone_number);
        Button next = findViewById(R.id.next_btn4);


        candidate_txt =  findViewById(R.id.VerifyingPhoneNumber_candidate_txt);
        candidate_txt.setText(getIntent().getStringExtra("candidate"));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        IDCardActivity.class
                );
                verify_phone_num();
                startActivity(intent);
            }
        });
    }
    private String  verify_phone_num() {
        String url = "http://52.187.72.71:5000/number";
        final String[] code = new String[1];

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_mac",GetMacAadress.getMACAddress("wlan0"));
        } catch (JSONException e) {
            e.printStackTrace();
        };

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            code[0] = response.getString("StatusCode");
                            Toast.makeText(getApplicationContext(), code[0],Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        MyVolley.getInstance(this).addToRequestQueue(jsonObjReq,"post");

        return code[0];
    }
}
