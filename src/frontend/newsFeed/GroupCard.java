package frontend.newsFeed;

import Group.Group;
import backend.Database;
import backend.User;
import frontend.Group.GroupPage;
import frontend.UserProfile;
import utils.Picture;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class GroupCard extends JPanel {
    private final Group group;
    private boolean isMember;

    public GroupCard(Group group, boolean isMember) throws IOException {
        this.group = group;
        this.isMember = isMember;
        initUI();
    }

    private void initUI() throws IOException {
        this.setPreferredSize(new Dimension(600, 60));
        this.setMaximumSize(new Dimension(600, 60));
        this.setBackground(UIUtils.HEX2Color("b8b8b8"));
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon originalIcon = group.getGroupPhoto().toIcon();
//        ImageIcon originalIcon = new Picture("C:/Users/Omar Hekal/Desktop/profile.jpg").toIcon();
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel group_photo = new JLabel(new ImageIcon(scaledImage));

        JLabel group_name = new JLabel(group.getName());
//        JLabel group_name = new JLabel("Group name");
        group_name.setFont(new Font("Arial", Font.BOLD, 16));

        ArrayList<JButton> btns = getButtons();
        JPanel btnPanel = new JPanel(new GridLayout(1, btns.size(), 5, 0));
        btnPanel.setBackground(UIUtils.HEX2Color("b8b8b8"));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        for (JButton btn: btns) {
            btnPanel.add(btn);
        }

        this.add(group_photo, BorderLayout.WEST);
        this.add(group_name, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.EAST);
    }

    private ArrayList<JButton> getButtons() throws IOException {
        User current_user = Database.getInstance().getCurrentUser().getUser();
        ArrayList<JButton> btns = new ArrayList<>();
        if(!isMember){
            JButton join_group_btn = UIUtils.createGroupCardButton("Join Group");
            join_group_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    User current_user = Database.getInstance().getCurrentUser().getUser();
                    group.getMembershipManager().sendMembershipRequest(current_user);
                    join_group_btn.setVisible(false);
                }
            });
            btns.add(join_group_btn);
        }else {
            JButton view_group_btn = UIUtils.createGroupCardButton("View group");
            view_group_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame group_frame = new JFrame();
                    try {
                        GroupPage groupPage = new GroupPage(group, Database.getInstance().getCurrentUser());
                        group_frame.setContentPane(groupPage);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    group_frame.pack();
                    group_frame.setVisible(true);
                }
            });
            btns.add(view_group_btn);
        }
        return btns;
    }
}
