
package college_event_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();

        String sql = "SELECT event_id, event_name, event_date, venue, "
                   + "description, available_seats FROM events";

        try (Connection connection =
                 DriverManager.getConnection(url, username, password);
             PreparedStatement statement =
                 connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                events.add(new Event(
                    result.getInt("event_id"),
                    result.getString("event_name"),
                    result.getString("event_date"),
                    result.getString("venue"),
                    result.getString("description"),
                    result.getInt("available_seats")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not load events from MySQL.", e);
        }

        return events;
    }
    public Event findById(int eventId) {
    String sql = "SELECT event_id, event_name, event_date, venue, "
               + "description, available_seats FROM events WHERE event_id = ?";

    try (Connection connection = DriverManager.getConnection(url, username, password);
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, eventId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new Event(
                    resultSet.getInt("event_id"),
                    resultSet.getString("event_name"),
                    resultSet.getString("event_date"),
                    resultSet.getString("venue"),
                    resultSet.getString("description"),
                    resultSet.getInt("available_seats")
                );
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Could not retrieve event details.", e);
    }

    return null;
}

public boolean update(Event event) {
    String sql = "UPDATE events SET event_name = ?, event_date = ?, "
               + "venue = ?, description = ?, available_seats = ? "
               + "WHERE event_id = ?";

    try (Connection connection = DriverManager.getConnection(url, username, password);
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, event.getEventName());
        statement.setString(2, event.getEventDate());
        statement.setString(3, event.getVenue());
        statement.setString(4, event.getDescription());
        statement.setInt(5, event.getAvailableSeats());
        statement.setInt(6, event.getEventId());

        return statement.executeUpdate() > 0;

    } catch (SQLException e) {
        throw new RuntimeException("Could not update event details.", e);
    }
}
}
