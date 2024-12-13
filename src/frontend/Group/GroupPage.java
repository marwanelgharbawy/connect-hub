package frontend.Group;

import Group.Group;
import backend.CurrentUser;
import backend.User;
import content.ContentFields;
import content.Post;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

public class GroupPage extends JPanel {

    public GroupPage(Group group, CurrentUser user) throws IOException {
        setSize(700, 800);
        setLayout(new BorderLayout());

        GroupPageBuilder builder = new GroupPageBuilder(group, user);
        JPanel mainPanel = builder.build();

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        // Create a simple JFrame to host the GroupPage
        JFrame frame = new JFrame("Group Page Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);

        CurrentUser mockUser = new CurrentUser(new User("TestUser", "test@example.com", "12345", LocalDate.now()));

        // Create mock objects for Group and CurrentUser
        Group mockGroup = new Group("Test Group", "This is a test group for demonstration purposes.", "", mockUser.getUser());


        // Simulate adding some members and posts
        mockGroup.addMember(new User("Member1", "member1@example.com","pass",LocalDate.now()));
        mockGroup.addAdmin(mockUser.getUser());

        mockGroup.getGroupContent().addPost(new Post(mockUser.getUser().getUserId(), new ContentFields("First post in the group!", "")));
        mockGroup.getGroupContent().addPost(new Post(mockUser.getUser().getUserId(), new ContentFields("Another update for the group.", "")));

        try {
            // Create the GroupPage and add it to the JFrame
            GroupPage groupPage = new GroupPage(mockGroup, mockUser);
            frame.add(groupPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Show the frame
        frame.setVisible(true);
    }

}
