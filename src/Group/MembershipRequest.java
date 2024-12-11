package Group;

import backend.User;

public class MembershipRequest {
    User sender;
    Group receiver;
    String status;

    public MembershipRequest(User sender, Group receiver){
        this.sender = sender;
        this.receiver = receiver;
        this.status = "pending";
    }

    public User getSender() {
        return sender;
    }

    public Group getReceiver() {
        return receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
