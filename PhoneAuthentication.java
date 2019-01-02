package com.example.kioiduncan.errands;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthentication extends AppCompatActivity {
 private Button mButton;
 private ProgressBar mPhoneBar;
 private ProgressBar mCodeBar;
 private Spinner mSpinnerCountries;
 private TextView mTextView;
 private EditText mPhoneText;
 private LinearLayout mPhoneNumber;
 private LinearLayout mSendVerification;
 private LinearLayout mCodeLayout;
 private  TextView mErrorMessage;
 private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
 private FirebaseAuth mAuth;
 private String mVerificationId;
 private PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);

        mButton= (Button) findViewById(R.id.verification);
        mPhoneBar=(ProgressBar)findViewById(R.id.progressBar);
        mCodeBar =(ProgressBar) findViewById(R.id.codeBar);
        mSpinnerCountries =(Spinner) findViewById(R.id.spinnerCountries);
        mTextView=(TextView)findViewById(R.id.automessage );
        mPhoneText=(EditText)findViewById(R.id.phoneNumber);
        mCodeLayout=(LinearLayout)findViewById(R.id.codeLayout);
        mPhoneNumber=(LinearLayout)findViewById(R.id.phoneLinearLayout);
        mSendVerification =(LinearLayout) findViewById(R.id.textViewLinear);
        mAuth= FirebaseAuth.getInstance();
        mErrorMessage =(TextView)findViewById(R.id.errorMessage);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showing the progress bar once the phone number has been entered and disable the button and the input field
                mPhoneBar.setVisibility(View.VISIBLE);
                mPhoneText.setEnabled(false);
                mButton.setEnabled(false);

                String phoneNumber = mPhoneText.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        PhoneAuthentication.this,
                        mCallBacks
                );
            }
        });
        mCallBacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                mErrorMessage.setText("Error in Verification");
                mErrorMessage.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                mPhoneBar.setVisibility(View.INVISIBLE);
                mCodeLayout.setVisibility(View.VISIBLE);
                mButton.setText("Verify Text");

                // ...
            }
        };

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            Intent intent =new Intent(PhoneAuthentication.this,ChooseActivity.class);
                            startActivity(intent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            mErrorMessage.setText("LOGIN FAILED");
                            mErrorMessage.setVisibility(View.VISIBLE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
