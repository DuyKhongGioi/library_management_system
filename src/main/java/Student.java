import java.util.ArrayList;
import java.util.List;

public class Student extends Member {
    private String studentID;
    private String course;
    private int year;

    public Student(int memberID, String name, String contactInfo, String studentID, String course, int year) {
        super(memberID, name, contactInfo); // Gọi constructor của lớp cha (Member)
        this.studentID = studentID;
        this.course = course;
        this.year = year;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //Mượn sách
    @Override
    public void borrowBook(BookCopy bookCopy) {
        if (bookCopy.getStatus().equals("Available")) {
            Borrowing borrowing = new Borrowing(bookCopy);
            this.getBorrowings().add(borrowing);
            bookCopy.updateStatus("Borrowed");
            System.out.println("Student " + this.getName() + " has borrowed the book. Due date: " + borrowing.getDueDate());
        } else {
            System.out.println("This book is not available for borrowing.");
        }
    }

    // Trả sách
    @Override
    public void returnBook(Borrowing borrowing) {
        borrowing.getBookCopy().updateStatus("Available");
        this.getBorrowings().remove(borrowing);
        System.out.println("Student " + this.getName() + " has returned the book.");
    }


    // Lịch sửa mượn
    public void viewBorrowingHistory() {
        List<Borrowing> borrowings = this.getBorrowings();
        if (borrowings.isEmpty()) {
            System.out.println("Student " + this.getName() + " has no borrowing history.");
        } else {
            System.out.println("Borrowing history for student " + this.getName() + ":");
            int i = 1;
            for (Borrowing borrowing : borrowings) {
                System.out.println(i + ".");
                borrowing.displayBorrowingDetails();
                i++;
            }
        }
    }

    // Số sách đang mượn
    public int getBorrowedBookCount() {
        return this.getBorrowings().size();
    }

    // Cập nhật thông tin
    public void updatePersonalInfo(String newName, String newContactInfo) {
        this.setName(newName);
        this.setContactInfo(newContactInfo);
        System.out.println("Personal information updated for student: " + this.getName());
    }

    @Override
    public String toString() {
        return super.toString() + ", Student ID: " + studentID + ", Course: " + course + ", Year: " + year;
    }
}
