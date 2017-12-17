package com.example.dell.map;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignUp";

    private EditText mName;
    private EditText mContact;
    private EditText mEmail;
    private EditText mPassword;
    private Button mButton;
    private AwesomeValidation awesomeValidation;
    private DatabaseReference mDatabase;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        mAuth = FirebaseAuth.getInstance();
        mName = (EditText)findViewById(R.id.name);
        mContact = (EditText)findViewById(R.id.contact);
        mEmail = (EditText)findViewById(R.id.email);
        mPassword = (EditText)findViewById(R.id.password);
        mButton = (Button) findViewById(R.id.signup);

        awesomeValidation.addValidation(SignupActivity.this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$" , R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.contact,"^[7-9]{1}[0-9]{9}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                signUp();
            }
        });

    }

    private void signUp() {

        final String name, contact, email, password;
        name = mName.getText().toString();
        contact = mContact.getText().toString();
        email=mEmail.getText().toString();
        password=mPassword.getText().toString();


        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) )
        {

            Toast.makeText(SignupActivity.this,"Fields are empty",Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(awesomeValidation.validate() && task.isSuccessful())
                {
                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user != null) {
                        //sign in
                        // Toast.makeText(MainActivity.this, "You're now signed in. Welcome to Zorro.", Toast.LENGTH_SHORT).show();
                        user.sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Verification Email sent.");
                                            FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                                            String uid=current_user.getUid();
                                            mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                            HashMap<String,String> userMap=new HashMap<>();
                                            userMap.put("name",name);
                                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(SignupActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(SignupActivity.this, Authentication.class));
                                                }

                                            });
                                        }
                                    }
                                });


                    }

                }
                else
                {
                    Toast.makeText(SignupActivity.this,"You are already Registered or Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
