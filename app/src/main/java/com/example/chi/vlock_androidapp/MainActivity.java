package com.example.chi.vlock_androidapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (is_user().equals("200")) {
            Handler handler = new Handler(){
                public void handleMessage(Message msg){
                    super.handleMessage(msg);
                    startActivity(new Intent(MainActivity.this,TermsActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0,2000);
        } else if (is_user().equals("1000")) {
            Handler handler = new Handler(){
                public void handleMessage(Message msg){
                    super.handleMessage(msg);
                    startActivity(new Intent(MainActivity.this,VotingListActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0,2000);
        }

    }

    private String  is_user() {
        String url = "http://52.187.72.71:5000/user";
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
