package com.innovsoft.gossip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.innovsoft.gossip.database.FirebaseDBHelper;

/**
 * Created by SriMaddy on 3/27/2017.
 */

public class ProfilePhotoSetupActivity extends AppCompatActivity {

    private FirebaseDBHelper dbHelper;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo_setup);

        initFirebase();
        findViewsById();
    }

    private void initFirebase() {
        dbHelper = new FirebaseDBHelper(this.getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void findViewsById() {

    }
}
