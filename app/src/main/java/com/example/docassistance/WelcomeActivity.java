package com.example.docassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
    public static final String TAG = "WelcomeActivity";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setTitle("Welcome");
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: "+ mAuth.getCurrentUser()+" "+mAuth.getUid());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_Logout:
                userLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void userLogout() {
        mAuth.signOut();
        finish();
        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
    }
}
