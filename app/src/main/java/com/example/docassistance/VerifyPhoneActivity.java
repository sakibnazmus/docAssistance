package com.example.docassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private EditText editTextCode;
    private String mVerificationId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editTextCode);

        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        sendVerificationCode(mobile);

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("invalid input");
                    editTextCode.requestFocus();
                    return;
                }

                verifyVerficationCode(code);

            }
        });

    }


    private void sendVerificationCode(String mobile) {
        String mobileWithCountryCode  = "+88" + mobile;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileWithCountryCode,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallback);
        Log.d(TAG, "sendVerificationCode: mobile number: "+ mobileWithCountryCode);


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    Log.d(TAG, "onVerificationCompleted: "+code);


                    if(code != null){
                        editTextCode.setText(code);
                        verifyVerficationCode(code);
                    } else {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onVerificationFailed: error: "+e.getMessage());
                    Log.d(TAG, "onVerificationFailed: codeSentId: "+mVerificationId);
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    mVerificationId = s;
                    Log.d(TAG, "onCodeSent: "+ mVerificationId);

                }


            };

    private void verifyVerficationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
        Log.d(TAG, "verifyVerficationCode: credential"+credential);

        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(VerifyPhoneActivity.this,ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    String messege = "something is wrong we will fix it soon...";

                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        messege = "invalid code entered...";
                    }
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.parent),messege,Snackbar.LENGTH_LONG);
                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                }
            }
        });
    }
}
