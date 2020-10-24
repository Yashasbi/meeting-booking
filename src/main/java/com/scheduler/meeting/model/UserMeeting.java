package com.scheduler.meeting.model;

import java.time.LocalDateTime;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserMeeting {

    private Meeting meeting;
    private MeetingAcceptanceState meetingAcceptanceState;

}
