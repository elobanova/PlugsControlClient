package lab.android.rwth.evgenijandkate.plugscontrolclient;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.adapter.PlugsListAdapter;
import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.LogInFragment;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.User;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.AddPlugRequest;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.DeletePlugRequest;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.OnResponseListener;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class PlugsListFragment extends ListFragment {
    public static final String PLUGS_LIST_KEY = "PLUGS";
    private static final int ADD_PLUG_REQUEST = 0;
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
        User connectedUser = LogInFragment.getConnectedUser();
        if (connectedUser != null && connectedUser.isAdmin()) {
            // Put divider between items and FooterView
            getListView().setFooterDividersEnabled(true);
            LinearLayout footerView = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.footer_view, null);
            getListView().addFooterView(footerView);
            initAddPlugButton();
            initDeletePlugButton();
        }
    }

    private void initAddPlugButton() {
        final Button addPlugButton = (Button) getListView().findViewById(R.id.add_plug_button);
        addPlugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addActivityIntent = new Intent(getActivity(), AddPlugActivity.class);
                startActivityForResult(addActivityIntent, ADD_PLUG_REQUEST);
            }
        });
    }

    private void initDeletePlugButton() {
        final Button deletePlugsButton = (Button) getListView().findViewById(R.id.delete_plug_button);
        deletePlugsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletePlugRequest deletePlugRequest = new DeletePlugRequest(adapter.getItems());
                deletePlugRequest.setOnResponseListener(new OnResponseListener<Boolean>() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onResponse(Boolean responseOK) {
                        if (responseOK) {
                            removeCheckedItems();
                            deletePlugsButton.setEnabled(adapter.atLeastOneItemIsChecked());
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
                deletePlugRequest.send();
            }
        });
    }

    private void removeCheckedItems() {
        List<IListItem> itemsToDelete = new ArrayList<>();
        for (IListItem adapterItem : adapter.getItems()) {
            if (adapterItem.isChecked()) {
                itemsToDelete.add(adapterItem);
            }
        }

        adapter.removeAll(itemsToDelete);
    }

    private void addItemsToAdapter() {
        if (this.adapter != null && this.items != null) {
            for (IListItem plugItem : this.items) {
                this.adapter.add(plugItem);
            }
        }
    }
}
