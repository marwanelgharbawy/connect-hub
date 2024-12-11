package frontend.newsFeed;

import backend.CurrentUser;
import backend.Database;
import backend.User;
import frontend.MainMenu;
import frontend.UserProfile;
import frontend.contentCreation.ContentCreation;
import frontend.friendManager.FriendManagerWindow;
import frontend.searchManager.SearchWindow;
import utils.UIUtils;
import utils.Utilities;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static utils.UIUtils.createSideButton;

public class NewsFeed extends JFrame {
    JPanel mainPanel;
    JPanel sidePanel;
    JPanel contentPanel;
    JPanel onlinePanel;
    CurrentUser current_user;
    MainMenu parent;

    public NewsFeed(MainMenu parent) throws IOException {
        this.parent = parent;
        this.current_user = Database.getInstance().getCurrentUser();
        initUI();
        populateOnlinePanel();
        setVisible(true);
    }

    private void initUI() throws IOException {
        setSize(1400, 700);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(10, 1, 4, 8));
        sidePanel.setBackground(UIUtils.HEX2Color("1a4586"));
        sidePanel.setPreferredSize(new Dimension(200, 0));

        sidePanel.setBorder(BorderFactory.createEmptyBorder(80, 20, 20, 20));

        JButton home_btn = createSideButton("Home");
        JButton stories_btn = createSideButton("Stories");
//        JButton friends_btn = UIUtils.createSideButton("My Friends");
//        JButton suggestions_btn = UIUtils.createSideButton("Suggestions");
        JButton groups_btn = createSideButton("Groups");
        JButton my_profile_btn = createSideButton("My Profile");
//        JButton blocked_users_btn = UIUtils.createSideButton("Blocked Users");
//        JButton friend_requests_btn = UIUtils.createSideButton("Friend requests");

        sidePanel.add(home_btn);
        sidePanel.add(stories_btn);
//        sidePanel.add(friends_btn);
//        sidePanel.add(suggestions_btn);
        sidePanel.add(groups_btn);
        sidePanel.add(my_profile_btn);
//        sidePanel.add(blocked_users_btn);
//        sidePanel.add(friend_requests_btn);


        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout());

        JPanel top_panel = new JPanel(); top_panel.setLayout(new BorderLayout());
        top_panel.setPreferredSize(new Dimension(0, 61));
        top_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top_panel.setBackground(Color.WHITE);

        JButton share_content_btn = UIUtils.createIconButton("icons/plus.png", "Share");
        share_content_btn.addActionListener(share_content_btn_evt());
        JButton refresh_btn = UIUtils.createIconButton("icons/refresh.png", "Refresh");
        refresh_btn.addActionListener(refresh_btn_evt());
        JButton log_out_btn = UIUtils.createIconButton("icons/logout.png", "Log Out");
        log_out_btn.addActionListener(log_out_btn_evt());
        JButton friend_manager_btn = UIUtils.createIconButton("icons/friends.png", "Friend Manager");
        friend_manager_btn.addActionListener(friend_manager_btn_evt());
        JButton notification_btn =UIUtils.createIconButton("icons/notification.png", "notifications");
        JButton search_btn = UIUtils.createIconButton("icons/search.png", "Search");
        search_btn.addActionListener(search_btn_evt());


        JPanel top_btn_panel = new JPanel();
        top_btn_panel.setLayout(new GridLayout(1, 4, 4, 4));
        top_btn_panel.setBackground(Color.white);

        top_btn_panel.add(share_content_btn);
        top_btn_panel.add(notification_btn);
        top_btn_panel.add(friend_manager_btn);
        top_btn_panel.add(refresh_btn);
        top_btn_panel.add(log_out_btn);
        top_btn_panel.add(search_btn);

        top_panel.add(top_btn_panel, BorderLayout.EAST);

        contentPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        refreshPages();
        home_btn.addActionListener(e-> cardLayout.show(contentPanel, home_btn.getText()));
        stories_btn.addActionListener(e-> cardLayout.show(contentPanel, stories_btn.getText()));
        my_profile_btn.addActionListener(e-> cardLayout.show(contentPanel, my_profile_btn.getText()));
        log_out_btn.addActionListener(log_out_btn_evt());
        this.addWindowListener(close_evt());

        rightPanel.add(top_panel, BorderLayout.NORTH);
        rightPanel.add(contentPanel, BorderLayout.CENTER);


        onlinePanel = new JPanel();
        onlinePanel.setLayout(new GridLayout(10, 1, 4, 8));
        onlinePanel.setBackground(UIUtils.HEX2Color("1a4586"));
        onlinePanel.setPreferredSize(new Dimension(200, 0));
        onlinePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        mainPanel.add(onlinePanel, BorderLayout.EAST);

    }

    private void refreshPages() throws IOException {
        contentPanel.removeAll();
        /*Home*/
        PostsNewsFeed postsNewsFeed = new PostsNewsFeed();
        contentPanel.add(postsNewsFeed, "Home");

        /*Stories*/
        StoryNewsFeed storyNewsFeed = new StoryNewsFeed();
        contentPanel.add(storyNewsFeed, "Stories");

        /*My Profile*/
        UserProfile current_user_profile = new UserProfile(current_user.getUser());
        contentPanel.add(current_user_profile, "My Profile");
    }

    private ActionListener refresh_btn_evt(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    refreshPages();
                    populateOnlinePanel();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    ActionListener share_content_btn_evt(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ContentCreation(current_user.getUser().getUserId());
            }
        };
    }

    ActionListener log_out_btn_evt(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = current_user.getUser();
                user.setOnline(false);

                dispose();
                parent.setVisible(true);
            }
        };
    }
    ActionListener search_btn_evt(){
        return new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                new SearchWindow(current_user.getUser());
            }
        };
    }

    ActionListener friend_manager_btn_evt(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FriendManagerWindow friendManagerWindow = new FriendManagerWindow(current_user.getUser());

            }
        };
    }

    WindowAdapter close_evt(){
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                User user = current_user.getUser();
                user.setOnline(false);

                dispose();
                parent.setVisible(true);
            }
        };
    }

    private void populateOnlinePanel(){
        onlinePanel.removeAll();
        JLabel label = new JLabel("Online Friends");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        onlinePanel.add(label);
        for(User friend: current_user.getUser().getFriendManager().getFriends()){
            if(friend.isOnline()){
                onlinePanel.add(createOnlineUserButton(friend));
            }
        }
    }
    private JButton createOnlineUserButton(User user){
        JButton button = UIUtils.createUserButton(user);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame user_profile = new JFrame();
                try {
                    UserProfile user_profile_content = new UserProfile(current_user.getUser(), user);
                    user_profile.setContentPane(user_profile_content);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                user_profile.pack();
                user_profile.setVisible(true);
            }
        });

        return button;
    }

    public static void main(String[] args) throws IOException {
        new NewsFeed(null);
    }
}
