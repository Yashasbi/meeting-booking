package com.scheduler.meeting.dao;

import com.scheduler.meeting.constants.MeetingSchedulerConstants;
import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingAcceptanceState;
import com.scheduler.meeting.model.MeetingState;
import com.scheduler.meeting.model.UserMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class UserDaoPostgres implements UserDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserMeeting> getMeetingListByUser(String username, LocalDateTime startDate, LocalDateTime endDate) {

        String query = "SELECT A.*,B.acceptancestate FROM MEETINGINFO A INNER JOIN USERMEETING B ON  A.meetingid=B.meetingid WHERE (B.username=? and  B.startdate >= ? and B.enddate <= ? );";




        List<UserMeeting> userMeetingList = new ArrayList<>();



        UserMeeting userMeeting = jdbcTemplate.queryForObject(query, new Object[]{username, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate)}, (resultSet, i) -> {

            String meetingId = resultSet.getString(MeetingSchedulerConstants.MEETING_ID_COLUMN);
            String meetingTitle = resultSet.getString(MeetingSchedulerConstants.MEETING_TITLE_COLUMN);
            String organizer = resultSet.getString(MeetingSchedulerConstants.ORGANISER_NAME_COLUMN);
            String meetingDesc = resultSet.getString(MeetingSchedulerConstants.MEETING_DESCRIPTION_COLUMN);
            Timestamp startlocalDateTime = resultSet.getTimestamp(MeetingSchedulerConstants.START_DATE_COLUMN);
            Timestamp endlocalDateTime = resultSet.getTimestamp(MeetingSchedulerConstants.END_DATE_COLUMN);
            MeetingState meetingState = MeetingState.valueOf(resultSet.getString(MeetingSchedulerConstants.MEETING_STATUS_COLUMN));
            String listOfAttendeesString = resultSet.getString(MeetingSchedulerConstants.ATTENDEES_LIST_COLUMN);

            List<String> attendeesList = Arrays.asList(listOfAttendeesString.split(","));

            MeetingAcceptanceState meetingAcceptanceState = MeetingAcceptanceState.valueOf(resultSet.getString("acceptanceState"));
            Meeting meeting = new Meeting(organizer,attendeesList,UUID.fromString(meetingId),startlocalDateTime, endlocalDateTime ,meetingTitle,meetingDesc,meetingState);
            return new UserMeeting(meeting, meetingAcceptanceState);
        });
        userMeetingList.add(userMeeting);
        return  userMeetingList;
    }




    @Override
    public void putUserMeeting(String userName, Meeting meeting, MeetingAcceptanceState meetingAcceptanceState) {
        String query = String.format("Insert into USERMEETING(username,meetingId,acceptanceState,startdate,enddate) values('%s','%s','%s','%s','%s');", userName,meeting.getMeetingId(),
                meetingAcceptanceState,meeting.getStartTime().toString(),meeting.getEndTime().toString());
        jdbcTemplate.execute(query);
    }

    @Override
    public void updateUserMeetingInfo(String userName, Meeting meeting, MeetingAcceptanceState meetingAcceptanceState) {
        System.out.println("Updated usr meeting info");
        String query = String.format("Update usermeeting set startdate='%s' , enddate = '%s' where username = '%s' ;",meeting.getStartTime().toString(),meeting.getEndTime().toString(),userName);
        jdbcTemplate.execute(query);
    }

    @Override
    public void deleteMeetings(String userName, UUID meetingId) {

        String query;

        if(userName == null) {
            query = String.format("DELETE FROM USERMEETING WHERE MEETINGID='%s';",meetingId);
        } else {
            query = String.format("DELETE FROM USERMEETING WHERE (MEETINGID='%s' AND USERNAME='%s');", meetingId, userName);
        }

        jdbcTemplate.execute(query);
    }

    @Override
    public List<String> findNewAttendeesAddedOrRemoved(List<String> newAttendeesList, List<String> oldAttendeesList) {
        return null;
    }

    @Override
    public void updateUserMeetingState(String userName, UUID meetingId, MeetingAcceptanceState meetingAcceptanceState) {
        if(meetingAcceptanceState.equals(MeetingAcceptanceState.DECLINED)){
            System.out.println("User to be removed from table");
            deleteMeetings(userName,meetingId);
        }
        else{
            String query = String.format("Update usermeeting set acceptancestate='%s' where (username = '%s' and meetingid = '%s');",meetingAcceptanceState.toString(),userName,meetingId.toString());
            jdbcTemplate.execute(query);
        }

    }
    @Override
    public boolean isMeetingScheduledBetweenTimeInterval(String userName, LocalDateTime startTime, LocalDateTime endTime) {

        //String query = "SELECT * FROM USERMEETING WHERE userName = ? and startTime >= ? and endTime <= ?";
        List<UserMeeting> userMeetings = getMeetingListByUser(userName,startTime,endTime);
        return userMeetings == null;
    }
}
