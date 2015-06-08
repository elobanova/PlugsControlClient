package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import org.json.JSONException;
import org.json.JSONObject;

import lab.android.rwth.evgenijandkate.plugscontrolclient.model.RoleEnum;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.User;

/**
 * Created by ekaterina on 08.06.2015.
 */
public class JSONUserParser {
    private static final String FIRST_NAME_PROPERTY_NAME = "first_name";
    private static final String LAST_NAME_PROPERTY_NAME = "last_name";
    private static final String ROLE_PROPERTY_NAME = "role";

    public static User parse(String jsonString, User loggingInUser) throws JSONException {
        JSONObject userJSONObject = new JSONObject(jsonString);
        String userFirstName = userJSONObject.getString(FIRST_NAME_PROPERTY_NAME);
        String userLastName = userJSONObject.getString(LAST_NAME_PROPERTY_NAME);
        boolean isAdmin = RoleEnum.isAdmin(userJSONObject.getString(ROLE_PROPERTY_NAME));
        return new User.UserBuilder(loggingInUser).firstName(userFirstName).lastName(userLastName).isAdmin(isAdmin).build();
    }
}
