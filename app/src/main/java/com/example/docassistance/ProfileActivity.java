package com.example.docassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "tag";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();

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
                Log.d(TAG, "logout: "+ mAuth.getCurrentUser());
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }


}
