package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
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

    public static List<IListItem> parse(String jsonString) throws JSONException {
        List<IListItem> items = new ArrayList<>();
        //TODO
        jsonString = "[{\"id\":1,\"name\":\"Plug A\",\"state\":\"OFF\"},{\"id\":2,\"name\":\"Plug B\",\"state\":\"OFF\"},{\"id\":3,\"name\":\"Plug C\",\"state\":\"OFF\"},{\"id\":4,\"name\":\"Plug D\",\"state\":\"OFF\"},{\"id\":5,\"name\":\"Awesome Plug\",\"state\":\"ON\"}]";
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            int plugId = jsonArray.getJSONObject(i).getInt(ID_PROPERTY_NAME);
            String plugLabel = jsonArray.getJSONObject(i).getString(LABEL_PROPERTY_NAME);
            StateEnum plugState = StateEnum.valueOf(jsonArray.getJSONObject(i).getString(STATE_PROPERTY_NAME));
            items.add(new PlugItem(plugId, plugLabel, plugState));
        }
        return items;
    }
}
