package frontend.newsFeed;

import backend.User;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;

public class NewsFeed extends JFrame {
    JPanel mainPanel;
    JPanel sidePanel;
    JPanel contentPanel;
    User current_user;

    public NewsFeed(User current_user){
        this.current_user = current_user;
        initUI();
        setVisible(true);
    }

    private void initUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(10, 1, 4, 8));
        sidePanel.setBackground(Utilities.HEX2Color("1a4586"));
        sidePanel.setPreferredSize(new Dimension(200, 0));

        sidePanel.setBorder(BorderFactory.createEmptyBorder(80, 20, 20, 20));

        JButton home_btn = createSideButton("Home");
        JButton stories_btn = createSideButton("Stories");
        JButton friends_btn = createSideButton("My Friends");
        JButton suggestions_btn = createSideButton("Suggestions");
        JButton my_profile_btn = createSideButton("My Profile");
        JButton blocked_users_btn = createSideButton("Blocked Users");
        JButton friend_requests_btn = createSideButton("Friend requests");

        sidePanel.add(home_btn);
        sidePanel.add(stories_btn);
        sidePanel.add(friends_btn);
        sidePanel.add(suggestions_btn);
        sidePanel.add(my_profile_btn);
        sidePanel.add(blocked_users_btn);
        sidePanel.add(friend_requests_btn);


        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout());

        JPanel top_panel = new JPanel(); top_panel.setLayout(new BorderLayout());
        top_panel.setPreferredSize(new Dimension(0, 61));
        top_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top_panel.setBackground(Color.WHITE);

        JButton share_content_btn = createIconButton("icons/plus.png", "Share");
        JButton refresh_btn = createIconButton("icons/refresh.png", "Refresh");
        JButton log_out_btn = createIconButton("icons/logout.png", "Log Out");

        JPanel top_btn_panel = new JPanel();
        top_btn_panel.setLayout(new GridLayout(1, 2, 4, 4));
        top_btn_panel.setBackground(Color.white);

        top_btn_panel.add(share_content_btn);
        top_btn_panel.add(refresh_btn);
        top_btn_panel.add(log_out_btn);

        top_panel.add(top_btn_panel, BorderLayout.EAST);

        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        initPages();

        rightPanel.add(top_panel, BorderLayout.NORTH);
        rightPanel.add(contentPanel, BorderLayout.CENTER);



        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

    }

    private void initPages(){

    }

    private JButton createIconButton(String icon_path, String tool_tip){
        JButton button = new JButton(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getModel().isPressed() ? new Color(56, 151, 139) : new Color(70, 179, 165));
                g2d.fillOval(0, 0, getWidth(), getHeight());

                g2d.dispose();
                super.paintComponent(g);
            }
        };

        ImageIcon originalIcon = new ImageIcon(icon_path);
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);

        button.setToolTipText(tool_tip);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false); // Prevent default button background
        button.setPreferredSize(new Dimension(40, 40)); // Set button size
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createSideButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(getModel().isPressed() ? new Color(56, 151, 139) : new Color(70, 179, 165));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2d.dispose();
                super.paintComponent(g);
            }


        };

        button.setForeground(Color.WHITE); // Set text color
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set font style and size
        button.setPreferredSize(new Dimension(120, 40)); // Set button size
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false); // Prevent default button background

        return button;
    }

    public static void main(String[] args) {
        new NewsFeed(null);
    }
}