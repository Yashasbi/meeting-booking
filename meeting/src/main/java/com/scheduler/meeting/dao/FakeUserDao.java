package com.scheduler.meeting.dao;

import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingAcceptanceState;
import com.scheduler.meeting.model.UserMeeting;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FakeUserDao implements UserDao
{
    HashMap<String, List<UserMeeting>>  userMeetingInfoDb = new HashMap<>();

    @Override
    public List<UserMeeting> getMeetingListByUser(String userName, LocalDateTime startTime, LocalDateTime endTime) {

        List<UserMeeting> userMeetings = userMeetingInfoDb.get(userName);

        if(userMeetings == null) {
            return Collections.EMPTY_LIST;
        }

        return userMeetings.stream()
                .filter(userMeeting -> {
                    boolean isValidStartTime = userMeeting.getMeeting().getStartTime().isAfter(startTime) || userMeeting.getMeeting().getStartTime().isEqual(startTime);
                    boolean isValidEndTime = userMeeting.getMeeting().getEndTime().isBefore(endTime) || userMeeting.getMeeting().getEndTime().isEqual(endTime);
                    boolean isDeclined = MeetingAcceptanceState.DECLINED.equals(userMeeting.getMeetingAcceptanceState());

                    return isValidStartTime && isValidEndTime && !isDeclined;
                })
                .collect(Collectors.toList());

    }

    @Override
    public void putUserMeeting(String userName, Meeting meeting, MeetingAcceptanceState meetingAcceptanceState) {

        UserMeeting userNewMeeting = new UserMeeting(meeting, meetingAcceptanceState);
        List<UserMeeting> userMeeting = userMeetingInfoDb.get(userName);

        if(userMeeting == null) {
           userMeeting = new ArrayList<>();
        }

       userMeeting.add(userNewMeeting);

       userMeetingInfoDb.put(userName,userMeeting);
    }

    @Override
    public void updateUserMeeting(String userName, UUID meetingId, MeetingAcceptanceState meetingAcceptanceState) {
        List<UserMeeting> userMeetings = userMeetingInfoDb.get(userName);

        Optional<UserMeeting> userMeetingOptional = userMeetings.stream().filter(userMeeting -> userMeeting.getMeeting().getMeetingId().equals(meetingId)).findFirst();
        userMeetingOptional.get().setMeetingAcceptanceState(meetingAcceptanceState);

        //userMeetings.add(userMeetingOptional.get());


    }

    @Override
    public void deleteCancelledMeetings(String userName, UUID meetingId) {
        List<UserMeeting> userMeetings = userMeetingInfoDb.get(userName);
        Optional<UserMeeting> optionalUserMeeting = userMeetings.stream().filter(userMeeting -> userMeeting.getMeeting().getMeetingId().equals(meetingId)).findFirst();
        userMeetings.remove(optionalUserMeeting.get());

    }


}
