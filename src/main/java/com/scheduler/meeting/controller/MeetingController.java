package com.scheduler.meeting.controller;

import com.scheduler.meeting.customexceptions.ConflictingMeetingException;
import com.scheduler.meeting.customexceptions.InvalidStartTimeException;
import com.scheduler.meeting.inputModel.CreateMeetingInput;
import com.scheduler.meeting.inputModel.UpdateMeetingInfoInput;
import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.outputmodel.CreateMeetingResponse;
import com.scheduler.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@RequestMapping("api/v1/meeting")
@RestController
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Validated
    @PostMapping
    public CreateMeetingResponse createMeeting(@RequestBody CreateMeetingInput input) throws InvalidStartTimeException, ConflictingMeetingException {
        System.out.println("Creating meeting object");
        if (input.getStartTime().isAfter(input.getEndTime()) || input.getEndTime().equals(input.getStartTime())) {
            throw new InvalidStartTimeException("*******************START TIME SHOULD ALWAYS BE LESS THAN END TIME*************************");
        }
        else{

            System.out.println("Else block");
           // meetingService.findConfilctingMeetings(input.getOrganizerName(),input.getStartTime(),input.getEndTime());
            UUID meetingId = meetingService.createMeeting(input.getOrganizerName(),
                    input.getAttendees(), input.getMeetingTitle(), input.getMeetingDescription(), Timestamp.valueOf(input.getStartTime()), Timestamp.valueOf(input.getEndTime()));

            CreateMeetingResponse response = new CreateMeetingResponse();
            response.setMeetingId(meetingId);
            return response;
        }
    }

    @GetMapping(path="{meetingId}")
    public Meeting getMeetingById(@PathVariable("meetingId") UUID meetingId ){
        return  meetingService.getMeetingById(meetingId);
    }

    @DeleteMapping(path="{meetingId}")
    public boolean deleteMeetingByMeetingId(@PathVariable("meetingId") UUID meetingId) {
        return meetingService.changeMeetingState(meetingId);
    }

    @PutMapping(path="{meetingId}")
    public boolean updateMeeting(@PathVariable("meetingId") UUID meetingId,@RequestBody UpdateMeetingInfoInput updateMeetingInfoInput){
            return meetingService.updateMeeting(meetingId,updateMeetingInfoInput);
    }
}
