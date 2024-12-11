package Group;

import backend.User;
import content.ContentFields;
import content.Post;

public class Admin extends Member{

    public Admin(Group group, User user){
        super(user, group);
    }

    public void deleteMember(Member member){
        group.getMembers().remove(member);
    }

    public void acceptRequest(MembershipRequest request){
        group.addMember(new Member(request.getSender(), group));
        request.setStatus("accepted");
    }

    public void declineRequest(MembershipRequest request){
        request.setStatus("declined");
    }

    public void removeMember(Member member){
        group.removeMember(member);
    }

    public void editPost(Post post, ContentFields field){
        group.getGroupContent().editPost(post, field);
    }

    public void removePost(Post post){
        group.getGroupContent().removePost(post);
    }
}
