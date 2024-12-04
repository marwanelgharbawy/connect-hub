package utils;

import javax.swing.*;

public class UIUtils {
    public static void initializeWindow(JFrame window, JPanel panel, String title, int width, int height) {
        window.setContentPane(panel);
        window.setTitle(title);
        window.setSize(width, height);
        window.setVisible(true);
    }
}
