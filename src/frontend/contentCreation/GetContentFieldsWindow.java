package frontend.contentCreation;

import content.ContentFields;
import utils.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;

public class GetContentFieldsWindow extends JFrame {
    private JTextField textField1; //Input text field
    private JPanel mainPanel;
    private JButton addPictureButton; // Button to upload picture
    private JButton submitButton; // Submit Button
    private String text;
    private String imagePath;

    public GetContentFieldsWindow(ContentFields contentFields, boolean isStory) {
        // Set the title dynamically according to the instance of the content
        String title;
        if (isStory) {
            title = "Add Story";
        } else {
            title = "Add Post";
        }
        UIUtils.initializeWindow(this, mainPanel, title, 400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Set up the button listeners for content parsing
        setupButtonListeners(contentFields);
    }

    // Set up the button action listeners
    private void setupButtonListeners(ContentFields contentFields) {

        // Get picture path and add picture to project directory
        addPictureButton.addActionListener(_ -> imagePath = Utilities.addPicture(this));
        // Get content
        submitButton.addActionListener(_ -> {
            // Get text and check that the field is not empty
            text = textField1.getText();
            if (!text.isEmpty()) {
                contentFields.setText(text);
                contentFields.setImagePath(imagePath);
                dispose();
            } else {
                // Display an error message if text field is empty
                JOptionPane.showMessageDialog(null, "Text field is empty!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }


}
