package lab.android.rwth.evgenijandkate.plugscontrolclient.model;

/**
 * Created by elobanova on 07.06.2015.
 */
public class PlugTransferableData {
    private String name;
    private String switchCode;
    private String houseCode;
    private StateEnum state;

    public PlugTransferableData(String name, String switchCode, String houseCode, StateEnum state) {
        this.name = name;
        this.switchCode = switchCode;
        this.houseCode = houseCode;
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSwitchCode(String code) {
        this.switchCode = code;
    }

    public void setHouseCode(String code) {
        this.houseCode = code;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    public String getSwitchCode() {
        return this.switchCode;
    }

    public String getHouseCode() {
        return this.houseCode;
    }

    public StateEnum getState() {
        return this.state;
    }
}
