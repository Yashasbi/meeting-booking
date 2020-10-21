package com.scheduler.meeting.inputModel;

import com.scheduler.meeting.model.MeetingAcceptanceState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class ChangeUserMeetingStateInput {

    UUID meetingId;
    MeetingAcceptanceState meetingAcceptanceState;

}
