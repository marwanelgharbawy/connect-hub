package utils;

import backend.Profile;
import backend.User;

import javax.swing.*;
import java.awt.*;
import Group.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UIUtils {
    public static void initializeWindow(JFrame window, JPanel panel, String title, int width, int height) {
        window.setContentPane(panel);
        window.setTitle(title);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public static JButton createIconButton(String icon_path, String tool_tip){
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

    public static JButton createSideButton(String text) {
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
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        return button;
    }
    public static JButton createNotifButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(getModel().isPressed() ? new Color(100, 100, 100) : new Color(150, 150, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(80, 25));
        button.setMaximumSize(new Dimension(80, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false); // Transparent content area for custom painting

        return button;
    }

    public static JButton createGroupCardButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(getModel().isPressed() ? new Color(100, 100, 100) : new Color(150, 150, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
                g2d.dispose();
                super.paintComponent(g);
            }
        };

        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(100, 25));
        button.setMaximumSize(new Dimension(100, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false); // Transparent content area for custom painting

        return button;
    }

    public static Color HEX2Color(String hex){
        if(hex.length() > 6) throw new IllegalArgumentException("Wrong Color format : " + hex);

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new Color(r, g, b);
    }
    public static JButton createUserButton(User user) {
        // Create a button with text
        JButton button = new JButton(user.getUsername()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Button color when pressed
                g2d.setColor(getModel().isPressed() ? new Color(56, 151, 139) : new Color(70, 179, 165));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        // Add profile image path
        ImageIcon profilePicIcon = new ImageIcon(user.getProfile().getProfile_img_path());
        Image profilePic = profilePicIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon profilePicScaled = new ImageIcon(profilePic);
        button.setIcon(profilePicScaled);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(10);  // Add gap between icon and text
        button.setPreferredSize(new Dimension(140, 35));
        // Set font and button content
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);

        return button;
    }
    /*
    public static JButton createGroupButton(Group group) {
        // Create a button with text
        JButton button = new JButton(group.getName()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Button color when pressed
                g2d.setColor(getModel().isPressed() ? new Color(56, 151, 139) : new Color(70, 179, 165));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        // Add profile image path
        ImageIcon profilePicIcon = group.getGroupPhoto().toIcon();
        Image profilePic = profilePicIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon profilePicScaled = new ImageIcon(profilePic);
        button.setIcon(profilePicScaled);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(10);  // Add gap between icon and text
        button.setPreferredSize(new Dimension(140, 35));
        // Set font and button content
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);

        return button;
    }
    */



}
