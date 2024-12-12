package frontend;

import backend.User;
import content.Post;
import friendManager.FriendRequest;
import frontend.newsFeed.PostCard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class UserProfileBuilder {
    private final User currentUser;
    private final User desiredUser;
    private final JPanel mainPanel;

    public UserProfileBuilder(User currentUser, User desiredUser) {
        this.currentUser = currentUser;
        this.desiredUser = desiredUser;
        this.mainPanel = new JPanel(new BorderLayout());
    }

    public JPanel buildCoverPhoto() {
        JPanel coverPhotoPanel = new JPanel(null);
        coverPhotoPanel.setPreferredSize(new Dimension(1000, 350));
        JLabel coverPhotoLabel = new JLabel();
        coverPhotoLabel.setBounds(0, 0, 1000, 270);
        coverPhotoLabel.setIcon(new ImageIcon(desiredUser.getProfile().getCoverPhoto().toIcon().getImage().getScaledInstance(1000, 300, Image.SCALE_DEFAULT)));

        JPanel profilePhotoPanel = new JPanel(null);
        profilePhotoPanel.setBounds(300, 130, 180, 220);
        profilePhotoPanel.setOpaque(false);

        JLabel profilePhotoLabel = new JLabel();
        profilePhotoLabel.setIcon(new ImageIcon(desiredUser.getProfile().getProfilePhoto().getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
        profilePhotoLabel.setBounds(0, 0, 180, 180);
        profilePhotoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        JLabel usernameLabel = new JLabel(desiredUser.getUsername(), SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setBounds(0, 180, 180, 20);

        profilePhotoPanel.add(profilePhotoLabel);
        profilePhotoPanel.add(usernameLabel);

        coverPhotoPanel.add(profilePhotoPanel);
        coverPhotoPanel.add(coverPhotoLabel);

        return coverPhotoPanel;
    }

    public JPanel buildBioPanel() {
        JPanel bioPanel = new JPanel(new BorderLayout());
        bioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bioLabel = new JLabel(desiredUser.getProfile().getBio());
        bioLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bioLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel buttons = new JPanel();
        setButtons(currentUser,desiredUser,buttons);

        bioPanel.add(bioLabel, BorderLayout.CENTER);
        bioPanel.add(buttons, BorderLayout.EAST);

        return bioPanel;
    }
    private void setButtons(User currentUser, User desiredUser, JPanel buttons){
        buttons.removeAll();
        JButton unfriendButton = new JButton("Unfriend");
        unfriendButton.addActionListener(e -> {
            try {
                currentUser.getFriendManager().confirmRemove(currentUser, desiredUser);
                setButtons(currentUser,desiredUser,buttons);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton blockButton = new JButton("Block");
        blockButton.addActionListener(e -> {
            try {
                currentUser.getFriendManager().getBlockManager().blockUser(currentUser, desiredUser);
                setButtons(currentUser,desiredUser,buttons);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton unblockButton = new JButton("Unblock");
        unblockButton.addActionListener(e -> {
            try {
                currentUser.getFriendManager().getBlockManager().unblockUser(currentUser, desiredUser);
                setButtons(currentUser,desiredUser,buttons);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton cancelRequest = new JButton("Cancel Request");
        cancelRequest.addActionListener(e -> {
            try {
                FriendRequest sentFriendRequest = currentUser.getFriendManager().getRequestManager().getSentRequest(desiredUser.getUserId());
                FriendRequest receivedFriendRequest = currentUser.getFriendManager().getRequestManager().getReceivedRequest(desiredUser.getUserId());
                if(sentFriendRequest!=null){
                    currentUser.getFriendManager().getRequestManager().cancelRequest(sentFriendRequest);
                }
                if(receivedFriendRequest!=null){
                    currentUser.getFriendManager().getRequestManager().cancelRequest(receivedFriendRequest);
                }

                setButtons(currentUser,desiredUser,buttons);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton acceptRequest = new JButton("Accept");
        acceptRequest.addActionListener(e -> {
            try {
                FriendRequest friendRequest = currentUser.getFriendManager().getRequestManager().getReceivedRequest(desiredUser.getUserId());
                currentUser.getFriendManager().getRequestManager().acceptFriendRequest(friendRequest);
                setButtons(currentUser,desiredUser,buttons);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton addFriendButton = new JButton("Add friend");
        addFriendButton.addActionListener(e -> {
            try {
                currentUser.getFriendManager().getRequestManager().sendFriendRequest(currentUser, desiredUser);
                setButtons(currentUser,desiredUser,buttons);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        if (currentUser.getFriendManager().getFriends().contains(desiredUser)) {
            buttons.add(unfriendButton, BorderLayout.NORTH);
            buttons.add(blockButton, BorderLayout.SOUTH);
        } else if (currentUser.getFriendManager().getBlockManager().getBlockedUsers().contains(desiredUser)) {
            buttons.add(unblockButton, BorderLayout.NORTH);
        } else if (currentUser.isRequestSent(desiredUser)) {
            JLabel pendingLabel = new JLabel("Pending Friend Request");
            buttons.add(pendingLabel, BorderLayout.NORTH);
            buttons.add(cancelRequest, BorderLayout.SOUTH);
        } else if (currentUser.isRequestReceived(desiredUser)) {
            JLabel receivedLabel = new JLabel("Received Friend Request");
            cancelRequest.setText("Decline");
            buttons.add(receivedLabel);
            buttons.add(acceptRequest,BorderLayout.NORTH);
            buttons.add(cancelRequest,BorderLayout.SOUTH);
            buttons.add(blockButton);
        } else {
            buttons.add(addFriendButton, BorderLayout.NORTH);
            buttons.add(blockButton, BorderLayout.SOUTH);
        }
        buttons.revalidate();
        buttons.repaint();

    }
    

    public JPanel buildPostsPanel() throws IOException {
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));

//        for (int i = 1; i <= 10; i++) { // Add 10 sample posts for testing
//            ContentFields contentFields = new ContentFields("Sample post content", "C:/path/to/image.jpg");
//            Post post = new Post("Post title " + i, contentFields);
//
//            PostCard postCard = new PostCard(post);
//            postsPanel.add(postCard);
//            postsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
//        }
        ArrayList<Post> posts = desiredUser.getContentManager().getPosts();
        if (posts.isEmpty()) {
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
        mainPanel.add(buildCoverPhoto(), BorderLayout.NORTH);
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(buildBioPanel(), BorderLayout.NORTH);
        mainContentPanel.add(buildPostsPanel(), BorderLayout.CENTER);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);

        return mainPanel;
    }
}
