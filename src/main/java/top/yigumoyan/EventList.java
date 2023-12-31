package top.yigumoyan;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EventList implements Comparable<EventList>, Cloneable {
    List<Event> eventList;

    public EventList() {
        this.eventList = new ArrayList<>();
    }

    public EventList(List<Event> eventList) {
        this.eventList = new ArrayList<>();
        this.setEventList(eventList);
    }

    public void push(Event event) {
        this.eventList.add(event);
    }

    public void pushList(List<Event> e) {
        if (e != null && !e.isEmpty()) {
            this.eventList.addAll(e);
        }
    }

    public boolean isEmpty() {
        return this.eventList.isEmpty();
    }

    public int getLength() {
        return this.eventList.size();
    }

    public void setEventList(List<Event> eventList) {
        this.eventList.clear();
        this.eventList.addAll(eventList);
    }


    public List<Event> getEventList(Date date) {
        if (this.eventList == null || this.eventList.isEmpty()) {
            return null;
        }
        List<Event> result = new ArrayList<>();
        Iterator<Event> iterator = this.eventList.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            if (event.getTime().before(date)) {
                result.add(event);
                iterator.remove();
            }
        }
        return result;
    }

    public void remove(Date date) {
        if (this.eventList == null || this.eventList.isEmpty()) {
            return;
        }
        this.eventList.removeIf(event -> (date.getTime() - event.getTime().getTime()) > 5000L);
    }

    @Override
    public int compareTo(EventList e) {
        return this.eventList.equals(e.eventList) ? 1 : 0;
    }

    @Override
    public EventList clone() {
        try {
            return (EventList) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
