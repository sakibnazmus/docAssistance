package com.example.docassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText loginEmail,loginPassword;
    private TextView btnSignUp,btnLoginPatient,btnLoginDoctor;
    private TextView forgotPassword;

    private ProgressBar pb;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database;
    private DatabaseReference patientsRef,doctorsRef;

    private List<UsersSignUpModel> usersSignUpModelList;

    //aikhane email resetting er kajta hoi thik kintu tarporeo login hobena karon login system alada kore korcilam tai kaj hoschena
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
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

        forgotPassword = findViewById(R.id.textView_forgot_password);
        forgotPassword.setOnClickListener(this);



    }

    @Override
    protected void onStart() {
        super.onStart();
      /*  if(mAuth.getCurrentUser()!= null){
            finish();
            startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
        }*/
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
                finish();
                break;
            case R.id.textView_forgot_password:
                showRecoverDialog();
        }
    }

    private void showRecoverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);
        final EditText editText = new EditText(this);
        editText.setHint("Email");
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText.setMinEms(16);

        linearLayout.addView(editText);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = editText.getText().toString().trim();

                beingRecovery(email);
            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();



    }

    private void beingRecovery(String email) {
        pb.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pb.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Email has been send!", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
            pb.setVisibility(View.VISIBLE);
            patientsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean flag = true;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UsersSignUpModel usersSignUpModel = dataSnapshot1.getValue(UsersSignUpModel.class);


                        if (patientEmail.equals(usersSignUpModel.getEmail()) && patientPassword.equals(usersSignUpModel.getPassword())) {
                            //Toast.makeText(getApplicationContext(),"success:patient",Toast.LENGTH_SHORT).show();
                            flag = false;

                            mAuth.signInWithEmailAndPassword(patientEmail,patientPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        finish();
                                        startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));

                                    }else{
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        }

                    }
                    pb.setVisibility(View.GONE);
                    if(flag){
                        loginEmail.setText("");
                        loginPassword.setText("");
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
            pb.setVisibility(View.VISIBLE);
            doctorsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean flag = true;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UsersSignUpModel usersSignUpModel = dataSnapshot1.getValue(UsersSignUpModel.class);

                        if (doctorEmail.equals(usersSignUpModel.getEmail()) && doctorPassword.equals(usersSignUpModel.getPassword())) {
                            Toast.makeText(getApplicationContext(),"success:doctors",Toast.LENGTH_SHORT).show();
                            flag = false;

                            mAuth.signInWithEmailAndPassword(doctorEmail,doctorPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        finish();
                                        startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
                                    }else{
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }

                    }
                    pb.setVisibility(View.GONE);
                    if(flag){
                        loginEmail.setText("");
                        loginPassword.setText("");
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
