package lab.android.rwth.evgenijandkate.plugscontrolclient.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 07.06.2015.
 */
public class User implements Serializable {
    private String emailAddress;
    private String password;
    private String ipValue;
    private String portValue;

    public User(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public User(String emailAddress, String password, String ipValue, String portValue) {
        this(emailAddress, password);
        this.ipValue = ipValue;
        this.portValue = portValue;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public String getIpValue() {
        return this.ipValue;
    }

    public String getPortValue() {
        return this.portValue;
    }
}