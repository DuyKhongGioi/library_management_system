import java.util.ArrayList;
import java.util.List;

public class Member {
    private int memberID;
    private String name;
    private String contactInfo;
    private List<Borrowing> borrowings;

    public Member(int memberID, String name, String contactInfo) {
        this.memberID = memberID;
        this.name = name;
        this.contactInfo = contactInfo;
        borrowings = new ArrayList<Borrowing>();
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    //Phương thức mượn sách
    public void borrowBook(BookCopy bookCopy) {
        if (bookCopy.getStatus().equals("Available")) {
            Borrowing borrowing = new Borrowing(bookCopy);
            borrowings.add(borrowing);
            bookCopy.updateStatus("Borrowed");
            System.out.println("Member" + this.toString() + " have just borrowed this book"
                    + ", must return on " + borrowing.getDueDate());
        } else {
            System.out.println("This book is not available now. Please comeback later.");
        }
    }

    //Return book method
    public void returnBook(Borrowing borrowing) {
        borrowing.getBookCopy().updateStatus("Available");
        borrowings.remove(borrowing);
        System.out.println("Member" + this.toString() + " have just returned this book");
    }

    //In ra các sách đang mượn của thành viên
    public void displayBorrowings() {
        System.out.println("Member" + this.toString() + " has " + borrowings.size() + " borrowings:");
        int i = 1;
        for (Borrowing borrowing : borrowings) {
            System.out.println(i + ".");
            i++;
            borrowing.displayBorrowingDetails();
        }
    }

    @Override
    public String toString() {
        return String.format("%-20s %-20s %-20s", this.getMemberID(), this.getName(), this.getContactInfo());
    }
}
