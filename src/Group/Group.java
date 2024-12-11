package Group;

import java.io.IOException;
import java.util.ArrayList;
import utils.*;

public class Group {
    String name;
    String description;
    Picture groupPhoto;
    ArrayList<Member> members;
    String groupId;


    public Group(String name, String description, String groupPhotoPath) throws IOException {
        this.name = name;
        this.description = description;
        this.groupPhoto = new Picture(groupPhotoPath);
        this.groupId = Utilities.generateId();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Picture getGroupPhoto() {
        return groupPhoto;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGroupPhoto(String groupPhotoPath) throws IOException {
        this.groupPhoto = groupPhoto.setImage(groupPhotoPath);
    }

    public boolean includeUser(Member member){
        return members.contains(member);
    }

}
