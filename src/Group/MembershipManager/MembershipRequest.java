package Group.MembershipManager;

import Group.Group;
import backend.User;
import org.json.JSONObject;
import utils.Utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MembershipRequest {
    User sender;
    Group receiver;
    MembershipRequestStatus status;
    private LocalDateTime timestamp;

    public MembershipRequest(User sender, Group receiver){
        this.sender = sender;
        this.receiver = receiver;
        this.status = MembershipRequestStatus.Pending;
        this.timestamp = LocalDateTime.now();
    }

    public MembershipRequest(User sender, Group receiver, LocalDateTime timestamp){
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.status = MembershipRequestStatus.Pending;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public User getSender() {
        return sender;
    }

    public Group getReceiver() {
        return receiver;
    }

    public MembershipRequestStatus getStatus() {
        return status;
    }

    public void setStatus(MembershipRequestStatus status) {
        this.status = status;
    }


    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("sender-id", getSender().getUserId());
        json.put("date", Utilities.DataTo_y_M_d_hh_mm(getTimestamp()));
        return json;
    }
}
