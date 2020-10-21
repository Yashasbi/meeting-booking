package com.scheduler.meeting.service;

import com.scheduler.meeting.dao.MeetingDao;
import com.scheduler.meeting.dao.UserDao;
import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingAcceptanceState;
import com.scheduler.meeting.model.MeetingState;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingService
{
    @Autowired
    private MeetingDao meetingDao;

    @Autowired
    private UserDao userDao;

    public UUID createMeeting(String organizerName, List<String> attendees, String meetingTitle, String meetingDescription, LocalDateTime startTime, LocalDateTime endTime)
    {
        UUID meetingId=UUID.randomUUID();
        Meeting meeting=new Meeting(organizerName,attendees,meetingId,startTime,endTime,meetingTitle,meetingDescription, MeetingState.SCHEDULED);
        meetingDao.createMeeting(meeting);
        userDao.putUserMeeting(organizerName,meeting, MeetingAcceptanceState.ACCEPTED);
        for(String user:attendees){
            userDao.putUserMeeting(user,meeting,MeetingAcceptanceState.TENTATIVE);
        }
        return  meetingId;
    }
    public Meeting getMeetingById(UUID uuid){
        return meetingDao.getMeetingById(uuid);
    }

    public boolean changeMeetingState(UUID meetingId){

        Meeting meeting = meetingDao.getMeetingById(meetingId);
        String organizerName = meeting.getOrganizerName();
        userDao.deleteCancelledMeetings(organizerName,meetingId);
        List<String> attendees = meeting.getAttendees();
        for (String a : attendees){
            userDao.deleteCancelledMeetings(a,meetingId);
        }
        return meetingDao.changeMeetingByMeetingId(meeting);
    }
}

