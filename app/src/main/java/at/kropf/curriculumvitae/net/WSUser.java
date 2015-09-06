package at.kropf.curriculumvitae.net;

import android.content.Context;

import org.json.JSONException;

import at.kropf.curriculumvitae.R;
import at.kropf.curriculumvitae.net.model.UserRequestObject;
import at.kropf.mkropfvolley.BaseRequestData;
import at.kropf.mkropfvolley.ResponseListener;

/**
 * User Webservice Class
 * Holds methods for userCheck and login
 */
public class WSUser {

    private final Context context;

    BaseRequestData baseRequestData;

    public WSUser(Context context, ResponseListener listener) {
        this.context = context;
        baseRequestData = new BaseRequestData(context, listener);
    }

    public void doLoginFirst(String email) throws JSONException {

        UserRequestObject user = new UserRequestObject();
        user.setUsername(email);

        baseRequestData.doPost(context.getResources().getString(R.string.initialIP) + context.getString(R.string.doLogin), user.toJSONString());

    }

    public void doLoginSecond(String email, String password) throws JSONException {

        UserRequestObject user = new UserRequestObject();
        user.setUsername(email);
        user.setPassword(password);

        baseRequestData.doPost(context.getResources().getString(R.string.initialIP) + context.getString(R.string.doLogin2), user.toJSONString());

    }

}