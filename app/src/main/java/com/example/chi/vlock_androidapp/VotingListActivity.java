package com.example.chi.vlock_androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class VotingListActivity extends AppCompatActivity {
    static final String[] LIST_MENU = {"총학생회", "소프트웨어융합대학", "소프트웨어학부"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_list);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = findViewById(R.id.voting_list) ;
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position) ;
                Intent intent = new Intent(
                        getApplicationContext(),
                        CandidateListActivity.class
                );
                intent.putExtra("voting",strText);
                startActivity(intent);
                // TODO : use strText
                Toast.makeText(getApplicationContext(),strText,Toast.LENGTH_SHORT).show();
            }
        }) ;


    }
}
