
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
public class OrganizerRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<Organizer> findAll() {
        List<Organizer> organizers = new ArrayList<>();

        String sql = "SELECT organizer_id, organizer_name, "
                   + "department, email, phone FROM organizers";

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Organizer organizer = new Organizer(
                    resultSet.getInt("organizer_id"),
                    resultSet.getString("organizer_name"),
                    resultSet.getString("department"),
                    resultSet.getString("email"),
                    resultSet.getString("phone")
                );

                organizers.add(organizer);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not retrieve organizer details.", e);
        }

        return organizers;
    }
}
