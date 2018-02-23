package com.example.chi.vlock_androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CandidateListActivity extends AppCompatActivity {
    String[] LIST_MENU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        Intent intent = getIntent();
        String voting = intent.getStringExtra("voting");
        if (voting.equals("총학생회")){
            LIST_MENU = new String[]{"총학생회", "기호 1번", "기호 2번"};
        } else if (voting.equals("소프트웨어융합대학")) {
            LIST_MENU = new String[]{"소프트웨어융합대학", "기호 1번", "기호 2번"};
        } else if (voting.equals("소프트웨어학부")) {
            LIST_MENU = new String[]{"소프트웨어학부", "기호 1번", "기호 2번"};
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = findViewById(R.id.candidate_list) ;
        listview.setAdapter(adapter) ;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                if (position != 0) {
                    // get TextView's Text.
                    String strText = (String) parent.getItemAtPosition(position);
                    Intent intent = new Intent(
                            getApplicationContext(),
                            VerifyingPhoneNumberActivity.class
                    );
                    intent.putExtra("candidate", strText);
                    startActivity(intent);
                    // TODO : use strText
                    Toast.makeText(getApplicationContext(), strText, Toast.LENGTH_SHORT).show();
                }
            }
        }) ;
    }
}
