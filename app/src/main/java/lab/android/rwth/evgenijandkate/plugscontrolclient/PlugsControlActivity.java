package lab.android.rwth.evgenijandkate.plugscontrolclient;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.LogInFragment;

public class PlugsControlActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "data";
    public static final int MENU_LOGOUT = Menu.FIRST;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_LOGOUT, Menu.NONE, R.string.logout_menu_label);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_LOGOUT:
                LogInFragment.performLogout(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
