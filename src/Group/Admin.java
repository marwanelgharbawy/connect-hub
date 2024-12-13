package Group;

import Group.MembershipManager.MembershipRequest;
import Group.MembershipManager.MembershipRequestManager;
import Group.MembershipManager.MembershipRequestStatus;
import backend.CurrentUser;
import backend.User;
import content.ContentFields;
import content.Post;

import java.io.IOException;

public class Admin extends Member{

    public Admin(User user, Group group) throws IOException {
        super(user, group);
    }

    public void acceptRequest(MembershipRequest request){
        group.addMember(new Member(request.getSender(), group));
        request.setStatus(MembershipRequestStatus.Approved);
    }

    public void declineRequest(MembershipRequest request){
        request.setStatus(MembershipRequestStatus.Declined);
    }

    public void removeMember(Member member){
        group.removeMember(member);
    }

    public void removeMember(User user){
        group.removeMember(user);
    }

    public void addMember(CurrentUser currentUser){
        group.addMember(currentUser);
    }

    public void addMember(Member member){
        group.addMember(member);
    }

    public void addMember(User user){
        group.addMember(user);
    }

    public void removeMember(CurrentUser currentUser){
        group.removeMember(currentUser);
    }

    public void editPost(Post post, ContentFields field){
        group.getGroupContent().editPost(post, field);
    }

    public void removePost(Post post){
        group.getGroupContent().removePost(post);
    }
}
