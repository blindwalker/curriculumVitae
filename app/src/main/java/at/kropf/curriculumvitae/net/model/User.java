package at.kropf.curriculumvitae.net.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by martinkropf on 23.08.15.
 * Model for User fetched from server
 */
public class User {

    private String username;
    private String name;


    //JSON parsing
    public static User readUser(JSONObject response) throws JSONException {

        User user = new User();
        user.setUsername(response.getJSONObject("user").getString("username"));
        user.setName(response.getJSONObject("user").getString("name"));

        return user;
    }


    //SETTER
    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }


    //GETTER
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
