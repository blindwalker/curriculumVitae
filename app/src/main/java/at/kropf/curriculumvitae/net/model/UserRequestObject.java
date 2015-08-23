package at.kropf.curriculumvitae.net.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by martinkropf on 23.08.15.
 */
public class UserRequestObject {

    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject toJSONString() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", this.username);
        jsonObject.put("password", this.password);
        return jsonObject;
    }
}
