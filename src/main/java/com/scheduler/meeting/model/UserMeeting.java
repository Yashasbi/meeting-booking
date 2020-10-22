package com.scheduler.meeting.model;

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
