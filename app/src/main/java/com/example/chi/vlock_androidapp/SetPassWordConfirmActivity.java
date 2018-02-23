package com.example.chi.vlock_androidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SetPassWordConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass_word_confirm);
        final Button next = findViewById(R.id.next_btn5);
        next.setEnabled(false);

        final Intent intent1 = getIntent();
        final String first_passwd = intent1.getStringExtra("password");
        final PinEntryEditText pinEntry = findViewById(R.id.txt_pin_entry1);

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals(first_passwd)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(pinEntry.getWindowToken(), 0);
                        next.setEnabled(true);
                    } else {
                        next.setEnabled(false);
                    }
                }
            });
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(
                        getApplicationContext(),
                        FingerPrintActivity.class
                );
                intent2.putExtra("is_user",false);
                intent2.putExtra("encrypt_passwd",encrypt_passwd(first_passwd));
                startActivity(intent2);
            }
        });
    }

    private String  encrypt_passwd(String passwd) {
        String url = "https://3z62jgkhmj.execute-api.us-east-1.amazonaws.com/prod/encrypt";
        final String[] encrypt_passwd = new String[1];

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd","encrypt");
            jsonObject.put("passwd",passwd);
        } catch (JSONException e) {
            e.printStackTrace();
        };

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            encrypt_passwd[0] = response.getString("encrypt_data");
                            Toast.makeText(getApplicationContext(), encrypt_passwd[0],Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                Map header = new HashMap();
                header.put("x-api-key","hZQHU4sKpi6TpS7L9C22T4vNfvmoDXga7dIghqxR");

                return header;
            }
        };
        MyVolley.getInstance(this).addToRequestQueue(jsonObjReq,"post");

        return encrypt_passwd[0];
    }
}
