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

    public Session(User user) {
        this.user = user;
    }

    public Session(String token, int expires, User user) {
        this.token = token;
        this.expires = expires;
        this.user = user;
    }

    public static Session readSessionFirst(JSONObject response) {
        try {
            User user = User.readUser(response);
            Session session = new Session(
                    user
            );

            return session;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Session readSessionSecond(Session session, JSONObject response) {
        try {
            session.setToken(response.getString("token"));
            session.setExpires(response.getInt("expires"));

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


    public void setToken(String token) {
        this.token = token;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
