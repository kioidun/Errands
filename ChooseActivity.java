package com.example.kioiduncan.errands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.kioiduncan.errands.utilities.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChooseActivity extends AppCompatActivity {

    private Button mLogin;

    private FirebaseAuth mAuth;

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //loading if user is a firstTimer

        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(ChooseActivity.this, PREF_USER_FIRST_TIME, "true"));

        Intent introIntent = new Intent(ChooseActivity.this, MainActivity.class);
       introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);

       if (isUserFirstTime)
           startActivity(introIntent);
        setContentView(R.layout.activity_choose);

        mLogin = (Button) findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(ChooseActivity.this, PhoneAuthentication.class);
            startActivity(intent);
            finish();

        }

    }
}
