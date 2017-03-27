package com.innovsoft.gossip.request;

import com.innovsoft.gossip.bean.User;
import com.octo.android.robospice.request.SpiceRequest;

import okhttp3.RequestBody;

/**
 * Created by SriMaddy on 3/27/2017.
 */

public class SignupRequest extends SpiceRequest<User> {

    private User mUser;

    public SignupRequest(User user) {
        super(User.class);
        this.mUser = user;
    }

    @Override
    public User loadDataFromNetwork() throws Exception {
//        RequestBody formBody = new FormEncodingBuilder
        return null;
    }
}
