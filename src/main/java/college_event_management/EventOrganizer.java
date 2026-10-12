
package college_event_management;

public class EventOrganizer {

    private int id;
    private int eventId;
    private int organizerId;
    private String eventName;
    private String organizerName;

    public EventOrganizer(int id, int eventId, int organizerId,
                          String eventName, String organizerName) {
        this.id = id;
        this.eventId = eventId;
        this.organizerId = organizerId;
        this.eventName = eventName;
        this.organizerName = organizerName;
    }

    public int getId() {
        return id;
    }

    public int getEventId() {
        return eventId;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getOrganizerName() {
        return organizerName;
    }
}
