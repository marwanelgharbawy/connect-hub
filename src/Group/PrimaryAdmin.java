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

    public void makeAdmin(Member member){
        group.addAdmin(member);
    }

    public void demoteAdmin(Admin admin){
        group.removeAdmin(admin);
    }

    public void deleteGroup(){
        group = null;
    }

}
