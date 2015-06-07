package lab.android.rwth.evgenijandkate.plugscontrolclient;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.adapter.PlugsListAdapter;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class PlugsListFragment extends ListFragment {
    public static final String PLUGS_LIST_KEY = "PLUGS";
    private List<IListItem> items;
    private PlugsListAdapter adapter;

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
        addItemsToAdapter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Put divider between items and FooterView
        getListView().setFooterDividersEnabled(true);
        LinearLayout footerView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.footer_view, null);
        getListView().addFooterView(footerView);
    }

    private void addItemsToAdapter() {
        if (this.adapter != null && this.items != null) {
            for (IListItem plugItem : this.items) {
                this.adapter.add(plugItem);
            }
        }
    }
}
