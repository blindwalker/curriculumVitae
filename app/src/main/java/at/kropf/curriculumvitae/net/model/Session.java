package at.kropf.curriculumvitae.net.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by martinkropf on 23.08.15.
 */
public class Session {

    private String token;
    private int expires;
    private User user;

    private Session(User user) {
        this.user = user;
    }

    private Session(String token, int expires, User user) {
        this.token = token;
        this.expires = expires;
        this.user = user;
    }

    public static Session readSessionFirst(JSONObject response) {
        try {
            User user = User.readUser(response);

            return new Session(
                    user
            );
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Session readSessionSecond(JSONObject response) {
        try {
            User user = User.readUser(response.getJSONObject("user"));

            return new Session(
                    response.getString("token"),
                    response.getInt("expires")/1000,
                    user
            );
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getToken() {
        return token;
    }

    public int getExpires() {
        return expires;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
