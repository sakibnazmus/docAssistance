package com.example.docassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class PrescriptionActivity extends AppCompatActivity {

    LinearLayout mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        /*mScrollView = (LinearLayout) findViewById(R.id.prescriptionLayout);
        LinearLayout medicineInput = (LinearLayout) findViewById(R.id.medicineInput);
        mScrollView.addView(medicineInput);*/
    }
}
