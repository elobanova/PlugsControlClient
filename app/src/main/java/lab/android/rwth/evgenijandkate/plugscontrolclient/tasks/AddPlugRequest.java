package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import lab.android.rwth.evgenijandkate.plugscontrolclient.PlugsControlActivity;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.PlugTransferableData;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class AddPlugRequest {
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
            try {
                PlugTransferableData plugData = args[0];
                String urlParameters =
                        "name=" + URLEncoder.encode(plugData.getName(), "UTF-8") +
                                "&house_code=" + URLEncoder.encode(plugData.getHouseCode(), "UTF-8") +
                                "&switch_code=" + URLEncoder.encode(plugData.getSwitchCode(), "UTF-8") +
                                "&state=" + URLEncoder.encode(plugData.getState().getName(), "UTF-8");

                URL url = new URL("http://" + PlugsControlActivity.SERVER_IP + ":" + PlugsControlActivity.SERVER_PORT + "/api/plugs");

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                conn.setRequestProperty("Content-Length", "" +
                        Integer.toString(urlParameters.getBytes().length));
                conn.setRequestProperty("Content-Language", "en-US");

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                DataOutputStream dataOutputStream = new DataOutputStream(
                        conn.getOutputStream());
                dataOutputStream.writeBytes(urlParameters);
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
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return null;
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
