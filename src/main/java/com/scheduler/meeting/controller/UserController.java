package com.scheduler.meeting.controller;

import com.scheduler.meeting.dao.UserDao;
import com.scheduler.meeting.inputModel.ChangeUserMeetingStateInput;
import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.model.MeetingAcceptanceState;
import com.scheduler.meeting.model.UserMeeting;
import com.scheduler.meeting.service.UserMeetingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/user")
@RestController

public class UserController {

    @Autowired
    private UserMeetingInfoService userMeetingInfoService;

    @GetMapping
    public List<UserMeeting> getMeetingListByUser(@RequestParam(name="userName") String username, @RequestParam(name="startTime")  String startTime , @RequestParam(name="endTime") String endTime){

        System.out.println("Getting Meeting for user");
        LocalDateTime startTimeData = LocalDateTime.parse(startTime);

        LocalDateTime endTimeData = LocalDateTime.parse(endTime);

        return userMeetingInfoService.getMeetingListByUser(username,startTimeData,endTimeData);

    }

    @PutMapping
    public boolean changeUserMeetingState(@RequestParam(name="userName") String userName, @RequestBody ChangeUserMeetingStateInput changeUserMeetingStateInput){

        userMeetingInfoService.changeUserMeetingState(userName,changeUserMeetingStateInput.getMeetingId(),changeUserMeetingStateInput.getMeetingAcceptanceState());
        return true;
    }

}
