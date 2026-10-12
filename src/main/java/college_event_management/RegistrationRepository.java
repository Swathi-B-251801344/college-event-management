
package college_event_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public String register(Registration registration) {

        String duplicateSql =
            "SELECT COUNT(*) FROM registrations " +
            "WHERE roll_number = ? AND event_id = ?";

        String seatsSql =
            "SELECT available_seats FROM events WHERE event_id = ?";

        String insertSql =
            "INSERT INTO registrations " +
            "(student_name, roll_number, department, event_id, registration_date) " +
            "VALUES (?, ?, ?, ?, CURDATE())";

        String updateSeatsSql =
            "UPDATE events SET available_seats = available_seats - 1 " +
            "WHERE event_id = ? AND available_seats > 0";

        try (Connection con =
                 DriverManager.getConnection(url, username, password)) {

            con.setAutoCommit(false);

            try {
                try (PreparedStatement ps =
                         con.prepareStatement(duplicateSql)) {
                    ps.setString(1, registration.getRollNumber());
                    ps.setInt(2, registration.getEventId());

                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        if (rs.getInt(1) > 0) {
                            con.rollback();
                            return "You are already registered for this event.";
                        }
                    }
                }

                try (PreparedStatement ps =
                         con.prepareStatement(updateSeatsSql)) {
                    ps.setInt(1, registration.getEventId());

                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        return "Event not found or no seats available.";
                    }
                }

                try (PreparedStatement ps =
                         con.prepareStatement(insertSql)) {
                    ps.setString(1, registration.getStudentName());
                    ps.setString(2, registration.getRollNumber());
                    ps.setString(3, registration.getDepartment());
                    ps.setInt(4, registration.getEventId());
                    ps.executeUpdate();
                }

                con.commit();
                return "Registration successful!";

            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not save the registration to MySQL.", e);
        }
    }
    
public java.util.List<Registration> findAll() {
    java.util.List<Registration> registrations = new java.util.ArrayList<>();

    String sql = "SELECT student_name, roll_number, department, event_id FROM registrations";

    try (java.sql.Connection connection = java.sql.DriverManager.getConnection(
                url, username, password);
         java.sql.PreparedStatement statement = connection.prepareStatement(sql);
         java.sql.ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Registration registration = new Registration();
            registration.setStudentName(resultSet.getString("student_name"));
            registration.setRollNumber(resultSet.getString("roll_number"));
            registration.setDepartment(resultSet.getString("department"));
            registration.setEventId(resultSet.getInt("event_id"));

            registrations.add(registration);
        }

    } catch (java.sql.SQLException e) {
        throw new RuntimeException("Could not load registrations.", e);
    }

    return registrations;
}

public java.util.List<Registration> searchByRollNumber(String rollNumber) {
    java.util.List<Registration> registrations = new java.util.ArrayList<>();

    String sql = "SELECT student_name, roll_number, department, event_id "
               + "FROM registrations WHERE roll_number = ?";

    try (java.sql.Connection connection =
                 java.sql.DriverManager.getConnection(url, username, password);
         java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, rollNumber);

        try (java.sql.ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Registration registration = new Registration();
                registration.setStudentName(resultSet.getString("student_name"));
                registration.setRollNumber(resultSet.getString("roll_number"));
                registration.setDepartment(resultSet.getString("department"));
                registration.setEventId(resultSet.getInt("event_id"));

                registrations.add(registration);
            }
        }

    } catch (java.sql.SQLException e) {
        throw new RuntimeException("Could not search registrations.", e);
    }

    return registrations;
}

public String cancelRegistration(String rollNumber, int eventId) {
    String deleteSql =
            "DELETE FROM registrations WHERE roll_number = ? AND event_id = ?";

    String updateSeatsSql =
            "UPDATE events SET available_seats = available_seats + 1 "
            + "WHERE event_id = ?";

    try (java.sql.Connection connection =
                 java.sql.DriverManager.getConnection(url, username, password)) {

        connection.setAutoCommit(false);

        try {
            int deletedRows;

            try (java.sql.PreparedStatement statement =
                         connection.prepareStatement(deleteSql)) {
                statement.setString(1, rollNumber);
                statement.setInt(2, eventId);
                deletedRows = statement.executeUpdate();
            }

            if (deletedRows == 0) {
                connection.rollback();
                return "Registration not found.";
            }

            try (java.sql.PreparedStatement statement =
                         connection.prepareStatement(updateSeatsSql)) {
                statement.setInt(1, eventId);
                statement.executeUpdate();
            }

            connection.commit();
            return "Registration cancelled successfully.";

        } catch (java.sql.SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }

    } catch (java.sql.SQLException e) {
        throw new RuntimeException("Could not cancel registration.", e);
    }
}

}
