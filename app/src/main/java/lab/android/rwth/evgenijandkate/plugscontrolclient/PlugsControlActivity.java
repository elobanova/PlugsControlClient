package lab.android.rwth.evgenijandkate.plugscontrolclient;

import android.app.FragmentManager;
import android.os.Bundle;

import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.ActivityWithLogoutMenu;

public class PlugsControlActivity extends ActivityWithLogoutMenu {
    private final static String FRAGMENT_TAG = "data";
    private PlugsControlFragment controlFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugs_control);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        controlFragment = (PlugsControlFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (controlFragment == null) {
            // add the fragment
            controlFragment = new PlugsControlFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, controlFragment, FRAGMENT_TAG).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (controlFragment != null) {
            controlFragment.refreshList();
        }
    }
}
