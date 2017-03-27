package com.innovsoft.gossip.database;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innovsoft.gossip.bean.User;
import com.innovsoft.gossip.utils.Constants;

/**
 * Created by SriMaddy on 3/27/2017.
 */

public class FirebaseDBHelper {

    private static Context mContext;
    private static FirebaseDatabase firebaseDatabase;

    public FirebaseDBHelper(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void addUser(User user) {
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child(Constants.KEY_USERS).push();

        user.setKey(databaseReference.getKey());

        databaseReference.setValue(user);
    }
}
