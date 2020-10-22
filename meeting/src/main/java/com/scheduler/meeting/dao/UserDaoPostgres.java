package com.scheduler.meeting.dao;

import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingAcceptanceState;
import com.scheduler.meeting.model.UserMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class UserDaoPostgres implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserMeeting> getMeetingListByUser(String username, LocalDateTime startTime, LocalDateTime endDate) {
        return null;
    }

    @Override
    public void putUserMeeting(String userName, Meeting meeting, MeetingAcceptanceState meetingAcceptanceState) {
        String query = String.format("Insert into USERMEETING(username,meetingId,acceptanceState,startdate,enddate) values('%s','%s','%s','%s','%s');", userName,meeting.getMeetingId(),
                meetingAcceptanceState,meeting.getStartTime().toString(),meeting.getEndTime().toString());
        jdbcTemplate.execute(query);
    }

    @Override
    public void updateUserMeeting(String userName, UUID meetingId, MeetingAcceptanceState meetingAcceptanceState) {

    }

    @Override
    public void deleteCancelledMeetings(String userName, UUID meetingId) {

        String query=String.format("DELETE FROM USERMEETING WHERE MEETINGID='%s';",meetingId);
        jdbcTemplate.execute(query);
    }
}
