package Group;

import java.io.IOException;
import java.util.ArrayList;

import backend.User;
import content.Post;
import utils.*;

import static Group.PrimaryAdmin.getInstance;

public class Group {
    private String name;
    private String description;
    private Picture groupPhoto;
    private final GroupContent groupContent;
    private ArrayList<Member> members;
    private ArrayList<Admin> admins;
    private final PrimaryAdmin primaryAdmin;
    private final String groupId;


    public Group(String name, String description, String groupPhotoPath, User primaryAdmin) throws IOException {
        this.name = name;
        this.description = description;
        this.groupPhoto = new Picture(groupPhotoPath);
        this.groupId = Utilities.generateId();
        this.primaryAdmin = getInstance(this, primaryAdmin);
        this.groupContent = GroupContent.getInstance();
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

    public boolean isMember(Member member){
        return members.contains(member);
    }

    public boolean isMember(User user){
        for (Member member: members)
            if (member.getUser() == user)
                return true;
        return false;
    }

    public boolean isAdmin(Member member){
        return admins.contains(member);
    }

    public boolean isAdmin(User user){
        for (Admin admin: admins)
            if (admin.getUser() == user)
                return true;
        return false;
    }

    public boolean isPrimaryAdmin(Member member){
        return member == getInstance(this, primaryAdmin.getUser());
    }

    public boolean isPrimaryAdmin(User user){
        return user == getInstance(this, primaryAdmin.getUser()).getUser();
    }

    public void removeMember(Member member){
        this.members.remove(member);
    }

    public void addMember(Member member){
        this.members.add(member);
    }

    public GroupContent getGroupContent() {
        return groupContent;
    }

    public void addPost(Post post){
        this.groupContent.addPost(post);
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public void addAdmin(Member member){
        members.remove(member);
        admins.add((Admin) member);
    }

    public void removeAdmin(Admin admin){
        members.add((Member) admin);
        admins.remove(admin);
    }

    public String getGroupId() {
        return groupId;
    }
}
