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
    private String image;

    //JSON parsing
    public static User readUser(JSONObject response) throws JSONException {

        User user = new User();
        user.setUsername(response.getString("username"));
        user.setName(response.getString("firstname"));
        user.setImage(response.getString("img"));
        return user;
    }


    //SETTER
    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //GETTER
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
