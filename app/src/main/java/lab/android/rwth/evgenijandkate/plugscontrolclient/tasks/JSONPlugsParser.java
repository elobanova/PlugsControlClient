package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.PlugItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.StateEnum;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class JSONPlugsParser {
    private static final String ID_PROPERTY_NAME = "id";
    private static final String LABEL_PROPERTY_NAME = "name";
    private static final String STATE_PROPERTY_NAME = "state";

    public static IListItem parseItem(String jsonString) throws JSONException {
        return parseItem(new JSONObject(jsonString));
    }

    public static List<IListItem> parse(String jsonString) throws JSONException {
        List<IListItem> items = new ArrayList<>();
        JSONArray plugsList = new JSONArray(jsonString);
        for (int i = 0; i < plugsList.length(); i++) {
            JSONObject plugJSONObject = plugsList.getJSONObject(i);
            items.add(parseItem(plugJSONObject));
        }
        return items;
    }

    private static IListItem parseItem(JSONObject plugJSONObject) throws JSONException {
        StateEnum plugState = StateEnum.valueOf(plugJSONObject.getString(STATE_PROPERTY_NAME));
        int plugId = plugJSONObject.getInt(ID_PROPERTY_NAME);
        String plugLabel = plugJSONObject.getString(LABEL_PROPERTY_NAME);
        return new PlugItem(plugId, plugLabel, plugState);
    }
}
