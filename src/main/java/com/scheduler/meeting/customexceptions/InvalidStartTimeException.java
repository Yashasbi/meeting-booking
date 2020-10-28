package com.scheduler.meeting.customexceptions;

public class InvalidStartTimeException extends Exception {
    public InvalidStartTimeException(String s){
        super(s);
    }
}
