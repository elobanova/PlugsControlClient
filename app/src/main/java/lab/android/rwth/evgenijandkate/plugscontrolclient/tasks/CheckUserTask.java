package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import lab.android.rwth.evgenijandkate.plugscontrolclient.PlugsControlActivity;
import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.LogInFragment;
import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.SSLContextHelper;
import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.SignInActivity;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.PlugTransferableData;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.StateEnum;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.User;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class CheckUserTask {
    private Context context;
    public CheckUserTask(Context context){
        this.context=context;
    }
    private OnResponseListener onResponseListener;

    public void send(User user) {
        new HttpCheckUserTask().execute(user);
    }

    public void setOnResponseListener(OnResponseListener onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    private class HttpCheckUserTask extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... args) {
            User loggingInUser = args[0];
            HttpsURLConnection conn = null;
            try {
                URL url = new URL("https://" + loggingInUser.getIpValue() + ":" + loggingInUser.getPortValue() + "/api/authenticate");
                conn = (HttpsURLConnection) url.openConnection();
                conn.setSSLSocketFactory(SSLContextHelper.initSSLContext(context).getSocketFactory());
                conn.setHostnameVerifier(SSLContextHelper.getHostnameVerifier());
                conn.setRequestMethod("GET");
                conn.addRequestProperty("Authorization", LogInFragment.getB64Auth(loggingInUser.getEmailAddress(), loggingInUser.getPassword()));
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
                        return JSONUserParser.parse(sb.toString(), loggingInUser);
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
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onResponseListener.onPreExecute();
        }

        @Override
        protected void onPostExecute(User connectedUser) {
            super.onPostExecute(connectedUser);
            onResponseListener.onResponse(connectedUser);
        }
    }
}
