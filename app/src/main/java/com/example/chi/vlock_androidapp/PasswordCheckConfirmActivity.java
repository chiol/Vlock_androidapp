package com.example.chi.vlock_androidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.alimuzaffar.lib.pin.PinEntryEditText;

public class PasswordCheckConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_check_confirm);

        final Button button = findViewById(R.id.PasswordCheckConfirmActivyty_next_btn);
        button.setEnabled(false);

        final Intent intent1 = getIntent();
        final String first_passwd = intent1.getStringExtra("password");
        final PinEntryEditText pinEntry = findViewById(R.id.PasswordCheckConfirmActivyty_pinentry);

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals(first_passwd)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(pinEntry.getWindowToken(), 0);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                }
            });
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(
                        getApplicationContext(),
                        FingerPrintActivity.class
                );
                intent2.putExtra("is_user",true);
                startActivity(intent2);
            }
        });
    }
}
