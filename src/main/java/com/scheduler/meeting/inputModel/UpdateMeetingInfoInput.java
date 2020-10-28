package com.scheduler.meeting.inputModel;

import com.scheduler.meeting.model.MeetingState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateMeetingInfoInput {
    private List<String> attendees;
    private Timestamp startTime;
    private Timestamp endTime;
    private String meetingTitle;
    private String meetingDescription;
    private MeetingState meetingState;

}
