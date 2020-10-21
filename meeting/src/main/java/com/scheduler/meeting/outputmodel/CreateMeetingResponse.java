package com.scheduler.meeting.outputmodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Setter
public class CreateMeetingResponse {

    private UUID meetingId;
}
