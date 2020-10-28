package com.scheduler.meeting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Meeting {

    private String organizerName;
    private List<String> attendees;
    private UUID meetingId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String meetingTitle;
    private String meetingDescription;
    private MeetingState meetingState;


}
