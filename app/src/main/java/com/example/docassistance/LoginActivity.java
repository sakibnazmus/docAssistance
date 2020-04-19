package com.example.docassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private EditText editTextMobile;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        editTextMobile = findViewById(R.id.editTextMobile);

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Log.d(TAG, "onCreate: "+ mAuth.getCurrentUser());
            finish();
        }



        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString().trim();

                if(mobile.length()<11 || mobile.isEmpty()){
                    editTextMobile.setError("please input a valid mobile number");
                    editTextMobile.requestFocus();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this,VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
                finish();

            }
        });

    }


}
