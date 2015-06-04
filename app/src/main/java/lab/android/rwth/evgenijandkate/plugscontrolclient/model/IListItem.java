package lab.android.rwth.evgenijandkate.plugscontrolclient.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 04.06.2015.
 */
public interface IListItem extends Serializable {
    int getListItemId();
    String getListItemLabel();
    StateEnum getState();

    void setState(StateEnum stateEnum);
}
