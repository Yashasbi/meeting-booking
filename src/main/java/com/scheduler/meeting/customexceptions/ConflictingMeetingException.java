package com.scheduler.meeting.customexceptions;

public class ConflictingMeetingException extends Exception {
    public ConflictingMeetingException(String s){
        super(s);
    }
}
