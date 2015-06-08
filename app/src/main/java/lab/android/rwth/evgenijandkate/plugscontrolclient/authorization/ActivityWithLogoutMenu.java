package lab.android.rwth.evgenijandkate.plugscontrolclient.authorization;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import lab.android.rwth.evgenijandkate.plugscontrolclient.R;

/**
 * Created by ekaterina on 08.06.2015.
 */
public class ActivityWithLogoutMenu extends Activity {
    public static final int MENU_LOGOUT = Menu.FIRST;

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
