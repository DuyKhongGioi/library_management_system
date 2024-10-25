//import java.util.Scanner;
//
//public class LibraryStaff extends Member {
//    private int staffID;
//    private String position;
//    private LibraryManagement libraryManagement;
//    private Scanner input = new Scanner(System.in);
//
//    public LibraryStaff(int staffID, String position, int memberID, String name, String contactInfo, LibraryManagement libraryManagement) {
//        super(memberID, name, contactInfo);
//        this.staffID = staffID;
//        this.position = position;
//        this.libraryManagement = libraryManagement;
//    }
//
//    public void addBook() {
//        libraryManagement.addBooks();
//    }
//
//    public void removeBook() {
//        libraryManagement.removeBooksByISBN();
//    }
//
//    public void addNewMember() {
//        libraryManagement.addMultipleMembers();
//    }
//
//    public void removeMember() {
//        libraryManagement.removeMultipleMembers();
//    }
//
//    public void displayAllBooks() {
//        libraryManagement.displayAllBooks();
//    }
//
//    public void displayAllMembers() {
//        if (libraryManagement.getMembers().isEmpty()) {
//            System.out.println("There are no members in the library.");
//        } else {
//            for (Member member : libraryManagement.getMembers()) {
//                System.out.println(member.toString());
//            }
//        }
//    }
//
//    public void borrowBookAsMember(BookCopy bookCopy) {
//        if (this != null) {
//            this.borrowBook(bookCopy);
//        } else {
//            System.out.println("This staff member is not a library member.");
//        }
//    }
//
//    public void returnBookAsMember(Borrowing borrowing) {
//        if (this != null) {
//            this.returnBook(borrowing);
//        } else {
//            System.out.println("This staff member is not a library member.");
//        }
//    }
//
//    public void checkBookStatus() {
//        libraryManagement.checkBookStatus();
//    }
//
//    public void calculateLateFee(int memberID) {
//        libraryManagement.calculateLateFee(memberID);
//    }
//
//    @Override
//    public String toString() {
//        return "Library Staff ID: " + staffID + ", Position: " + position + ", " + super.toString();
//    }
//}
