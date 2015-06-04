package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
                URL url = new URL("http://192.168.0.17:3000/api/plugs");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
              //  conn.connect();
                return JSONPlugsParser.parse("here goes the string from conn");
            } catch (MalformedURLException e) {
                onResponseListener.onError(e.getMessage());
            } catch (IOException e) {
                onResponseListener.onError(e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
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
