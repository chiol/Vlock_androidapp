package com.example.chi.vlock_androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class SuccessFingerprintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_fingerprint);
        Button next = findViewById(R.id.next_btn6);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        VotingListActivity.class);
                startActivity(intent);
            }
        });
        // 유저 만드는 과정
        if (!getIntent().getBooleanExtra("is_user",false)) {
            String passwd = delivery_passwd();
            String macAddress = GetMacAadress.getMACAddress("wlan0");
            send_my_info_to_server(macAddress,passwd);
        }
        // 투표 과정
        else {
        }
    }



    private void send_my_info_to_server(String mac,String passwd) {
        String url = "http://52.187.72.71:5000/user";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_mac",mac);
            jsonObject.put("password",passwd);
        } catch (JSONException e) {
            e.printStackTrace();
        };

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("StatusCode");
                            Log.e("connection",code);
                            Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("connection",error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        MyVolley.getInstance(this).addToRequestQueue(jsonObjReq,"post");
    }

    public String delivery_passwd() {
        Intent intent = getIntent();
        return intent.getStringExtra("passwd");
    }
}
