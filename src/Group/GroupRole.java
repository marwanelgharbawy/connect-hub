package Group;

import backend.User;
import content.Post;

public interface GroupRole {
    User getUser();

    Group getGroup();

    void leaveGroup();

    void post(String text, String imagePath);
}
