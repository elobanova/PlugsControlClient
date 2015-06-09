package lab.android.rwth.evgenijandkate.plugscontrolclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.OnResponseListener;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.PlugsListGetRequest;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class PlugsControlFragment extends Fragment {
    private final static String FRAGMENT_TAG = "list_fragment";
    private PlugsListGetRequest getRequest;
    private PlugsListFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //retain state on configuration change (e.g. on orientation change)
        setRetainInstance(true);
        loadData();
    }

    private void loadData() {
        getRequest = new PlugsListGetRequest(getActivity());
        getRequest.setOnResponseListener(new OnResponseListener<List<IListItem>>() {

            @Override
            public void onPreExecute() {

            }

            @Override
            public void onResponse(List<IListItem> items) {
                FragmentManager fragmentManager = getFragmentManager();
                //fetch the fragment if it was saved (e.g. during orientation change)
                fragment = (PlugsListFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
                if (fragment == null) {
                    // add the fragment
                    fragment = PlugsListFragment.newInstance(items);
                    fragmentManager.beginTransaction().add(R.id.plugs_control_fragment_container, fragment, FRAGMENT_TAG).commit();
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
        getRequest.send();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.plugs_control_fragment, container, false);
    }

    public void refreshList() {
        FragmentManager fragmentManager = getFragmentManager();
        fragment = (PlugsListFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment != null) {
            // remove the fragment
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
        loadData();
    }
}
