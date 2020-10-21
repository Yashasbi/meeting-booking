package com.scheduler.meeting.dao;

import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingState;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class FakeMeetingDataAccessService implements MeetingDao {
    private HashMap<UUID, Meeting> meetingDb =new HashMap<>();

    @Override

    public void createMeeting(Meeting meeting)
    {
        meetingDb.put(meeting.getMeetingId(),meeting);
    }

    @Override
    public Meeting getMeetingById(UUID meetingId) {
        if(meetingDb.containsKey(meetingId)){
            return meetingDb.get(meetingId);
        }
        return null;
    }

    @Override
    public boolean changeMeetingByMeetingId(Meeting meeting) {
        meeting.setMeetingState(MeetingState.CANCELLED);
        return true;
    }

}
