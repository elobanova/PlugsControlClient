package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.PlugsControlActivity;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class PlugsListGetRequest {
    private OnResponseListener onResponseListener;

    public void send() {
        new HttpGetPlugsTask().execute();
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetPlugsTask extends AsyncTask<Void, Void, List<IListItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onResponseListener.onPreExecute();
        }

        @Override
        protected List<IListItem> doInBackground(Void... params) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://" + PlugsControlActivity.SERVER_IP + ":" + PlugsControlActivity.SERVER_PORT + "/api/plugs");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int status = conn.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        return JSONPlugsParser.parse(sb.toString());
                }

            } catch (MalformedURLException e) {
                onResponseListener.onError(e.getMessage());
            } catch (IOException e) {
                onResponseListener.onError(e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<IListItem> items) {
            super.onPostExecute(items);
            onResponseListener.onResponse(items);
        }
    }
}
