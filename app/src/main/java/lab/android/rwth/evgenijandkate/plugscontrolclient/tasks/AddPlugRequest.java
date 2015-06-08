package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.LogInFragment;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.PlugTransferableData;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.User;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class AddPlugRequest {
    public static final String NAME_PROPERTY = "name";
    public static final String HOUSE_CODE_PROPERTY = "house_code";
    public static final String SWITCH_CODE_PROPERTY = "switch_code";
    public static final String STATE_PROPERTY = "state";
    private OnResponseListener onResponseListener;

    public void send(PlugTransferableData plugData) {
        new HttpAddPlugTask().execute(plugData);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpAddPlugTask extends AsyncTask<PlugTransferableData, Void, Boolean> {

        @Override
        protected Boolean doInBackground(PlugTransferableData... args) {
            HttpURLConnection conn = null;
            User connectedUser = LogInFragment.getConnectedUser();
            if (connectedUser == null) return false;
            try {
                PlugTransferableData plugData = args[0];
                JSONObject plugAsJson = new JSONObject();
                plugAsJson.put(NAME_PROPERTY, plugData.getName());
                plugAsJson.put(HOUSE_CODE_PROPERTY, plugData.getHouseCode());
                plugAsJson.put(SWITCH_CODE_PROPERTY, plugData.getSwitchCode());
                plugAsJson.put(STATE_PROPERTY, plugData.getState().getName());

                URL url = new URL("http://" + connectedUser.getIpValue() + ":" + connectedUser.getPortValue() + "/api/plugs");

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(connectedUser.getEmailAddress(), connectedUser.getPassword()));
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Content-Length", "" +
                        Integer.toString(plugAsJson.toString().length()));
                conn.setRequestProperty("Content-Language", "en-US");

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(
                        conn.getOutputStream());
                dataOutputStream.write(plugAsJson.toString().getBytes("UTF-8"));
                dataOutputStream.flush();
                dataOutputStream.close();

                int status = conn.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        return true;
                }
            } catch (MalformedURLException e) {
                onResponseListener.onError(e.getMessage());
            } catch (IOException e) {
                onResponseListener.onError(e.getMessage());
            } catch (JSONException e) {
                onResponseListener.onError(e.getMessage());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onResponseListener.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean isCreated) {
            super.onPostExecute(isCreated);
            onResponseListener.onResponse(isCreated);
        }
    }
}
