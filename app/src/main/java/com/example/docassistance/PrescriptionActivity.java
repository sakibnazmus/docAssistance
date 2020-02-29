package com.example.docassistance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity {

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int WRITE_STORAGE_PERMISSION_REQUEST_CODE = 2;
    private Activity mActivity;
    private ListView mListView;
    ArrayList<Medicine> medicines;
    MedicineListAdaptere medicineListAdapter;
    private EditText mName;
    private EditText mDays;
    private EditText mComment;
    private Button btnAdd;
    private Button btnGeneratePDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        mActivity = this;

        mListView = findViewById(R.id.prescriptionView);
        mName = findViewById(R.id.medicine);
        mDays = findViewById(R.id.medicineDays);
        mComment = findViewById(R.id.medicineComment);

        medicines = new ArrayList<>();
        medicineListAdapter = new MedicineListAdaptere(mActivity, R.layout.view_medicine, medicines);
        mListView.setAdapter(medicineListAdapter);

        btnAdd = findViewById(R.id.addMedicine);
        btnGeneratePDF = findViewById(R.id.generatePDF);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdd();
            }
        });
        btnGeneratePDF.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                generate(medicines);
            }
        });

        boolean result = checkPermissionForReadExtertalStorage();
        if(!result){
            try {
                requestPermissionForReadExtertalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result = checkPermissionForWriteExtertalStorage();
        if(!result){
            try {
                requestPermissionForWriteExtertalStorage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void requestPermissionForReadExtertalStorage(){
        try {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestPermissionForWriteExtertalStorage(){
        try {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public boolean checkPermissionForWriteExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = mActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private void generate(ArrayList<Medicine> medicines) {
        File file= createPdf(medicines);

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setPackage("com.adobe.reader");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        startActivity(intent);
    }

    private File createPdf(ArrayList<Medicine> medicines){
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        String extstoragedir = Environment.getExternalStorageDirectory().toString();
        File fol = new File(extstoragedir, "pdf");
        if(!fol.exists()) {
            fol.mkdir();
        }

        File file = new File(fol, "sample.pdf");
        try {
            if(file.exists()) {
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);

            PdfDocument pdfDocument = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new
                    PdfDocument.PageInfo.Builder(width, height, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setTextSize(35f);
            float textHeight = 80f;
            for(Medicine medicine: medicines){
                canvas.drawText("Medicine: " + medicine.name + "\t\t Days: " + medicine.days, 40, textHeight, paint);
                canvas.drawText("Comment: " + medicine.note, 40, textHeight+30, paint);
                textHeight += 80f;
            }
            pdfDocument.finishPage(page);

            pdfDocument.writeTo(fout);
            pdfDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return file;
    }

    private void onAdd(){
        String medicineName = mName.getText().toString();
        int days = Integer.parseInt(mDays.getText().toString());
        String comment = mComment.getText().toString();

        Medicine medicine = new Medicine(medicineName, days, comment);
        medicines.add(medicine);
        mName.setText("");
        mDays.setText("");
        mComment.setText("");
        medicineListAdapter.updateList(medicines);
    }
}
