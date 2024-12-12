package Group;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import utils.*;

public class Group {
    String groupId;
    String name;
    String description;
    Picture groupPhoto;
    Member primaryAdmin;
    ArrayList<Member> admins;
    ArrayList<Member> members;

    public Group(String name, String description, String groupPhotoPath) throws IOException {
        this.name = name;
        this.description = description;
        this.groupPhoto = new Picture(groupPhotoPath);
        this.groupId = Utilities.generateId();
    }

    public Group(String groupId) {
        this.groupId = groupId;
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

    public void setGroupData(JSONObject data) throws IOException {
        this.name = (String) data.get("name");
        this.description = (String) data.get("description");
        this.groupPhoto = new Picture((String) data.get("group-photo")); // Takes the path of the image
        // TODO: Parse JSON for the following attributes
//        this.primaryAdmin = new Member();
//        this.admins = new ArrayList<>();
//        this.members = new ArrayList<>();
    }

    public boolean includeUser(Member member){
        return members.contains(member);
    }

    public String getGroupId() {
        return groupId;
    }

    public JSONObject getGroupData() {
        JSONObject data = new JSONObject();
        data.put("name", name);
        data.put("description", description);
        // data.put("group-photo", /*photo's path*/ );
        // data.put("primary-admin", /*primaryAdmin*/ );
        // data.put("admins", /*admins*/ );
        // data.put("members", /*members*/ );
        return data;
    }
}