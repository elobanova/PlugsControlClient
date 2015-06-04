package lab.android.rwth.evgenijandkate.plugscontrolclient.model;

/**
 * Created by ekaterina on 04.06.2015.
 */
public enum StateEnum {
    ON("ON"), OFF("OFF");

    private final String name;

    private StateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
