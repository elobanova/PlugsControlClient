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
    private String firstName;
    private String lastName;
    private boolean isAdmin = false;

    private User(UserBuilder builder) {
        this.emailAddress = builder.nestedEmailAddress;
        this.password = builder.nestedPassword;
        this.ipValue = builder.nestedIpValue;
        this.portValue = builder.nestedPortValue;
        this.isAdmin = builder.nestedIsAdmin;
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

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public static class UserBuilder {
        private String nestedEmailAddress;
        private String nestedPassword;
        private String nestedIpValue;
        private String nestedPortValue;
        private String nestedFirstName;
        private String nestedLastName;
        private boolean nestedIsAdmin;

        public UserBuilder(String nestedEmailAddress, String nestedPassword) {
            this.nestedEmailAddress = nestedEmailAddress;
            this.nestedPassword = nestedPassword;
        }

        public UserBuilder(User user) {
            this.nestedEmailAddress = user.emailAddress;
            this.nestedPassword = user.password;
            this.nestedIpValue = user.ipValue;
            this.nestedPortValue = user.portValue;
            this.nestedFirstName = user.firstName;
            this.nestedLastName = user.lastName;
            this.nestedIsAdmin = user.isAdmin;
        }

        public UserBuilder ipAddress(String ipAddress) {
            this.nestedIpValue = ipAddress;
            return this;
        }

        public UserBuilder portAddress(String port) {
            this.nestedPortValue = port;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.nestedFirstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.nestedLastName = lastName;
            return this;
        }

        public UserBuilder isAdmin(boolean isAdmin) {
            this.nestedIsAdmin = isAdmin;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}