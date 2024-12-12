package frontend.notification;

import backend.Database;
import notificationManager.Notification;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class NotificationsWindow extends JFrame {
    public NotificationsWindow() throws IOException {
        initUI();
        setSize(800, 600);
        setVisible(true);
    }

    private void initUI() throws IOException {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.white);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ArrayList<Notification> notifs = Database.getInstance().getCurrentUser().getNotifications();
        if (notifs.isEmpty()){
            JLabel no_notifs_label = new JLabel("No Notifications available yet");
            no_notifs_label.setFont(new Font("Arial", Font.BOLD, 48));
            no_notifs_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(no_notifs_label);
        }
        else {
            for (Notification notif : notifs) {
                JPanel notifPanel = NotifCardBuilder.getNotificationCard(notif);
                contentPanel.add(notifPanel);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        setContentPane(contentPanel);
    }
}
