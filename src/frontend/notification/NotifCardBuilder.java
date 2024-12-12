package frontend.notification;

import notificationManager.Notification;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class NotifCardBuilder {
    public static JPanel getNotificationCard(Notification notif){
        JPanel notifPanel = new JPanel();
        notifPanel.setPreferredSize(new Dimension(600, 60));
        notifPanel.setMaximumSize(new Dimension(600, 60));
        notifPanel.setBackground(UIUtils.HEX2Color("b8b8b8"));
        notifPanel.setLayout(new BorderLayout(10, 10));
        notifPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon icon = notif.getNotifImage();
        JLabel photoLabel = new JLabel(icon);

        JLabel usernameLabel = new JLabel(notif.getNotifMessage());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));


        ArrayList<JButton> btns = notif.getNotifBtns();
        JPanel btnPanel = new JPanel(new GridLayout(1, btns.size(), 5, 0));
        btnPanel.setBackground(UIUtils.HEX2Color("b8b8b8"));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        for (JButton btn: btns) {
//            btn.setPreferredSize(new Dimension(100, 20));
//            btn.setMaximumSize(new Dimension(100, 20));
//            btn.setFont(new Font("Arial", Font.PLAIN, 12));
            btnPanel.add(btn);
        }

        notifPanel.add(photoLabel, BorderLayout.WEST);
        notifPanel.add(usernameLabel, BorderLayout.CENTER);
        notifPanel.add(btnPanel, BorderLayout.EAST);
        return notifPanel;
    }
}
