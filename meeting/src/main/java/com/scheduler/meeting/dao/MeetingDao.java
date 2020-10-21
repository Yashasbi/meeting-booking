package com.scheduler.meeting.dao;

import com.scheduler.meeting.model.Meeting;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MeetingDao {
    public void createMeeting(Meeting meeting);

    public Meeting getMeetingById(UUID meetingId);

    public boolean changeMeetingByMeetingId(Meeting meeting);


}
