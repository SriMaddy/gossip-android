package com.innovsoft.gossip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.innovsoft.gossip.bean.User;
import com.innovsoft.gossip.utils.SharedPreference;

/**
 * Created by SriMaddy on 4/1/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Activity activity = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreference sharedPreference = new SharedPreference();
                User user = sharedPreference.getUser(activity);
                if(user != null) {
                    if(user.getImagePath() == null) {
                        Intent intent = new Intent(activity, ProfilePhotoSetupActivity.class);
                        startActivity(intent);
                        activity.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        finish();
                    } else if(user.getCircles() == null) {
//                Intent intent = new Intent(activity, CircleSelectionActivity.class);
//                startActivity(intent);
//                activity.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
//                finish();
                    }
                } else {
                    Intent intent = new Intent(activity, SignupActivity.class);
                    startActivity(intent);
                    activity.overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                    finish();
                }
            }
        }, 3000);
    }
}
