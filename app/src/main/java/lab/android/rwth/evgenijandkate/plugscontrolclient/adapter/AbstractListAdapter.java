package lab.android.rwth.evgenijandkate.plugscontrolclient.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lab.android.rwth.evgenijandkate.plugscontrolclient.model.IListItem;

/**
 * Created by ekaterina on 04.06.2015.
 */
public abstract class AbstractListAdapter<T extends Serializable> extends BaseAdapter {
    protected final List<T> items = new ArrayList<T>();
    protected final Context context;

    public AbstractListAdapter(Context context) {
        this.context = context;
    }

    /**
     * Adds the item into a list of items stored in adapter
     *
     * @param item a serializable item to be added to an adapter items
     */
    public void add(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    /**
     * Clears the items from a list of items stored in adapter
     */
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    public void removeAll(List<IListItem> itemsToDelete) {
        items.removeAll(itemsToDelete);
        notifyDataSetChanged();
    }
}
