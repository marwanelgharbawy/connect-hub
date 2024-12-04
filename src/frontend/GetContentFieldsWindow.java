package frontend;

import content.ContentFields;
import utils.UIUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GetContentFieldsWindow extends JFrame{
    private JTextField textField1; //Input text field
    private JPanel mainPanel;
    private JButton addPictureButton; // Button to upload picture
    private JButton submitButton; // Submit Button
    String text;
    String imagePath;
    String title;
    public GetContentFieldsWindow(ContentFields contentFields, boolean isStory) {
        if(isStory)
            title = "Add Story";
        else title = "Add Post";
        UIUtils.initializeWindow(this, mainPanel, title, 400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Get picture path
        addPictureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagePath = addPicture();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get text and check that the field is not empty
                text = textField1.getText();
                if(!text.isEmpty()){
                    contentFields.setText(text);
                    contentFields.setImagePath(imagePath);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(null,"Text field is empty!","Empty Field Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
    private String addPicture(){
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
                JOptionPane.showMessageDialog(null, "Invalid file format! Please an image file.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            System.out.println(file.getPath());
            return file.getPath();
        }
        return null;
    }
}
