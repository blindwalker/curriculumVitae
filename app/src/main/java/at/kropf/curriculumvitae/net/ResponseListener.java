package at.kropf.curriculumvitae.net;

import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: martinkropf
 * Date: 11.09.13
 * Time: 09:26
 * To change this template use File | Settings | File Templates.
 */
public interface ResponseListener {
    void onComplete(JSONObject json);

    void onError(Throwable error);
}
