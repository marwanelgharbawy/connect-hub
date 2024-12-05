package frontend.contentCreation;

import content.ContentFields;
import utils.UIUtils;

import javax.swing.*;
import java.io.File;

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
        addPictureButton.addActionListener(_ -> imagePath = addPicture());
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

    private String addPicture() {
        // Get file via file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Upload Image");

        int userSelection;
        userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Check if the file is in the correct format : ".png",".jpg",".jpeg"
            if (!file.getName().toLowerCase().endsWith(".png") && !file.getName().toLowerCase().endsWith(".jpg")
                    && !file.getName().toLowerCase().endsWith(".jpeg")) {
                // Display error message if format is invalid
                JOptionPane.showMessageDialog(null, "Invalid file format! Please an image file.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            // Return image path
            System.out.println(file.getPath());
            return file.getPath();
        }
        // Return null if no image is selected
        return null;
    }
}
