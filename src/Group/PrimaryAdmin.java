package Group;

import backend.Database;
import backend.User;

import java.io.IOException;

public class PrimaryAdmin extends Admin{

    private static PrimaryAdmin instance;

    private PrimaryAdmin(Group group, User user) {
        super(group, user);
    }

    public static PrimaryAdmin getInstance(Group group, User user){
        if (instance == null)
            return new PrimaryAdmin(group, user);
        return instance;
    }

    private PrimaryAdmin(Group group, String userId) throws IOException {
        super(group, Database.getInstance().getUser(userId));
    }

    public static PrimaryAdmin getInstance(Group group, String userId) throws IOException {
        if (instance == null)
            instance = new PrimaryAdmin(group, userId);
        return instance;
    }

    public void makeAdmin(Member member){
        group.addAdmin(member);
    }

    public void demoteAdmin(Admin admin){
        group.removeAdmin(admin);
    }
}
