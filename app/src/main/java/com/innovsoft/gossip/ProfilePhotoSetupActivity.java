package com.innovsoft.gossip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.innovsoft.gossip.bean.User;
import com.innovsoft.gossip.database.FirebaseDBHelper;
import com.innovsoft.gossip.utils.SharedPreference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SriMaddy on 3/27/2017.
 */

public class ProfilePhotoSetupActivity extends AppCompatActivity {

    private ImageView bgImg;
    private CircleImageView circleImageView;
    private Button saveBtn;

    private FirebaseDBHelper dbHelper;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final int GALLERY_IMAGE_CHOOSER = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo_setup);

        initFirebase();
        findViewsById();

        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.w("user", "loggedin");
                } else {
                    // User is signed out
                    Log.w("user", "loggedout");
                }
                // ...
            }
        };

        firebaseAuth.createUserWithEmailAndPassword("sri1500@gmail.com", "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.w("task", "success");
                        } else {
                            Log.w("task", "failed");
                        }
                    }
                });*/
    }

    private void initFirebase() {
        dbHelper = new FirebaseDBHelper(this.getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        firebaseAuth.removeAuthStateListener(mAuthListener);
    }

    private void findViewsById() {
        bgImg = (ImageView) findViewById(R.id.bg_img);
        saveBtn = (Button) findViewById(R.id.save_btn);
        circleImageView = (CircleImageView) findViewById(R.id.profile_img);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), GALLERY_IMAGE_CHOOSER);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfilePicture();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_IMAGE_CHOOSER) {
            if(resultCode == Activity.RESULT_OK) {
                if(data != null) {
                    try {
                        Bitmap profileImgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        circleImageView.setImageBitmap(profileImgBitmap);
                        bgImg.setImageBitmap(profileImgBitmap);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void uploadProfilePicture() {
        circleImageView.setDrawingCacheEnabled(true);
        circleImageView.buildDrawingCache();
        Bitmap bitmap = circleImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://gossips-f2f19.appspot.com").child("images/1.jpg");
        StorageReference storageReference = firebaseStorage.getReference().child("images/1.jpg");

        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isComplete()) {
                    if(task.isSuccessful()) {
                        Log.w("upload", "ok");
                    }
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                saveUserToSharedPreference(downloadUrl.toString());
            }
        });
        /*uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                dbHelper.updateProfilePhoto("-KgFrYzdKXoREDUvnL_-", downloadUrl.toString());
            }
        });*/
    }

    private void saveUserToSharedPreference(String path) {
        SharedPreference sharedPreference = new SharedPreference();
        User user = sharedPreference.getUser(this);
        if(user != null) {
            dbHelper.updateProfilePhoto(user.getKey(), path);
            user.setImagePath(path);
            sharedPreference.saveUser(this, user);
        }
    }
}
