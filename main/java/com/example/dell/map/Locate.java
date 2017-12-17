package com.example.dell.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Locate extends AppCompatActivity {

    private TextView sd;
    private DatabaseReference mUsersDatabase;

    private DatabaseReference mRootRef;

    private FirebaseUser mCurrent_user;

    private String mCurrent_state;
    String display_name = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);
        sd=(TextView) findViewById(R.id.sd2);

        final String user_id = getIntent().getStringExtra("user_id");

        mRootRef = FirebaseDatabase.getInstance().getReference();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 display_name = dataSnapshot.child("status").getValue().toString();
                String [] seperated = display_name.split(",");
                String latipos = seperated[0].trim();
                String longipos = seperated[1].trim();
                double dlat = Double.parseDouble(latipos);
                double dlong = Double.parseDouble(longipos);




                sd.setText(display_name);








            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    }

