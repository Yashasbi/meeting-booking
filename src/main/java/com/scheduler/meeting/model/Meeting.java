package com.scheduler.meeting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Meeting {

    private String organizerName;
    private List<String> attendees;
    private UUID meetingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String meetingTitle;
    private String meetingDescription;
    private MeetingState meetingState;


}
