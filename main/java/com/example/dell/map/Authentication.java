package com.example.dell.map;

import android.content.BroadcastReceiver;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Authentication extends AppCompatActivity {

  //  private BroadcastReceiver mRegistrationBroadCastReceiver;

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private Button mSignUp;

    private AwesomeValidation awesomeValidation;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onPause() {
        super.onPause();
       // LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadCastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadCastReceiver,new IntentFilter("registrationComplete"));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadCastReceiver,new IntentFilter(Config.STR_PUSH));
//
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

//        mRegistrationBroadCastReceiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if(intent.getAction().equals(Config.STR_PUSH))
//                {
//                    String message = intent.getStringExtra("message");
//                    showNotification("envy",message);
//                }
//            }
//        };

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        firebaseAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mSignUp = (Button) findViewById(R.id.signup);

        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        mLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                login();
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Authentication.this, SignupActivity.class));
            }
        });

    }

    private void login() {
        final String email,password,Name;
        email = mEmail.getText().toString();
        password = mPassword.getText().toString();




        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(Authentication.this, "Fields are empty", Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (awesomeValidation.validate() && task.isSuccessful()) {


                    Toast.makeText(Authentication.this, "Welcome", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Authentication.this, com.example.dell.map.Button.class));

                } else {

                    Toast.makeText(Authentication.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void showNotification(String title, String message) {
//        Intent intent = new Intent(getApplicationContext(),Login.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());
//        b.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setContentIntent(contentIntent);
//        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1,b.build());
//    }
//


}
