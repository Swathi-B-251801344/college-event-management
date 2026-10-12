
package college_event_management;

public class Student {

    private int studentId;
    private String studentName;
    private String rollNumber;
    private String department;

    public Student(int studentId, String studentName,
                   String rollNumber, String department) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.department = department;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getDepartment() {
        return department;
    }
}
