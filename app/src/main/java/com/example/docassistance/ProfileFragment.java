package com.example.docassistance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static com.example.docassistance.MainActivity.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mRef;

    private TextInputLayout tilFullName,tilEmail,tilAddress;
    private TextView tvProfileEmail,tvProfileName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Profile");

        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("users");

        tilFullName = view.findViewById(R.id.tilFullName);
        tilEmail = view.findViewById(R.id.tilEmail);
        tilAddress = view.findViewById(R.id.tilAddress);

        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);

        view.findViewById(R.id.btnUpdateProfileInfo).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        storingUserProfileInformation();
    }

    private void storingUserProfileInformation() {
        String fullName = tilFullName.getEditText().getText().toString().trim();
        String email = tilEmail.getEditText().getText().toString().trim();
        String address = tilAddress.getEditText().getText().toString().trim();
        String userUID = mAuth.getUid();

        UserModel userModel = new UserModel(fullName,email,address,userUID);
        mRef.child(userUID).setValue(userModel);

        tvProfileName.setText(userModel.getFullName());
        tvProfileEmail.setText(userModel.getEmail());
    }



}
