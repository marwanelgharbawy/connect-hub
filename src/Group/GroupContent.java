package Group;

import content.ContentFields;
import content.Post;

import java.util.ArrayList;

public class GroupContent {
    Group group;
    ArrayList<Post> posts;

    public GroupContent(Group group){
        this.group = group;
        this.posts = new ArrayList<>();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post){
        this.posts.add(post);
        group.getGroupNotifManager().addNewPostNotif(post);
    }

    public void removePost(Post post){
        this.posts.remove(post);
    }

    public void editPost(Post post, ContentFields field){
        post.setContentFields(field);
    }
}
