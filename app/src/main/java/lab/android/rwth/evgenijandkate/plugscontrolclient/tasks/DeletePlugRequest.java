package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.LogInFragment;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.User;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class DeletePlugRequest {
    private OnResponseListener onResponseListener;
    private List<IListItem> itemsCheckedToBeDeleted;

    public DeletePlugRequest(List<IListItem> items) {
        this.itemsCheckedToBeDeleted = new ArrayList<>();
        for (IListItem listItem : items) {
            if (listItem.isChecked()) {
                this.itemsCheckedToBeDeleted.add(listItem);
            }
        }
    }

    public void send() {
        new HttpDeletePlugTask().execute(itemsCheckedToBeDeleted);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpDeletePlugTask extends AsyncTask<List<IListItem>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(List<IListItem>... params) {
            for (IListItem listItem : params[0]) {
                if (!connectToDeleteItem(listItem.getListItemId())) {
                    return false;
                }
            }
            return true;
        }

        private boolean connectToDeleteItem(int listItemId) {
            HttpURLConnection conn = null;
            User connectedUser = LogInFragment.getConnectedUser();
            if (connectedUser == null) return false;
            try {
                URL url = new URL("http://" + connectedUser.getIpValue() + ":" + connectedUser.getPortValue() + "/api/plugs/" + listItemId);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(connectedUser.getEmailAddress(), connectedUser.getPassword()));
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
        protected void onPostExecute(Boolean statusChangedSuccessfully) {
            super.onPostExecute(statusChangedSuccessfully);
            onResponseListener.onResponse(statusChangedSuccessfully);
        }
    }
}
