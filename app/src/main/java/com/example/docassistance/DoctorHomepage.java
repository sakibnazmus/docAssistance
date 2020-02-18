package com.example.docassistance;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class DoctorHomepage extends AppCompatActivity {
    Button mNewPrescriptionButton;
    AppCompatActivity mActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_homepage);

        mActivityContext = this;
        mNewPrescriptionButton = findViewById(R.id.new_prescribe);
        mNewPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivityContext, PrescriptionPatientInfo.class);
                startActivity(intent);
            }
        });
    }

}
