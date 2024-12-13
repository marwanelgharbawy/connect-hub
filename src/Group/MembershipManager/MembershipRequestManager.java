package Group.MembershipManager;

import Group.Group;
import Group.Admin;
import backend.CurrentUser;
import backend.User;
import com.sun.net.httpserver.Request;

import java.io.IOException;
import java.util.ArrayList;

public class MembershipRequestManager {
    private Group group;
    private ArrayList<MembershipRequest> membershipRequests;

    public MembershipRequestManager(Group group) throws IOException {
        this.group = group;
        this.membershipRequests = new ArrayList<MembershipRequest>();
    }

    public void clearRequests(){
        membershipRequests.clear();
    }

    public void removeMembershipRequest(MembershipRequest request, Admin admin){
        admin.declineRequest(request);
        request.setStatus(MembershipRequestStatus.Declined);
        membershipRequests.remove(request);
        group.saveGroup();
    }

    public void acceptRequest(MembershipRequest request, Admin admin){
        admin.acceptRequest(request);
        request.setStatus(MembershipRequestStatus.Approved);
        membershipRequests.remove(request);
        group.getGroupNotifManager().addNewUserNotif(request.getSender());
        group.saveGroup();
    }

    public void sendMembershipRequest(User sender){
        Group receiver = group;
        if (!(receiver.isMember(sender) || receiver.isAdmin(sender) || receiver.isPrimaryAdmin(sender))) {
            MembershipRequest request = new MembershipRequest(sender, receiver);
            membershipRequests.add(request);
            group.saveGroup();
        }
    }

    public void cancelRequest(MembershipRequest request, CurrentUser user){
        request.setStatus(MembershipRequestStatus.Declined);
        membershipRequests.remove(request);
        group.saveGroup();
    }

    public ArrayList<MembershipRequest> getMembershipRequests() {
        return membershipRequests;
    }

    public ArrayList<MembershipRequest> getGroupRequests() {
        return membershipRequests;
    }

    public MembershipRequest getUserMembershipRequests(User user){
        for (MembershipRequest request: membershipRequests)
            if (request.getSender().getUserId().equals(user.getUserId()))
                return request;
        return null;
    }

    public boolean isRequestSent(Group group, User user){
        ArrayList<MembershipRequest> requestsSent = getUserMembershipRequests(user);
        for (MembershipRequest request: requestsSent)
            if (request.getReceiver() == group)
                return true;
        return false;
    }

    public MembershipRequest getRequest(Group group, User user){
        for (MembershipRequest request: membershipRequests)
            if (request.getSender() == user && request.getReceiver() == group)
                return request;
        return null;
    }
}
