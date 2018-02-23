package com.example.chi.vlock_androidapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        final CheckBox all_check = findViewById(R.id.allcheck);
        final CheckBox check1 = findViewById(R.id.check1);
        final CheckBox check2 = findViewById(R.id.check2);
        final CheckBox check3 = findViewById(R.id.check3);
        final Button next_btn1 = findViewById(R.id.next_btn1);

        next_btn1.setEnabled(false);
        all_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (all_check.isChecked()){
                    check1.setChecked(true);
                    check2.setChecked(true);
                    check3.setChecked(true);
                }else if (!all_check.isChecked()){
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                }
            }
        });
        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (check1.isChecked() && check2.isChecked()){
                    next_btn1.setEnabled(true);
                }else{
                    next_btn1.setEnabled(false);
                }
                if (check1.isChecked()){
                    campermissoncheck();
                } else{

                }
            }

        });

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (check1.isChecked() && check2.isChecked()){
                    next_btn1.setEnabled(true);
                } else{
                    next_btn1.setEnabled(false);
                }
            }
        });

        next_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        SetPassWordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void campermissoncheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            //Manifest.permission.READ_CALENDAR이 접근 승낙 상태 일때
        } else{
            //Manifest.permission.READ_CALENDAR이 접근 거절 상태 일때
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
//                //사용자가 다시 보지 않기에 체크를 하지 않고, 권한 설정을 거절한 이력이 있는 경우
//            } else{
//                //사용자가 다시 보지 않기에 체크하고, 권한 설정을 거절한 이력이 있는 경우
//            }

            //사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
            //만약 사용자가 다시 보지 않기에 체크를 했을 경우엔 권한 설정 다이얼로그가 뜨지 않고,
            //곧바로 OnRequestPermissionResult가 실행된다.
            ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    ,Manifest.permission.CAMERA}
                                    ,0);
        }
    }



}
