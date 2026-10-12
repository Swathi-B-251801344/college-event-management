
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
public class PaymentRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT payment_id, registration_id, amount, "
                   + "payment_status, payment_date FROM payments";

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Payment payment = new Payment(
                    resultSet.getInt("payment_id"),
                    resultSet.getInt("registration_id"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getString("payment_status"),
                    resultSet.getString("payment_date")
                );

                payments.add(payment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Could not retrieve payment details.", e);
        }

        return payments;
    }
}
