package com.example.chi.vlock_androidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.alimuzaffar.lib.pin.PinEntryEditText;

public class PasswordCheckActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_check);
        final PinEntryEditText pinEntry = findViewById(R.id.PasswordCheckActivyty_pinentry);
        final Button button = findViewById(R.id.PasswordCheckActivyty_next_btn);
        button.setEnabled(false);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.length() == 6) {
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
                Intent intent = new Intent(
                        getApplicationContext(),
                        PasswordCheckConfirmActivity.class
                );
                intent.putExtra("password", pinEntry.getText().toString());
                startActivity(intent);
            }
        });
    }

}
