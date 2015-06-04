package lab.android.rwth.evgenijandkate.plugscontrolclient.tasks;

import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;

/**
 * Created by ekaterina on 04.06.2015.
 */
public interface OnResponseListener {
    void onPreExecute();

    void onResponse(List<IListItem> items);

    void onError(String errorMessage);
}
