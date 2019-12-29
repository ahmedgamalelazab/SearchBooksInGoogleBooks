package com.example.googlebooksapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.EditText);
        final String text = editText.getText().toString();
        Log.e(MainActivity.class.getSimpleName(), "kosm el main " + text);
        //now lets send the user to another page
        myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, books.class);
                intent.putExtra("Key", editText.getText().toString());
                startActivity(intent);
            }
        });

    }
}
