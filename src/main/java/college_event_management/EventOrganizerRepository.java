
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
public class EventOrganizerRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<EventOrganizer> findAll() {
        List<EventOrganizer> assignments = new ArrayList<>();

        String sql =
            "SELECT eo.id, eo.event_id, eo.organizer_id, " +
            "e.event_name, o.organizer_name " +
            "FROM event_organizers eo " +
            "JOIN events e ON eo.event_id = e.event_id " +
            "JOIN organizers o ON eo.organizer_id = o.organizer_id";

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                EventOrganizer assignment = new EventOrganizer(
                    resultSet.getInt("id"),
                    resultSet.getInt("event_id"),
                    resultSet.getInt("organizer_id"),
                    resultSet.getString("event_name"),
                    resultSet.getString("organizer_name")
                );

                assignments.add(assignment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not retrieve event organizer assignments.", e);
        }

        return assignments;
    }
}
