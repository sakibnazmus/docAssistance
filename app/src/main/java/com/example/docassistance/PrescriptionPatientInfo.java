package com.example.docassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class PrescriptionPatientInfo extends AppCompatActivity {
    private Activity mActivity;

    EditText mFirstName;
    EditText mLastName;
    EditText mAge;
    RadioGroup mGender;
    EditText mPhoneNo;

    Button mNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_patient_info);

        mActivity = this;

        mFirstName = findViewById(R.id.patientFirstName);
        mLastName = findViewById(R.id.patientLastName);
        mAge = findViewById(R.id.patientAge);
        mGender = findViewById(R.id.patientGender);
        mPhoneNo = findViewById(R.id.patientPhoneNo);

        mNext = findViewById(R.id.patientNext);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNext(view);
            }
        });
    }

    private void onNext(View view){
        /*String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        int age = Integer.parseInt(mAge.getText().toString());
        Gender gender = Gender.valueOf(findViewById(mGender.getCheckedRadioButtonId()).toString().toUpperCase());
        String phone = mPhoneNo.getText().toString();
        Parcelable patientInfo = new PatientInfo(firstName, lastName, age, gender, phone);*/

        Intent intent = new Intent(mActivity, PrescriptionActivity.class);
//        intent.putExtra("patientInfo", patientInfo);
        mActivity.startActivity(intent);
    }
}
