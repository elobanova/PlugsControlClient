package lab.android.rwth.evgenijandkate.plugscontrolclient;

import android.app.ListFragment;
import android.os.Bundle;

import java.io.Serializable;
import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.adapter.PlugsListAdapter;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.OnResponseListener;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.PlugsListGetRequest;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class PlugsListFragment extends ListFragment {
    public static final String PLUGS_LIST_KEY = "PLUGS";
    private List<IListItem> items;
    private PlugsListAdapter adapter;
    private PlugsListGetRequest getRequest;

    public static PlugsListFragment newInstance(List<IListItem> items) {
        Bundle args = new Bundle();
        args.putSerializable(PLUGS_LIST_KEY, (Serializable) items);
        PlugsListFragment fragment = new PlugsListFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.items = (List<IListItem>) getArguments().getSerializable(PLUGS_LIST_KEY);

        this.adapter = new PlugsListAdapter(getActivity().getApplicationContext());
        setListAdapter(this.adapter);
        loadData();
    }

    private void loadData() {
        getRequest = new PlugsListGetRequest();
        getRequest.setOnResponseListener(new OnResponseListener() {

            @Override
            public void onPreExecute() {

            }

            @Override
            public void onResponse(List<IListItem> items) {
                PlugsListFragment.this.items = items;
                addItemsToAdapter();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
        getRequest.send();
    }

    private void addItemsToAdapter() {
        if (this.adapter != null && this.items != null) {
            for (IListItem plugItem : this.items) {
                this.adapter.add(plugItem);
            }
        }
    }
}
