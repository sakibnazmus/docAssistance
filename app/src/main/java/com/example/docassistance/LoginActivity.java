package com.example.docassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "tag";
    private EditText loginEmail,loginPassword;
    private TextView btnSignUp,btnLoginPatient,btnLoginDoctor;
    private ProgressBar pb;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference patientsRef,doctorsRef;

    private List<UsersSignUpModel> usersSignUpModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        patientsRef = database.getReference("patients_user");
        doctorsRef = database.getReference("doctors_user");

        loginEmail = findViewById(R.id.edit_email_login);
        loginPassword = findViewById(R.id.edit_password_login);
        pb = findViewById(R.id.progress_login);

        btnLoginPatient = findViewById(R.id.text_login_as_patient);
        btnLoginDoctor = findViewById(R.id.text_login_as_doctor);
        btnSignUp = findViewById(R.id.text_sign_up);

        btnLoginPatient.setOnClickListener(this);
        btnLoginDoctor.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_login_as_patient:
                patientsLogin();
                break;
            case R.id.text_login_as_doctor:
                doctorsLogin();
                break;
            case R.id.text_sign_up:
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                break;
        }
    }


    private void patientsLogin() {
        final String patientEmail,patientPassword;
        patientEmail = loginEmail.getText().toString().trim();
        patientPassword = loginPassword.getText().toString().trim();

        if(patientEmail.isEmpty()){
            loginEmail.setError("Enter an Email address! ");
            loginEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(patientEmail).matches()){
            loginEmail.setError("Invalid Email Address!");
            loginEmail.requestFocus();
            return;
        }


        if(patientPassword.isEmpty()){
            loginPassword.setError("Enter an password!");
            loginPassword.requestFocus();
            return;
        }


        if(patientPassword.length()<6){
            loginPassword.setError("Atleast 6 length!");
            loginPassword.requestFocus();
            return;
        }

        try {
            patientsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean flag = true;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UsersSignUpModel usersSignUpModel = dataSnapshot1.getValue(UsersSignUpModel.class);
                        //Log.d(TAG, "onDataChange: "+usersSignUpModel.getEmail() +" "+email+" "+password);
                        //Log.d(TAG, "onDataChange: compare: "+email.equals(usersSignUpModel.getEmail()));
                        //Log.d(TAG, "onDataChange: compare: "+password.equals(usersSignUpModel.getPassword()));
                        if (patientEmail.equals(usersSignUpModel.getEmail()) && patientPassword.equals(usersSignUpModel.getPassword())) {
                            Toast.makeText(getApplicationContext(),"success:patient",Toast.LENGTH_SHORT).show();
                            flag = false;
                            startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
                        }

                    }
                    if(flag){
                        Toast.makeText(getApplicationContext(),"incorrect email or password",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void doctorsLogin() {
        final String doctorEmail,doctorPassword;
        doctorEmail = loginEmail.getText().toString().trim();
        doctorPassword = loginPassword.getText().toString().trim();

        if(doctorEmail.isEmpty()){
            loginEmail.setError("Enter an Email address! ");
            loginEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(doctorEmail).matches()){
            loginEmail.setError("Invalid Email Address!");
            loginEmail.requestFocus();
            return;
        }


        if(doctorPassword.isEmpty()){
            loginPassword.setError("Enter an password!");
            loginPassword.requestFocus();
            return;
        }


        if(doctorPassword.length()<6){
            loginPassword.setError("Atleast 6 length!");
            loginPassword.requestFocus();
            return;
        }

        try {
            doctorsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean flag = true;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UsersSignUpModel usersSignUpModel = dataSnapshot1.getValue(UsersSignUpModel.class);

                        if (doctorEmail.equals(usersSignUpModel.getEmail()) && doctorPassword.equals(usersSignUpModel.getPassword())) {
                            Toast.makeText(getApplicationContext(),"success:doctors",Toast.LENGTH_SHORT).show();
                            flag = false;
                            startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
                        }

                    }
                    if(flag){
                        Toast.makeText(getApplicationContext(),"incorrect email or password",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

}
