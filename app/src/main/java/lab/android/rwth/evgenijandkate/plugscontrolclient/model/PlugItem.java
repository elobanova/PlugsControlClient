package lab.android.rwth.evgenijandkate.plugscontrolclient.model;

/**
 * Created by ekaterina on 04.06.2015.
 */
public class PlugItem implements IListItem {
    private final int id;
    private String name;
    private StateEnum state;
    private boolean isChecked = false;

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

    public void setListItemLabel(String name) {
        this.name = name;
    }

    @Override
    public StateEnum getState() {
        return this.state;
    }

    @Override
    public void setState(StateEnum stateEnum) {
        this.state = stateEnum;
    }

    @Override
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public boolean isChecked() {
        return this.isChecked;
    }
}
