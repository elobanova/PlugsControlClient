package lab.android.rwth.evgenijandkate.plugscontrolclient.model;

/**
 * Created by ekaterina on 04.06.2015.
 */
public enum RoleEnum {
    ADMIN("admin"), USER("user");

    private final String name;
    private boolean isAdmin;

    private RoleEnum(String name) {
        this.name = name;
    }

    public static boolean isAdmin(String role) {
        return RoleEnum.ADMIN.name.equals(role);
    }
}
