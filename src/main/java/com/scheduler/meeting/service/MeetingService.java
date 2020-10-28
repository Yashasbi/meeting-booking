package com.scheduler.meeting.service;

import com.scheduler.meeting.customexceptions.ConflictingMeetingException;
import com.scheduler.meeting.customexceptions.InvalidStartTimeException;
import com.scheduler.meeting.dao.MeetingDao;
import com.scheduler.meeting.dao.UserDao;
import com.scheduler.meeting.inputModel.UpdateMeetingInfoInput;
import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingAcceptanceState;
import com.scheduler.meeting.model.MeetingState;
import com.scheduler.meeting.model.UserMeeting;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeetingService
{
    @Autowired
    private MeetingDao meetingDao;

    @Autowired
    private UserDao userDao;


    public UUID createMeeting(String organizerName, List<String> attendees, String meetingTitle, String meetingDescription, Timestamp startTime, Timestamp endTime)
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
        userDao.deleteMeetings(organizerName, meetingId);
        List<String> attendees = meeting.getAttendees();
        userDao.deleteMeetings(null, meetingId);
        return meetingDao.changeMeetingByMeetingId(meeting);
    }

    public boolean updateMeeting(UUID meetingId,UpdateMeetingInfoInput updateMeetingInfoInput){

        Meeting meeting = meetingDao.getMeetingById(meetingId);
        List<String> existingAttendeesList = meeting.getAttendees();
        boolean startTimeChanged = false;
        boolean endTimeChanged = false;

        if(updateMeetingInfoInput.getMeetingTitle() != null) {
            meeting.setMeetingTitle(updateMeetingInfoInput.getMeetingTitle());
        }
        if(updateMeetingInfoInput.getMeetingDescription() != null) {
            meeting.setMeetingDescription(updateMeetingInfoInput.getMeetingDescription());
        }
        if(updateMeetingInfoInput.getStartTime()!=null){
            System.out.println("Updated time is " +updateMeetingInfoInput.getStartTime());
            meeting.setStartTime(updateMeetingInfoInput.getStartTime());
            System.out.println("Time in meeting object is " +meeting.getStartTime());
            startTimeChanged = true;
        }
        if(updateMeetingInfoInput.getEndTime() != null) {
            meeting.setEndTime(updateMeetingInfoInput.getEndTime());
            endTimeChanged = true;
        }
        if(updateMeetingInfoInput.getMeetingState()!=null) {
            meeting.setMeetingState(updateMeetingInfoInput.getMeetingState());
        }
        if(updateMeetingInfoInput.getAttendees()!=null) {
            meeting.setAttendees(updateMeetingInfoInput.getAttendees());

            List<String> newAttendeees = findDifferenceBetweenTwoList(updateMeetingInfoInput.getAttendees(), existingAttendeesList);
            List<String> removedAttenddes = findDifferenceBetweenTwoList(existingAttendeesList, updateMeetingInfoInput.getAttendees());

            removeMeetingForUserList(removedAttenddes, meetingId);
            addNewAttenddesList(newAttendeees, meeting);
        }

        /* Change the user table only when time of meeting is modified */

        if(startTimeChanged || endTimeChanged) {

            System.out.println("Time in meeting object is " +meeting.getStartTime());
            System.out.println("Start time changed");
            userDao.updateUserMeetingInfo(meeting.getOrganizerName(),meeting,MeetingAcceptanceState.ACCEPTED);
            List<String> commonUserList = findCommonBetweenTwoList(existingAttendeesList, updateMeetingInfoInput.getAttendees());
            updateMeetingForUserList(commonUserList, meeting);
        }

        return meetingDao.updateMeeting(meetingId, meeting);
    }

    private void addNewAttenddesList(List<String> newAttendeees, Meeting meeting) {
        newAttendeees.forEach(user -> {
            userDao.putUserMeeting(user, meeting, MeetingAcceptanceState.TENTATIVE);
        });
    }

    private List<String> findDifferenceBetweenTwoList(List<String>  newList, List<String> oldList){
            return newList.stream().filter(newAttendees->!oldList.contains(newAttendees)).collect(Collectors.toList());
    }

    private List<String> findCommonBetweenTwoList(List<String>  existingList, List<String> newList){
        if(newList == null) {
            return existingList;
        }
        return existingList.stream().filter(newAttendees -> newList.contains(newAttendees)).collect(Collectors.toList());
    }

    private void removeMeetingForUserList(List<String> userList, UUID meetingId) {
        userList.forEach(user -> {
            userDao.deleteMeetings(user, meetingId);
        });
    }

    private void updateMeetingForUserList(List<String> userList, Meeting meeting) {
        userList.forEach(user -> {
            userDao.updateUserMeetingInfo(user, meeting, MeetingAcceptanceState.TENTATIVE);
        });
    }
    public void findConfilctingMeetings(String userName,LocalDateTime startTime,LocalDateTime endTime) throws ConflictingMeetingException {
          if(!userDao.isMeetingScheduledBetweenTimeInterval(userName,startTime,endTime)){
              throw new ConflictingMeetingException("**************Conflicting time found.Please select a different time********************");
          }
    }
}

