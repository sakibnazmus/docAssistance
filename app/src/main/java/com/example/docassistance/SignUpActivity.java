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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";
    private TextView btnSignUpBackActivity;
    private EditText signUpEmail,signUpUsername,signUpPassword,signUpMobileNumber,signUpAdress;
    private TextView btnPatientSignUp;
    private TextView btnDoctorSignUp;
    private ProgressBar pb;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference patientRef,doctorRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        patientRef = database.getReference("patients_user");
        doctorRef = database.getReference("doctors_user");

        signUpEmail = findViewById(R.id.edit_email_sign_up);
        signUpUsername = findViewById(R.id.edit_username);
        signUpPassword = findViewById(R.id.edit_password_sign_up);
        signUpMobileNumber = findViewById(R.id.edit_mobile_number);
        signUpAdress = findViewById(R.id.edit_address);
        pb = findViewById(R.id.progress_sign_up);

        btnSignUpBackActivity = findViewById(R.id.text_login_back_activity);
        btnSignUpBackActivity.setOnClickListener(this);

        btnPatientSignUp = findViewById(R.id.text_sign_up_patient);
        btnPatientSignUp.setOnClickListener(this);

        btnDoctorSignUp = findViewById(R.id.text_sign_up_doctor);
        btnDoctorSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.text_sign_up_patient:
                userPatientsRegistration();
                break;
            case R.id.text_sign_up_doctor:
                userDoctorsRegistration();
                break;
            case R.id.text_login_back_activity:
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }

    private void userPatientsRegistration() {

        String email = signUpEmail.getText().toString().trim();
        String username = signUpUsername.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();
        String mobileNumber = signUpMobileNumber.getText().toString().trim();
        String address = signUpAdress.getText().toString().trim();

         //check();

        if(email.isEmpty()){
            signUpEmail.setError("Enter an Email address! ");
            signUpEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmail.setError("Invalid Email Address!");
            signUpEmail.requestFocus();
            return;
        }


        if(username.isEmpty()){
            signUpUsername.setError("Enter an address");
            signUpUsername.requestFocus();
            return;
        }
        if(username.length()<4){
            signUpUsername.setError("please Enter username atleast consisting of 3 characters!");
            signUpUsername.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signUpPassword.setError("Enter an password!");
            signUpPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            signUpPassword.setError("Atleast 6 length!");
            signUpPassword.requestFocus();
            return;
        }

        if(mobileNumber.isEmpty()){
            signUpMobileNumber.setError("Enter a  phone number");
            signUpMobileNumber.requestFocus();
            return;
        }

        if (mobileNumber.length()<11){
            signUpMobileNumber.setError("Enter a valid phone number");
            signUpMobileNumber.requestFocus();
            return;
        }

        if(address.isEmpty()){
            signUpAdress.setError("Enter an address");
            signUpAdress.requestFocus();
            return;
        }

        pb.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    storingPatientsUserSignUpData();
                    finish();
                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));

                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){

                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG)
                                .show();
                        signUpEmail.setText("");
                        signUpEmail.setError("Try with another gmail!");
                        signUpEmail.requestFocus();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });
    }


    public void storingPatientsUserSignUpData(){

        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();
        String uid = mAuth.getUid();

        String address = signUpAdress.getText().toString().trim();
        String mobileNumber = signUpMobileNumber.getText().toString().trim();
        String username = signUpUsername.getText().toString().trim();



        String key = patientRef.push().getKey();

        UsersSignUpModel usersSignUpModel = new UsersSignUpModel(email,password,uid,address,mobileNumber,username);

        patientRef.child(key).setValue(usersSignUpModel);
        Log.d(TAG, "storingPatientsUserSignUpData: "+key+" "+ usersSignUpModel.getEmail()+" "+usersSignUpModel.getPassword());
        Toast.makeText(getApplicationContext(),"success "+key,Toast.LENGTH_LONG)
                .show();


    }


    private void userDoctorsRegistration() {
        String email = signUpEmail.getText().toString().trim();
        String username = signUpUsername.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();
        String mobileNumber = signUpMobileNumber.getText().toString().trim();
        String address = signUpAdress.getText().toString().trim();

        if(email.isEmpty()){
            signUpEmail.setError("Enter an Email address! ");
            signUpEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmail.setError("Invalid Email Address!");
            signUpEmail.requestFocus();
            return;
        }


        if(username.isEmpty()){
            signUpUsername.setError("Enter an address");
            signUpUsername.requestFocus();
            return;
        }
        if(username.length()<4){
            signUpUsername.setError("please Enter username atleast consisting of 3 characters!");
            signUpUsername.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signUpPassword.setError("Enter an password!");
            signUpPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            signUpPassword.setError("Atleast 6 length!");
            signUpPassword.requestFocus();
            return;
        }

        if(mobileNumber.isEmpty()){
            signUpMobileNumber.setError("Enter a  phone number");
            signUpMobileNumber.requestFocus();
            return;
        }

        if(mobileNumber.length()<11){
            signUpMobileNumber.setError("Enter a valid phone number");
            signUpMobileNumber.requestFocus();
            return;
        }

        if(address.isEmpty()){
            signUpAdress.setError("Enter an address");
            signUpAdress.requestFocus();
            return;
        }

        pb.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    storingDoctorsUserSignUpData();
                    finish();
                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));

                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){

                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG)
                                .show();
                        signUpEmail.setText("");
                        signUpEmail.setError("Try with another gmail!");
                        signUpEmail.requestFocus();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });
    }


    public void storingDoctorsUserSignUpData(){

        String email = signUpEmail.getText().toString().trim();
        String password = signUpPassword.getText().toString().trim();
        String uid = mAuth.getUid();

        String address = signUpAdress.getText().toString().trim();
        String mobileNumber = signUpMobileNumber.getText().toString().trim();
        String username = signUpUsername.getText().toString().trim();

        String key = patientRef.push().getKey();

       // UsersSignUpModel usersSignUpModel = new UsersSignUpModel(email,password,uid);
        UsersSignUpModel usersSignUpModel = new UsersSignUpModel(email,password,uid,address,mobileNumber,username);



        doctorRef.child(key).setValue(usersSignUpModel);
        Toast.makeText(getApplicationContext(),"success "+key,Toast.LENGTH_LONG)
                .show();


    }


}
