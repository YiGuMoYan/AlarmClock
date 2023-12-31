package top.yigumoyan;

import java.util.Date;

public class Event {
    private Date time;
    private String message;

    public Event(Date time, String message) {
        this.time = time;
        this.message = message;
    }

    public Event() {
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Event{" +
                "time=" + time +
                ", message='" + message + '\'' +
                '}';
    }
}
