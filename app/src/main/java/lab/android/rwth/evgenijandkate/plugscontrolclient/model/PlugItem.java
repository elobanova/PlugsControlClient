package lab.android.rwth.evgenijandkate.plugscontrolclient.model;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class PlugItem implements IListItem {
    private final int id;
    private final String name;
    private StateEnum state;

    public PlugItem(int id, String name, StateEnum state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    @Override
    public int getListItemId() {
        return this.id;
    }

    @Override
    public String getListItemLabel() {
        return this.name;
    }
}
