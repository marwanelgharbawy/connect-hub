package friendManager;

import backend.Database;
import backend.User;

import java.io.IOException;
import java.util.ArrayList;

public class FriendManagerC implements FriendManagerI {
    private final RequestManagerI requestManager;
    private BlockManagerI blockManager;
    private final SuggestionManagerI suggestionManager;
    private final ArrayList<User> friends;

    public FriendManagerC(RequestManagerI requestManager, BlockManagerI blockManager, SuggestionManagerI suggestionManager) throws IOException {
        this.requestManager = requestManager;
        this.blockManager = blockManager;
        this.suggestionManager = suggestionManager;
        friends = new ArrayList<>();
    }
    @Override
    public RequestManagerI getRequestManager() {
        return requestManager;
    }

    @Override
    public BlockManagerI getBlockManager() {
        return blockManager;
    }

    @Override
    public SuggestionManagerI getSuggestionManager() {
        return suggestionManager;
    }

    public void setBlockManager(BlockManagerI blockManager) {
        this.blockManager = blockManager;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }
    @Override
    public void addFriend(User mainUser ,User user) {
        if(!friends.contains(user)){
            friends.add(user);
        }

    }
    @Override
    public void removeFriend(User mainUser, User userToRemove) {
        friends.remove(userToRemove);
        userToRemove.getFriendManager().getFriends().remove(mainUser);

    }

    @Override
    public void confirmRemove(User mainUser, User removedUser) throws IOException {
        removeFriend(mainUser,removedUser);
        Database database = Database.getInstance();
        database.saveUser(mainUser);
        database.saveUser(removedUser);

    }

}
