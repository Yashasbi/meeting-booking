package com.scheduler.meeting.service;


import com.scheduler.meeting.dao.MeetingDao;
import com.scheduler.meeting.dao.UserDao;
import com.scheduler.meeting.inputModel.ChangeUserMeetingStateInput;
import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingAcceptanceState;
import com.scheduler.meeting.model.UserMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserMeetingInfoService {

    @Autowired
    private UserDao userDao;


    public List<UserMeeting> getMeetingListByUser(String username, LocalDateTime startTime , LocalDateTime endTime){

        return userDao.getMeetingListByUser(username,startTime,endTime);

    }
    public boolean changeUserMeetingState(String userName, UUID meetingId,MeetingAcceptanceState meetingAcceptanceState){
        userDao.updateUserMeeting(userName,meetingId,meetingAcceptanceState);
        return true;
    }
}
