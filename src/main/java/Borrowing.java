import java.text.SimpleDateFormat;
import java.util.Date;

public class Borrowing {
    private static final double FEE_PER_DAY = 3000;
    private String borrowingID;
    private Date borrowDate;
    private Date returnDate;
    private Date dueDate;
    private BookCopy bookCopy;

    public Borrowing(BookCopy bookCopy) {
        this.borrowingID = borrowIDGenerator();
        this.bookCopy = bookCopy;
        this.borrowDate = new Date();
        this.dueDate = this.calculateDueDate();
    }

    public String getBorrowingID() {
        return borrowingID;
    }

    public void setBorrowingID(String borrowingID) {
        this.borrowingID = borrowingID;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    //Tạo bộ sinh mã mượn sách
    private String borrowIDGenerator() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStamp = formatter.format(this.borrowDate);
        return timeStamp;
    }

    //Lấy về ngày đáo hạn
    private Date calculateDueDate() {
        long MILLISECONDS_IN_A_DAY = 24 * 60 * 60 * 1000L;
        long dueDateInMillis = System.currentTimeMillis() + (30 * MILLISECONDS_IN_A_DAY);
        return new Date(dueDateInMillis);
    }

    //Phương thức xin thêm số ngày mượn
    public void extendsDueDate(int extendDays) {
        long newDueDate = dueDate.getTime() + (long) extendDays * 24 * 60 * 60 * 1000;
        this.dueDate = new Date(newDueDate);
        System.out.println("You have just extend this due date for this book to "
        + this.getDueDate());
    }

    public boolean isOverdue() {
        Date currentDate = new Date();
        return currentDate.after(this.dueDate);
    }

    public double calculateLateFee() {
        if (isOverdue()) {
            long overdueDays = (new Date().getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000);
            return overdueDays * FEE_PER_DAY;
        } else {
            return 0;
        }
    }

    public void displayBorrowingDetails() {
        Book book = bookCopy.getBook();
        System.out.println("Borrowing ID :" + this.getBorrowingID());
        System.out.println("Book : " + book.getTitle());
        System.out.println("ISBN : " + book.getISBN());
        System.out.println("Author : " + book.getAuthor());
        System.out.println("Publisher : " + book.getPublisher());
        System.out.println("Borrowing Date : " + this.getBorrowDate());
        System.out.println("Due Date : " + this.getDueDate());
    }
}
