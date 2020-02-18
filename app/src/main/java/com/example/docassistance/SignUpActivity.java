package com.example.docassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    EditText mFirstName;
    EditText mLastName;
    EditText mPass1;
    EditText mPass2;
    EditText mEmail;
    Button mSubmitBtn;

    Activity mActivity;
    Context mContext;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mActivity = this;
        mContext = this;

        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mPass1 = findViewById(R.id.password);
        mPass2 = findViewById(R.id.password2);
        mEmail = findViewById(R.id.email);
        mSubmitBtn = findViewById(R.id.submitBtn);

        mAuth = FirebaseAuth.getInstance();

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPass1.getText().toString())
                        .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(mContext, "Signed up successfully", Toast.LENGTH_SHORT);
                                } else {
                                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT);
                                }
                            }
                        });
            }
        });
    }
}
