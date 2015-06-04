package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

/**
 * Created by ekaterina on 04.06.2015.
 */
public interface OnResponseListener<T> {
    void onPreExecute();

    void onResponse(T response);

    void onError(String errorMessage);
}
