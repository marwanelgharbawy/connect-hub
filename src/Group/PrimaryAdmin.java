package Group;

import backend.User;

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

    public void makeAdmin(Member member){
        group.addAdmin(member);
    }

    public void demoteAdmin(Admin admin){
        group.removeAdmin(admin);
    }
}
