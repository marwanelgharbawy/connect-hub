package Group;

import backend.User;
import content.ContentFields;
import content.Post;

public class Member implements GroupRole{
    User user;
    Group group;

    public Member(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

    public void leaveGroup(){
        group.removeMember(this);
    }

    public void post(String text, String imagePath){
        ContentFields field = new ContentFields(text, imagePath);
        Post post = new Post(this.getUser().getUserId(), field);
        this.group.addPost(post);
    }
}
