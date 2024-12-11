package searchManager;

import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public interface SearchManagerI {
    void search(User user, String searchKey) throws IOException;
//    Group[] getGroupSearchResults();
    User[] getUserSearchResults();
}
