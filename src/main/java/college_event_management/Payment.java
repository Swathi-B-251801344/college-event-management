
package college_event_management;

import java.math.BigDecimal;

public class Payment {

    private int paymentId;
    private int registrationId;
    private BigDecimal amount;
    private String paymentStatus;
    private String paymentDate;

    public Payment(int paymentId, int registrationId,
                   BigDecimal amount, String paymentStatus,
                   String paymentDate) {
        this.paymentId = paymentId;
        this.registrationId = registrationId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }
}
