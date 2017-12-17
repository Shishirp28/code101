package com.example.dell.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
    }

    public void share(View view)
    {
        startActivity(new Intent(this,MapsActivity.class));
    }

    public void get(View view) {
        startActivity(new Intent(this,AllUserActivity.class));
    }

}
