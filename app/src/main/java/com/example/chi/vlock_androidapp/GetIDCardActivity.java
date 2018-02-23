package com.example.chi.vlock_androidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class GetIDCardActivity extends AppCompatActivity {
    ImageView iv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_idcard);

        Button next_btn3 = findViewById(R.id.next_btn3);
        iv = findViewById(R.id.iv);

        next_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        ManualActivity.class);
                verify_idcard();
                startActivity(intent);
            }
        });

        Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cam,1);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cam,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == 1) // 1 은 위에서 startActivityForResult(intent, 1);
            {
                ImageView imageView1 = findViewById(R.id.iv);
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                imageView1.setImageBitmap(bm);
            }
        }
    }

    private String  verify_idcard() {
        String url = "http://52.187.72.71:5000/idcard";
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
