
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
public class StudentRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        String sql = "SELECT student_id, student_name, "
                   + "roll_number, department FROM students";

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Student student = new Student(
                    resultSet.getInt("student_id"),
                    resultSet.getString("student_name"),
                    resultSet.getString("roll_number"),
                    resultSet.getString("department")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not retrieve student details.", e);
        }

        return students;
    }
}
