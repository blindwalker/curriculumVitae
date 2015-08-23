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

    public Session(String token, int expires, User user) {
        this.token = token;
        this.expires = expires;
        this.user = user;
    }

    public static Session readSession(JSONObject response) {
        try {
            User user = User.readUser(response);
            Session session = new Session(
                    response.getString("token"),
                    response.getInt("expires"),
                    user
            );

            return session;
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
}
