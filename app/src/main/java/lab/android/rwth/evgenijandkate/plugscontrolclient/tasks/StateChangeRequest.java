package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.StateEnum;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class StateChangeRequest {
    private OnResponseListener onResponseListener;
    private IListItem item;

    public StateChangeRequest(IListItem item) {
        this.item = item;
    }

    public void send() {
        new HttpGetChangeStateTask().execute(item);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpGetChangeStateTask extends AsyncTask<IListItem, Void, Boolean> {

        @Override
        protected Boolean doInBackground(IListItem... params) {
            HttpURLConnection conn = null;
            String path = StateEnum.ON.equals(params[0].getState()) ? "/turnON/" : "/turnOFF/";
            try {
                URL url = new URL("http://128.199.60.69:3000/api/plugs" + path + params[0].getListItemId());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
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
            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onResponseListener.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean statusChangedSuccessfully) {
            super.onPostExecute(statusChangedSuccessfully);
            onResponseListener.onResponse(statusChangedSuccessfully);
        }
    }
}
