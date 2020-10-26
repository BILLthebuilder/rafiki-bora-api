package rafikibora.dto;

import java.util.ArrayList;
import java.util.List;

public class ListSystemUser {

    private final List<SystemUser> systemUsers;

    public ListSystemUser(List<SystemUser> systemUsers){
        this.systemUsers = systemUsers;
    }

    public List<SystemUser> getSystemUsers() {
        return new ArrayList<>(systemUsers);
    }

}
