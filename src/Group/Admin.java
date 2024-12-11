package Group;

import backend.User;

public class Admin {
    Group group;
    Member member;

    public Admin(Group group, Member member){
        this.group = group;
        this.member = member;
    }

    public void deleteMember(Member member){
        group.getMembers().remove(member);
    }

}
