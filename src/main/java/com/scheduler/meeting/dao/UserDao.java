package com.scheduler.meeting.dao;

import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingAcceptanceState;
import com.scheduler.meeting.model.UserMeeting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserDao {

     List<UserMeeting> getMeetingListByUser(String username, LocalDateTime startTime , LocalDateTime endDate);

     void putUserMeeting(String userName, Meeting meeting, MeetingAcceptanceState meetingAcceptanceState);

     void deleteMeetings(String userName, UUID meetingId);

     void updateUserMeetingInfo(String userName, Meeting meeting, MeetingAcceptanceState meetingAcceptanceState);

     List<String> findNewAttendeesAddedOrRemoved(List<String> newAttendeesList , List<String> oldAttendeesList);

     void updateUserMeetingState(String userName, UUID meetingId, MeetingAcceptanceState meetingAcceptanceState);
}
