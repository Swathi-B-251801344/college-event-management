
package college_event_management;

public class Organizer {

    private int organizerId;
    private String organizerName;
    private String department;
    private String email;
    private String phone;

    public Organizer(int organizerId, String organizerName,
                     String department, String email, String phone) {
        this.organizerId = organizerId;
        this.organizerName = organizerName;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
