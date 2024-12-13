package Group;

import backend.Database;
import backend.User;

import java.io.IOException;

public class PrimaryAdmin extends Admin{

    private static PrimaryAdmin instance;

    public PrimaryAdmin(User user, Group group) throws IOException {
        super(user, group);
    }

    public PrimaryAdmin(Group group, String userId) throws IOException {
        super(Database.getInstance().getUser(userId), group);
    }

    public void makeAdmin(User user){
        group.addAdmin(user);
    }

    public void demoteAdmin(User user){
        group.removeAdmin(user);
    }

    public void deleteGroup(){
        group = null;
    }

}
