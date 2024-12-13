package frontend.Group;

import Group.Group;
import Group.MembershipManager.UserGroupStatus;
import backend.CurrentUser;

import javax.swing.*;
import java.awt.*;

public class GroupPageBuilder {
    Group group;
    CurrentUser user;
    UserGroupStatus status;
    JPanel mainPanel;

    public GroupPageBuilder(Group group, CurrentUser user){
        this.group = group;
        this.user = user;
        this.status = getStatus();
        this.mainPanel = new JPanel();
    }

    private UserGroupStatus getStatus() {
        if (group.isPrimaryAdmin(user.getUser()))
            return UserGroupStatus.PrimaryAdmin;
        if (group.isAdmin(user.getUser()))
            return UserGroupStatus.Admin;
        if (group.isMember(user.getUser()))
            return UserGroupStatus.Member;
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
            case UserGroupStatus.PrimaryAdmin -> buttonPanel = primaryAdminButtonBuilder();
        }
        return buttonPanel;
    }

    private JPanel primaryAdminButtonBuilder() {
        JPanel primaryAdminButtonsPanel = new JPanel();




        return primaryAdminButtonsPanel;
    }

    private JPanel adminButtonBuilder() {
        JPanel adminButtonsPanel = new JPanel();
        JButton membershipRequests = new JButton("Review Membership requests");
        membershipRequests.addActionListener(_ -> membershipRequestsActionListeners());
        JButton removeMember = new JButton("Remove Member");
        JButton managePosts = new JButton("Manage Posts");
        JButton post = new JButton("Post");
        post.addActionListener(_ -> postButtonActionListeners());
        JButton leaveGroup = new JButton("Leave Group");

        return adminButtonsPanel;
    }

    private void membershipRequestsActionListeners() {
    }

    private JPanel memberButtonBuilder() {
        JPanel memberButtonsPanel = new JPanel();
        JButton leaveGroup = new JButton("Leave group");
        leaveGroup.addActionListener(_ -> leaveGroupButtonActionListeners());
        JButton post = new JButton("Post");
        post.addActionListener(_ -> postButtonActionListeners());

        return memberButtonsPanel;
    }

    private void postButtonActionListeners() {
    }

    private void leaveGroupButtonActionListeners() {
    }

    private JPanel requestSentButtonBuilder() {
        JPanel requestSentButtonsPanel = new JPanel();
        JButton cancelRequest = new JButton("Cancel request");
        cancelRequest.addActionListener(_ -> cancelRequestButtonActionListeners());

        return requestSentButtonsPanel;
    }

    private void cancelRequestButtonActionListeners() {
    }

    private JPanel notMemberButtonBuilder() {
        JPanel notMemberButtonsPanel = new JPanel();
        JButton joinGroup = new JButton("Join Group");
        joinGroup.addActionListener(_ -> joinGroupButtonActionListeners());

        return notMemberButtonsPanel;
    }

    private void joinGroupButtonActionListeners() {
    }
}
