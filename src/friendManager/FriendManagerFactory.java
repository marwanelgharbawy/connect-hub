package friendManager;

import java.io.IOException;

public class FriendManagerFactory {

    public static FriendManagerC createFriendManager() throws IOException {
        // Initialize request and suggestion managers
        RequestManagerC requestManager = new RequestManagerC();
        SuggestionManagerC suggestionManager = new SuggestionManagerC();
        // Create a friend manager without block manager
        FriendManagerC friendManager = new FriendManagerC(requestManager, null, suggestionManager);
        // Create block manager using friend manager
        BlockManagerC blockManager = new BlockManagerC(friendManager);
        friendManager.setBlockManager(blockManager);
        return friendManager;
    }


}
