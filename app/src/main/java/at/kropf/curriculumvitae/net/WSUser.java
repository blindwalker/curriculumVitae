package at.kropf.curriculumvitae.net;

import android.content.Context;

import org.json.JSONException;

import at.kropf.curriculumvitae.R;
import at.kropf.curriculumvitae.net.model.UserRequestObject;

/**
 * Created by martinkropf on 01.07.15.
 */
public class WSUser extends BaseRequestData {

    Context context;

    public WSUser(Context context, ResponseListener listener) {
        super(context, listener);
        this.context = context;
    }

    public void doLoginFirst(String email) throws JSONException {

        UserRequestObject user = new UserRequestObject();
        user.setUsername(email);

        performCall(context.getResources().getString(R.string.initialIP) + context.getString(R.string.doLogin), user.toJSONString());


    }

    public void doLoginSecond(String email, String password) throws JSONException {

        UserRequestObject user = new UserRequestObject();
        user.setUsername(email);
        user.setPassword(password);

        performCall(context.getResources().getString(R.string.initialIP) + context.getString(R.string.doLogin2), user.toJSONString());


    }

}