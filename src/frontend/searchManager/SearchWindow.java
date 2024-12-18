package frontend.searchManager;

import backend.CurrentUser;
import backend.User;
import frontend.UserProfile;
import utils.UIUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class SearchWindow extends JFrame {

    private JTextField searchField;
    private JPanel resultsPanel;
    private final CurrentUser currentUser;
    private final User user;

    public SearchWindow(CurrentUser currentUser) {
        this.currentUser = currentUser;
        this.user = currentUser.getUser();
        initUI();
    }

    private void initUI() {
        setTitle("Search");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Top search bar panel
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setLayout(new BorderLayout());
        searchBarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchBarPanel.setBackground(UIUtils.HEX2Color("1a4586"));

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setToolTipText("Enter search term...");
        searchBarPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = UIUtils.createSideButton("Search");
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        add(searchBarPanel, BorderLayout.NORTH);

        // Main content panel for users and groups
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        resultsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        resultsPanel.setVisible(false);
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);

        // Add search button action listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchKey = searchField.getText().trim();
                if (!searchKey.isEmpty()) {
                    resultsPanel.setVisible(true);
                    try {
                        performSearch(user, searchKey);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(SearchWindow.this, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);

    }


    private void performSearch(User user, String searchKey) throws IOException {
        user.getSearchManager().search(user, searchKey);
        User[] userResults = user.getSearchManager().getUserSearchResults();
        // Group[] groupResults = user.getSearchManager().getGroupSearchResults(); // Uncomment when group search is available

        // Clear previous results
        resultsPanel.removeAll();

        boolean resultsFound = false;

        // Display user results
        if (userResults.length > 0) {
            resultsFound = true;
            JLabel usersLabel = new JLabel("People");
            usersLabel.setFont(new Font("Arial", Font.BOLD, 18));
            usersLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
            resultsPanel.add(usersLabel);

            for (User searchedUser : userResults) {
                JButton button = UIUtils.createUserButton(searchedUser);
                button.addActionListener(e -> {
                    setVisible(false);
                    JFrame userProfileWindow = new JFrame();
                    UserProfile userProfile;
                    try {
                        if(!user.getUserId().equals(searchedUser.getUserId())){
                            userProfile = new UserProfile(user,searchedUser);
                        }
                        else{
                            userProfile = new UserProfile(user);
                        }
                        userProfileWindow.setContentPane(userProfile);
                        userProfileWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                try {
                                    performSearch(user, searchKey);
                                    setVisible(true);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    userProfileWindow.pack();
                    userProfileWindow.setVisible(true);
                });

                resultsPanel.add(button);
                resultsPanel.add(button);
            }
        }

        // Display group results
    /*
    if (groupResults.length > 0) {
        resultsFound = true;
        JLabel groupsLabel = new JLabel("Groups");
        groupsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        groupsLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        resultsPanel.add(groupsLabel);

        for (Group searchedGroup : groupResults) {
            JButton button = UIUtils.createGroupButton(searchedGroup);
            resultsPanel.add(button);
                button.addActionListener(e -> {
                    setVisible(false);
                    // TODO: Init group window here
                    try {


                        groupWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                try {
                                    performSearch(user, searchKey);
                                    setVisible(true);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    groupWindow.pack();
                    groupWindow.setVisible(true);
                });

                resultsPanel.add(button);
                resultsPanel.add(button);
        }
    }
    */

        // Display "No results found" if nothing is found
        if (!resultsFound) {
            JLabel noResultsLabel = new JLabel("No results found.");
            noResultsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            noResultsLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
            resultsPanel.add(noResultsLabel);
        }

        // Refresh panels
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}
