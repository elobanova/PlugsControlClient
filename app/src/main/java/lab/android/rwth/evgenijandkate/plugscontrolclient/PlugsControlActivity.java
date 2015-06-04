package lab.android.rwth.evgenijandkate.plugscontrolclient;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.PlugItem;

public class PlugsControlActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "data";
    private PlugsListFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugs_control);
        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.fragment = (PlugsListFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (this.fragment == null) {
            // add the fragment
            this.fragment = PlugsListFragment.newInstance(fetchItems());
            fragmentManager.beginTransaction().add(R.id.fragment_container, this.fragment, FRAGMENT_TAG).commit();
        }
    }

    private List<IListItem> fetchItems() {
        List<IListItem> items = new ArrayList<>();
        return items;
    }
}
