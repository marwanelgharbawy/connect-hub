package frontend.searchManager;

import backend.User;
import utils.UIUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
public class SearchWindow extends JFrame {

    private JTextField searchField;
    private JPanel usersPanel;
    private JPanel groupsPanel;
    private JButton searchButton;
    private JPanel resultsPanel;
    private User user;

    public SearchWindow(User user) {
        this.user = user;
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

        searchButton = UIUtils.createSideButton("Search");
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
            JLabel usersLabel = new JLabel("Users");
            usersLabel.setFont(new Font("Arial", Font.BOLD, 18));
            usersLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
            resultsPanel.add(usersLabel);

            for (User searchedUser : userResults) {
                JButton button = UIUtils.createUserButton(searchedUser);
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
