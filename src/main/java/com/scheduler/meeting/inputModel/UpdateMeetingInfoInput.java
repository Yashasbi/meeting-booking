package com.scheduler.meeting.inputModel;

import com.scheduler.meeting.model.MeetingState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMeetingInfoInput {

    private String organizerName;
    private List<String> attendees;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String meetingTitle;
    private String meetingDescription;
    private MeetingState meetingState;

}
