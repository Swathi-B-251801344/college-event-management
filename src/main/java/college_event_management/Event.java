
package college_event_management;

public class Event {
    private int eventId;
    private String eventName;
    private String eventDate;
    private String venue;
    private String description;
    private int availableSeats;

    public Event(int eventId, String eventName, String eventDate,
                 String venue, String description, int availableSeats) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.venue = venue;
        this.description = description;
        this.availableSeats = availableSeats;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
    
public void setEventName(String eventName) {
    this.eventName = eventName;
}

public void setEventDate(String eventDate) {
    this.eventDate = eventDate;
}

public void setVenue(String venue) {
    this.venue = venue;
}

public void setDescription(String description) {
    this.description = description;
}

public void setAvailableSeats(int availableSeats) {
    this.availableSeats = availableSeats;
}

}
