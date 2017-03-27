package com.innovsoft.gossip;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.innovsoft.gossip.bean.User;
import com.innovsoft.gossip.database.FirebaseDBHelper;
import com.innovsoft.gossip.utils.Constants;
import com.innovsoft.gossip.utils.customfonts.MyEditText;
import com.innovsoft.gossip.utils.customfonts.MyTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Calendar;

/**
 * Created by SriMaddy on 3/26/2017.
 */

public class SignupActivity extends AppCompatActivity {

    private MyEditText usernameTxt;
    private MyEditText emailTxt;
    private MyEditText passwordTxt;
    private MyTextView btnSignup;

    private FirebaseDBHelper dbHelper;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initFirebase();
        findViewsById();
    }

    private void initFirebase() {
        dbHelper = new FirebaseDBHelper(this.getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void findViewsById() {
        usernameTxt = (MyEditText) findViewById(R.id.username);
        emailTxt = (MyEditText) findViewById(R.id.mail);
        passwordTxt = (MyEditText) findViewById(R.id.password);
        btnSignup = (MyTextView) findViewById(R.id.signup_btn);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendRequest();
                String username = usernameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                addUserInFirebase(username, email, password);
            }
        });
    }

    private void addUserInFirebase(final String username, final String email, final String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User();
                            user.setUsername(username);
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setTime(Calendar.getInstance().getTimeInMillis());
                            dbHelper.addUser(user);
                            Toast.makeText(SignupActivity.this, "User created", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignupActivity.this, ProfilePhotoSetupActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendRequest() {
        // volley library code
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = Constants.SIGNUP_URL;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", usernameTxt.getText().toString());
            jsonBody.put("email", emailTxt.getText().toString());
            jsonBody.put("password", passwordTxt.getText().toString());
            jsonBody.put("time", Calendar.getInstance().getTimeInMillis());
            final String mRequestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("response=>", response);

                    Type type = new TypeToken<User>() {}.getType();
                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

                    User user = gson.fromJson(response, type);

                    Log.w("user", user.toString());
//                    saveUserToSharedPreference(user);
//                    moveToHomeActivity();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error=>", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                mRequestBody, "utf-8");
                        return null;
                    }
                }

            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
