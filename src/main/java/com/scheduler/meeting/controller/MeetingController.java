package com.scheduler.meeting.controller;

import com.scheduler.meeting.inputModel.CreateMeetingInput;
import com.scheduler.meeting.inputModel.UpdateMeetingInfoInput;
import com.scheduler.meeting.model.Meeting;
import com.scheduler.meeting.outputmodel.CreateMeetingResponse;
import com.scheduler.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/meeting")
@RestController
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping
    public CreateMeetingResponse createMeeting(@RequestBody CreateMeetingInput input)
    {

        for(String s : input.getAttendees()){
            System.out.println(s);
        }
        UUID meetingId = meetingService.createMeeting(input.getOrganizerName(),
                input.getAttendees(), input.getMeetingTitle(), input.getMeetingDescription(), input.getStartTime(), input.getEndTime());

        CreateMeetingResponse response = new CreateMeetingResponse();
        response.setMeetingId(meetingId);

        System.out.println("Meeting created by id : " + meetingId);

        return response;
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
