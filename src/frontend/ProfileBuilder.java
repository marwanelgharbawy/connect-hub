package frontend;

import javax.swing.*;
import java.io.IOException;

interface ProfileBuilder {

    JPanel buildCoverPhoto();

    JPanel buildBioPanel();

    JPanel buildPostsPanel() throws IOException;

    JPanel build() throws IOException;
}
