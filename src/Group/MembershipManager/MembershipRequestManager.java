package Group.MembershipManager;

import Group.Group;
import Group.Admin;
import backend.CurrentUser;
import backend.User;

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
    }

    public void acceptRequest(MembershipRequest request, Admin admin){
        admin.acceptRequest(request);
        request.setStatus(MembershipRequestStatus.Approved);
        membershipRequests.remove(request);
    }

    public void sendMembershipRequest(User sender){
        Group receiver = group;
        if (!(receiver.isMember(sender) || receiver.isAdmin(sender) || receiver.isPrimaryAdmin(sender))) {
            MembershipRequest request = new MembershipRequest(sender, receiver);
            membershipRequests.add(request);
        }
    }

    public void cancelRequest(MembershipRequest request, CurrentUser user){
        request.setStatus(MembershipRequestStatus.Declined);
        membershipRequests.remove(request);
    }

    public ArrayList<MembershipRequest> getMembershipRequests() {
        return membershipRequests;
    }

    public ArrayList<MembershipRequest> getGroupRequests() {
        ArrayList<MembershipRequest> groupRequests = new ArrayList<>();
        for (MembershipRequest request: membershipRequests)
            if (request.getReceiver() == group)
                groupRequests.add(request);
        return groupRequests;
    }

    public ArrayList<MembershipRequest> getUserMembershipRequests(User user){
        ArrayList<MembershipRequest> userRequests = new ArrayList<>();
        for (MembershipRequest request: membershipRequests)
            if (request.getSender() == user)
                userRequests.add(request);
        return userRequests;
    }
}
