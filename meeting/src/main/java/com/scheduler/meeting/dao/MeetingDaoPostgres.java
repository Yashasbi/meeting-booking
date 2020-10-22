package com.scheduler.meeting.dao;

import com.scheduler.meeting.constants.MeetingSchedulerConstants;
import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class MeetingDaoPostgres implements MeetingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createMeeting(Meeting meeting) {
            String query = String.format("INSERT INTO MEETINGINFO VALUES('%s','%s','%s','%s','%s','%s','%s','%s');", meeting.getMeetingId(),
                    meeting.getOrganizerName(), meeting.getMeetingTitle(), meeting.getMeetingDescription(), meeting.getStartTime().toString(),
                    meeting.getEndTime().toString(), meeting.getMeetingState(), String.join(",", meeting.getAttendees()));
            jdbcTemplate.execute(query);


    }

    @Override
    public Meeting getMeetingById(UUID meetingId) {

        String query = "SELECT * FROM MEETINGINFO WHERE MEETINGID = ?";
        Meeting meeting = jdbcTemplate.queryForObject(query, new Object[]{meetingId.toString()}, (resultSet, i) -> {

            String meetingTitle = resultSet.getString(MeetingSchedulerConstants.MEETING_TITLE_COLUMN);
            String organizer = resultSet.getString(MeetingSchedulerConstants.ORGANISER_NAME_COLUMN);
            String meetingDesc = resultSet.getString(MeetingSchedulerConstants.MEETING_DESCRIPTION_COLUMN);
            LocalDateTime startlocalDateTime = LocalDateTime.parse(resultSet.getString(MeetingSchedulerConstants.START_DATE_COLUMN));
            LocalDateTime endlocalDateTime = LocalDateTime.parse(resultSet.getString(MeetingSchedulerConstants.END_DATE_COLUMN));
            MeetingState meetingState = MeetingState.valueOf(resultSet.getString(MeetingSchedulerConstants.MEETING_STATUS_COLUMN));
            String listOfAttendeesString = resultSet.getString(MeetingSchedulerConstants.ATTENDEES_LIST_COLUMN);

            List<String> attendeesList = Arrays.asList(listOfAttendeesString.split(","));

            return new Meeting(organizer, attendeesList, meetingId, startlocalDateTime, endlocalDateTime, meetingTitle, meetingDesc, meetingState);
        });

        return meeting;
    }

    @Override
    public boolean changeMeetingByMeetingId(Meeting meeting) {
        String query=String.format("UPDATE MEETINGINFO SET MEETINGSTATUS='%s' where MEETINGID='%s';",MeetingState.CANCELLED,meeting.getMeetingId());

        jdbcTemplate.execute(query);

        return true;
    }
}