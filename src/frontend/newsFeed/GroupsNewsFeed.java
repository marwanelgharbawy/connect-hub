package frontend.newsFeed;

import Group.Group;
import backend.Database;
import content.Post;
import frontend.CreateGroup;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class GroupsNewsFeed extends JPanel {
    private JPanel contentPanel;
    public GroupsNewsFeed() throws IOException {
        initUI();
    }

    private void initUI() throws IOException {
        this.setLayout(new BorderLayout());

        //////////////// TOP PANEL  ////////////////
        JPanel top_panel = new JPanel(); top_panel.setLayout(new BorderLayout());
        top_panel.setPreferredSize(new Dimension(0, 61));
        top_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        top_panel.setBackground(Color.WHITE);

        JPanel top_btn_panel = new JPanel(); top_btn_panel.setBackground(Color.white);
        top_btn_panel.setLayout(new GridLayout(1, 3, 4, 4));
        JButton new_group_btn = UIUtils.createGroupCardButton("New Group");
        new_group_btn.addActionListener(new_group_btn_evt());
        top_btn_panel.add(new_group_btn);
        JButton my_groups_btn = UIUtils.createGroupCardButton("My Group");
        my_groups_btn.addActionListener(my_groups_btn_evt());
        top_btn_panel.add(my_groups_btn);
        JButton suggestion_btn = UIUtils.createGroupCardButton("Suggestion");
        suggestion_btn.addActionListener(suggestion_btn_evt());
        top_btn_panel.add(suggestion_btn);

        top_panel.add(top_btn_panel, BorderLayout.WEST);

        //////////////// CONTENT PANEL ////////////////
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.white);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        populateMyGroups(true);


        this.add(top_panel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane);
    }

    private void populateMyGroups(boolean isMember) throws IOException {
        contentPanel.removeAll();
        Group[] groups = Database.getInstance().getGroups();
        boolean empty = true;
        for(Group group: groups){
            if(group.isInGroup(Database.getInstance().getCurrentUser().getUser()) == isMember){
                GroupCard groupCard = new GroupCard(group, isMember);
                contentPanel.add(groupCard);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                empty = false;
            }
        }
        if(empty){
            JLabel no_groups_label = new JLabel("No Groups available yet");
            no_groups_label.setForeground(Color.gray);
            no_groups_label.setFont(new Font("Arial", Font.BOLD, 48));
            no_groups_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(no_groups_label);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private ActionListener new_group_btn_evt(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CreateGroup(Database.getInstance().getCurrentUser().getUser());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    private ActionListener my_groups_btn_evt(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populateMyGroups(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    private ActionListener suggestion_btn_evt(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populateMyGroups(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
