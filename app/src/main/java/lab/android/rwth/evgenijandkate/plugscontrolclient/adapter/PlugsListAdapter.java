package lab.android.rwth.evgenijandkate.plugscontrolclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import lab.android.rwth.evgenijandkate.plugscontrolclient.R;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.StateEnum;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.OnResponseListener;
import lab.android.rwth.evgenijandkate.plugscontrolclient.tasks.StateChangeRequest;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class PlugsListAdapter extends AbstractListAdapter<IListItem> {
    public PlugsListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.plugs_list_item, parent, false);
        } else {
            view = convertView;
        }
        if (view instanceof LinearLayout) {
            final IListItem item = (IListItem) getItem(position);
            TextView plugName = (TextView) view.findViewById(R.id.plug_name_in_list);
            plugName.setText(item.getListItemLabel());

            initSwitcher(view, item);
            initCheck(view, item, parent);
        }
        return view;
    }

    private void initCheck(View view, final IListItem item, ViewGroup parent) {
        if (parent instanceof ListView) {
            CheckBox check = (CheckBox) view.findViewById(R.id.plug_check_box);
            ListView parentList = (ListView) parent;
            final Button deletePlugsButton = (Button) parentList.findViewById(R.id.delete_plug_button);

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setChecked(isChecked);
                    deletePlugsButton.setEnabled(atLeastOneItemIsChecked());
                }
            });
        }
    }

    public boolean atLeastOneItemIsChecked() {
        if (this.items != null) {
            for (IListItem listItem : this.items) {
                if (listItem.isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initSwitcher(View view, final IListItem item) {
        Switch switcher = (Switch) view.findViewById(R.id.switcher);
        switcher.setChecked(StateEnum.ON.equals(item.getState()));
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final StateEnum oldState = item.getState();
                item.setState(isChecked ? StateEnum.ON : StateEnum.OFF);
                StateChangeRequest stateChangeRequest = new StateChangeRequest(item);
                stateChangeRequest.setOnResponseListener(new OnResponseListener<Boolean>() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onResponse(Boolean responseOK) {
                        if (!responseOK) {
                            item.setState(oldState);
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
                stateChangeRequest.send();
            }
        });
    }
}
