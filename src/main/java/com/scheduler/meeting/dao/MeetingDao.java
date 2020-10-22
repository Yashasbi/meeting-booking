package com.scheduler.meeting.dao;

import com.scheduler.meeting.model.Meeting;

import java.util.UUID;

public interface MeetingDao {
    public void createMeeting(Meeting meeting);

    public Meeting getMeetingById(UUID meetingId);

    public boolean changeMeetingByMeetingId(Meeting meeting);

    public boolean updateMeeting(UUID meetingId,Meeting meeting);


}
