package lab.android.rwth.evgenijandkate.plugscontrolclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import lab.android.rwth.evgenijandkate.plugscontrolclient.R;
import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;

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
            IListItem item = (IListItem) getItem(position);
            TextView plugName = (TextView) view.findViewById(R.id.plug_name_in_list);
            plugName.setText(item.getListItemLabel());
        }
        return view;
    }
}
