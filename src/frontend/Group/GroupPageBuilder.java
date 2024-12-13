package frontend.Group;

import Group.Group;
import Group.MembershipManager.MembershipRequest;
import Group.MembershipManager.MembershipRequestStatus;
import Group.MembershipManager.UserGroupStatus;
import backend.CurrentUser;
import backend.User;
import content.ContentFields;
import content.Post;
import frontend.contentCreation.GetContentFieldsWindow;
import frontend.newsFeed.PostCard;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import static Group.MembershipManager.UserGroupStatus.PrimaryAdmin;

public class GroupPageBuilder extends Component {
    Group group;
    CurrentUser currentUser;
    UserGroupStatus status;
    JPanel mainPanel;

    public GroupPageBuilder(Group group, CurrentUser user){
        this.group = group;
        this.currentUser = user;
        this.status = getStatus();
        this.mainPanel = new JPanel();
    }

    private UserGroupStatus getStatus() {
        if (group.isPrimaryAdmin(currentUser.getUser()))
            return PrimaryAdmin;
        if (group.isAdmin(currentUser.getUser()))
            return UserGroupStatus.Admin;
        if (group.isMember(currentUser.getUser()))
            return UserGroupStatus.Member;
        if (group.getMembershipManager().isRequestSent(group, currentUser.getUser()))
            return UserGroupStatus.RequestSent;
        return UserGroupStatus.NotMember;
    }

    private void setStatus(UserGroupStatus newStatus) {
        this.status = newStatus;
    }

    public JPanel buildInfoPanel(){
        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(1000, 350));

        JLabel nameLabel = new JLabel(group.getName());
        JLabel descriptionLabel = new JLabel(group.getDescription());
        JLabel groupPhotoLabel = new JLabel();
        groupPhotoLabel.setIcon(new ImageIcon(group.getGroupPhoto().getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));

        groupPhotoLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        infoPanel.add(groupPhotoLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(descriptionLabel);

        return infoPanel;
    }

    public JPanel buildButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(1000 ,150));
        switch (status){
            case UserGroupStatus.NotMember -> buttonPanel = notMemberButtonBuilder();
            case UserGroupStatus.RequestSent -> buttonPanel = requestSentButtonBuilder();
            case UserGroupStatus.Member -> buttonPanel = memberButtonBuilder();
            case UserGroupStatus.Admin -> buttonPanel = adminButtonBuilder();
            case PrimaryAdmin -> buttonPanel = primaryAdminButtonBuilder();
        }
        return buttonPanel;
    }

    private JPanel primaryAdminButtonBuilder() {
        JPanel primaryAdminButtonsPanel = new JPanel(new GridLayout(1, 7));
        JButton makeAdmins = new JButton("Make Admins");
        makeAdmins.addActionListener(_ -> makeAdminsActionListener());
        JButton demoteAdmins = new JButton("Demote Admins");
        demoteAdmins.addActionListener(_ -> demoteAdminsActionListener());
        JButton membershipRequests = new JButton("Review Membership requests");
        membershipRequests.addActionListener(_ -> membershipRequestsActionListeners());
        JButton removeMemberPA = new JButton("Remove Member");
        removeMemberPA.addActionListener(_ -> removeMemberPAActionListener());
        JButton managePosts = new JButton("Manage Posts");
        managePosts.addActionListener(_ -> managePostsActionListeners());
        JButton deleteGroup = new JButton("Delete Group");
        deleteGroup.addActionListener(_ -> deleteGroupActionListener());
        JButton post = new JButton("Post");
        post.addActionListener(_ -> postButtonActionListeners());
        primaryAdminButtonsPanel.add(makeAdmins);
        primaryAdminButtonsPanel.add(demoteAdmins);
        primaryAdminButtonsPanel.add(membershipRequests);
        primaryAdminButtonsPanel.add(removeMemberPA);
        primaryAdminButtonsPanel.add(managePosts);
        primaryAdminButtonsPanel.add(deleteGroup);
        primaryAdminButtonsPanel.add(post);

        return primaryAdminButtonsPanel;
    }

    private void demoteAdminsActionListener() {
        ArrayList<User> admins = new ArrayList<>(group.getAdmins());
        if (admins.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No admins to demote.");
            return;
        }

        JComboBox<User> userBox = new JComboBox<>();
        for (User user: admins)
            userBox.addItem(user);

        JPanel demoteAdminsPanel = new JPanel(new GridLayout());
        demoteAdminsPanel.add(new JLabel("Promote Member"));
        demoteAdminsPanel.add(userBox);
        int result = JOptionPane.showConfirmDialog(this,demoteAdminsPanel, "Demote Member", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION){
            User promoted = (User) userBox.getSelectedItem();
            if(promoted!=null){
                group.getPrimaryAdmin().demoteAdmin(promoted);
                JOptionPane.showMessageDialog(this, "Member "+promoted.getUsername()+" demoted successfully.", "Demoted Member", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void deleteGroupActionListener() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the group?", "Delete Group", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Implement group deletion logic here
//            group.getGroupNotifManager().notifyAllMembers("The group has been deleted.");
            JOptionPane.showMessageDialog(null, "Group deleted successfully.");
            group.getPrimaryAdmin().deleteGroup();
        }

    }

    private void removeMemberPAActionListener() {
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.addAll(group.getMembers());
        allUsers.addAll(group.getAdmins());

        if (allUsers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No members or admins to remove.");
            return;
        }

        JComboBox<User> userBox = new JComboBox<>();
        for (User user: allUsers)
            userBox.addItem(user);

        JPanel removeMemberPanel = new JPanel(new GridLayout(1, 2));
        removeMemberPanel.add(new JLabel("Remove Member"));
        removeMemberPanel.add(userBox);

        int result = JOptionPane.showConfirmDialog(this, removeMemberPanel, "Remove Member", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION){
            User removedUser = (User) userBox.getSelectedItem();
            if(removedUser!=null){
                group.getPrimaryAdmin().removeMember(removedUser);
                JOptionPane.showMessageDialog(this, "Member "+removedUser.getUsername()+" removed successfully.", "Removed Member", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void makeAdminsActionListener() {
        ArrayList<User> members = new ArrayList<>(group.getMembers());
        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No members to promote.");
            return;
        }

        JComboBox<User> userBox = new JComboBox<>();
        for (User user: members)
            userBox.addItem(user);

        JPanel makeAdminsPanel = new JPanel(new GridLayout(1, 2));
        makeAdminsPanel.add(new JLabel("Promote Member"));
        makeAdminsPanel.add(userBox);
        int result = JOptionPane.showConfirmDialog(this,makeAdminsPanel, "Promote Member", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION){
            User promoted = (User) userBox.getSelectedItem();
            if(promoted!=null){
                group.getPrimaryAdmin().makeAdmin(promoted);
                JOptionPane.showMessageDialog(this, "Member "+promoted.getUsername()+" promoted successfully.", "Removed Member", JOptionPane.INFORMATION_MESSAGE);
            }

        }


    }

    private JPanel adminButtonBuilder() {
        JPanel adminButtonsPanel = new JPanel(new GridLayout(1, 5));
        JButton membershipRequests = new JButton("Review Membership requests");
        membershipRequests.addActionListener(_ -> membershipRequestsActionListeners());
        JButton removeMember = new JButton("Remove Member");
        removeMember.addActionListener(_ -> removeMemberActionListener());
        JButton managePosts = new JButton("Manage Posts");
        managePosts.addActionListener(_ -> managePostsActionListeners());
        JButton post = new JButton("Post");
        post.addActionListener(_ -> postButtonActionListeners());
        JButton leaveGroup = new JButton("Leave Group");
        leaveGroup.addActionListener(_ -> leaveGroupButtonActionListeners());
        adminButtonsPanel.add(membershipRequests);
        adminButtonsPanel.add(removeMember);
        adminButtonsPanel.add(managePosts);
        adminButtonsPanel.add(post);
        adminButtonsPanel.add(leaveGroup);
        return adminButtonsPanel;
    }

    private void postButtonActionListeners() {
        ContentFields contentFields = new ContentFields(null,null);
        GetContentFieldsWindow getContentFieldsWindow = new GetContentFieldsWindow(contentFields,false);
        Post post = new Post(currentUser.getUser().getUserId(),contentFields);
        group.getGroupContent().addPost(post);
    }

    private void managePostsActionListeners() {
        ArrayList<Post> posts = group.getGroupContent().getPosts();
        JComboBox<Post> postsBox = new JComboBox<>();
        for (Post post: posts)
            postsBox.addItem(post);

        if (posts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No posts to edit.");

        }
        JPanel managePosts = new JPanel(new GridLayout());
        managePosts.add(new JLabel("Manage Posts"));
        managePosts.add(postsBox);
        JButton deleteBtn = new JButton("Delete");
        managePosts.add(deleteBtn);
        deleteBtn.addActionListener(e -> {
            Post selectedPost = (Post) postsBox.getSelectedItem();
            if(selectedPost!=null){
                try {
                    int result = JOptionPane.showConfirmDialog(this,new PostCard(selectedPost));

                    if (result == JOptionPane.OK_OPTION){
                        group.getGroupContent().removePost(selectedPost);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JButton editBtn = new JButton("Edit");
        managePosts.add(editBtn);
        editBtn.addActionListener(e -> {
            Post selectedPost = (Post) postsBox.getSelectedItem();
            if(selectedPost!=null){
                try {
                    int result = JOptionPane.showConfirmDialog(this,new PostCard(selectedPost));

                    if (result == JOptionPane.OK_OPTION){
                        ContentFields contentFields = selectedPost.getContentFields();
                        GetContentFieldsWindow getContentFieldsWindow = new GetContentFieldsWindow(contentFields,false);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



    }

    private void removeMemberActionListener() {
        ArrayList<User> members = group.getMembers();

        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members or admins to remove.");
            return;
        }

        JComboBox<User> userBox = new JComboBox<>();
        for (User user: members)
            userBox.addItem(user);

        JPanel removeMemberPanel = new JPanel(new GridLayout(1, 2));
        removeMemberPanel.add(new JLabel("Remove Member"));
        removeMemberPanel.add(userBox);

        int result = JOptionPane.showConfirmDialog(this, removeMemberPanel, "Remove Member", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION){
            User removedUser = (User) userBox.getSelectedItem();
            group.getPrimaryAdmin().removeMember(removedUser);
            JOptionPane.showMessageDialog(this, "Member "+removedUser.getUsername()+" removed successfully.", "Removed Member", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void membershipRequestsActionListeners() {
        ArrayList<MembershipRequest> requests = group.getMembershipManager().getGroupRequests(group);
        if (requests.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No requests to review.");
            return;
        }

        JComboBox<MembershipRequest> requestsBox = new JComboBox<>();
        for (MembershipRequest request: requests)
            requestsBox.addItem(request);

        JPanel membershipRequestPanel = new JPanel(new GridLayout(2, 2));
        membershipRequestPanel.add(new JLabel("Review Request"));
        membershipRequestPanel.add(requestsBox);
        JButton acceptButton = new JButton("Accept request");
        JButton declineButton = new JButton("Decline request");
        membershipRequestPanel.add(acceptButton);
        membershipRequestPanel.add(declineButton);

        int result = JOptionPane.showConfirmDialog(null, membershipRequestPanel, "Membership Requests", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            MembershipRequest selectedRequest = (MembershipRequest) requestsBox.getSelectedItem();

            if (selectedRequest != null) {
                acceptButton.addActionListener(_ -> acceptRequestActionListener(selectedRequest));
                declineButton.addActionListener(_ -> declineRequestActionListener(selectedRequest));
            }
        }


    }

    private void declineRequestActionListener(MembershipRequest request) {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to decline this request?", "Decline Request", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            request.setStatus(MembershipRequestStatus.Declined);
            group.getMembershipManager().getMembershipRequests().remove(request);
            JOptionPane.showMessageDialog(null, "Request declined.");
        }
    }

    private void acceptRequestActionListener(MembershipRequest request) {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to accept this request?", "Accept Request", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            group.addMember(request.getSender());
            request.setStatus(MembershipRequestStatus.Approved);
            group.getMembershipManager().getMembershipRequests().remove(request);
            JOptionPane.showMessageDialog(null, "Request accepted.");
        }
    }

    private JPanel memberButtonBuilder() {
        JPanel memberButtonsPanel = new JPanel(new GridLayout(1, 2));
        JButton leaveGroup = new JButton("Leave group");
        leaveGroup.addActionListener(_ -> leaveGroupButtonActionListeners());
        JButton post = new JButton("Post");
        post.addActionListener(_ -> postButtonActionListeners());

        memberButtonsPanel.add(leaveGroup);
        memberButtonsPanel.add(post);
        return memberButtonsPanel;
    }

    private void leaveGroupButtonActionListeners() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave the group?", "Leave Group", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            group.removeMember(currentUser);
            JOptionPane.showMessageDialog(null, "You have left the group.");
        }
    }


    private JPanel requestSentButtonBuilder() {
        JPanel requestSentButtonsPanel = new JPanel(new GridLayout(1, 1));
        JButton cancelRequest = new JButton("Cancel request");
        cancelRequest.addActionListener(_ -> cancelRequestButtonActionListeners());

        requestSentButtonsPanel.add(cancelRequest);
        return requestSentButtonsPanel;
    }

    private void cancelRequestButtonActionListeners() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel your join request?", "Cancel Request", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            group.getMembershipManager().cancelRequest(group.getMembershipManager().getRequest(group, currentUser.getUser()), currentUser);
            JOptionPane.showMessageDialog(null, "Request canceled.");
        }
    }

    private JPanel notMemberButtonBuilder() {
        JPanel notMemberButtonsPanel = new JPanel(new GridLayout(1, 1));
        JButton joinGroup = new JButton("Join Group");
        joinGroup.addActionListener(_ -> joinGroupButtonActionListeners());

        notMemberButtonsPanel.add(joinGroup);
        return notMemberButtonsPanel;
    }

    private void joinGroupButtonActionListeners() {
        if (!group.isMember(currentUser.getUser()) && !group.isAdmin(currentUser.getUser())) {
            group.getMembershipManager().sendMembershipRequest(group, currentUser.getUser());
            JOptionPane.showMessageDialog(null, "Join request sent.");
        } else {
            JOptionPane.showMessageDialog(null, "You are already a member or admin of this group.");
        }
    }

    public JPanel buildPostsPanel() throws IOException {
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));
        ArrayList<Post> posts = group.getGroupContent().getPosts();
        UserGroupStatus status = getStatus();
        if (status == UserGroupStatus.NotMember || status == UserGroupStatus.RequestSent) {
            postsPanel.add(Box.createRigidArea(new Dimension(0, 200)));
            JLabel no_posts_label = new JLabel("Content is limited to group members.");
            no_posts_label.setFont(new Font("Arial", Font.BOLD, 48));
            no_posts_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            postsPanel.add(no_posts_label);
        } else if (posts.isEmpty()) {
            postsPanel.add(Box.createRigidArea(new Dimension(0, 200)));
            JLabel no_posts_label = new JLabel("No posts available yet");
            no_posts_label.setFont(new Font("Arial", Font.BOLD, 48));
            no_posts_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            postsPanel.add(no_posts_label);
        } else {
            posts.sort(Comparator.comparing(Post::getTimestamp).reversed());
            for (Post post : posts) {
                PostCard postCard = new PostCard(post);
                postsPanel.add(postCard);
                postsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            }
        }
        return postsPanel;
    }

    public JPanel build() throws IOException {
        mainPanel.removeAll();
        mainPanel.add(buildInfoPanel(), BorderLayout.NORTH);
        mainPanel.add(buildButtonPanel(), BorderLayout.CENTER);
        mainPanel.add(buildPostsPanel(), BorderLayout.SOUTH);
        return mainPanel;
    }
}