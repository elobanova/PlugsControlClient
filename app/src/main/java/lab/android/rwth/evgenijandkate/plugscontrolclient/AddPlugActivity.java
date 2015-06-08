package lab.android.rwth.evgenijandkate.plugscontrolclient;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import lab.android.rwth.evgenijandkate.plugscontrolclient.authorization.LogInFragment;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class AddPlugActivity extends Activity {
    private final static String FRAGMENT_TAG = "add";
    private AddPlugFragment addPlugFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plug_activity);

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        addPlugFragment = (AddPlugFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (addPlugFragment == null) {
            // add the fragment
            addPlugFragment = new AddPlugFragment();
            fragmentManager.beginTransaction().add(R.id.add_plug_fragment_container, addPlugFragment, FRAGMENT_TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, PlugsControlActivity.MENU_LOGOUT, Menu.NONE, R.string.logout_menu_label);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PlugsControlActivity.MENU_LOGOUT:
                LogInFragment.performLogout(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
