import java.util.ArrayList;
import java.util.List;

public class Book {
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private String category;
    private String year;
    private int copiesQuantity;
    private String status;
    private List<BookCopy> copies;

    //Constructor
    public Book(String title, String author, String publisher, String ISBN, String category,
                int copiesQuantity, String status, String year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.category = category;
        this.year = year;
        this.copiesQuantity = copiesQuantity;
        this.status = status;
        this.copies = new ArrayList<>();
    }

    //Lấy tiêu đề
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCopiesQuantity() {
        return copiesQuantity;
    }

    public void setCopiesQuantity(int copiesQuantity) {
        this.copiesQuantity = copiesQuantity;
    }

    public List<BookCopy> getCopies() {
        return copies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    //Thêm 1 bản copy
    public void addNewCopy(BookCopy newCopy) {
        this.copies.add(newCopy);
        this.copiesQuantity = this.copies.size();
    }



    //Lấy về bản copy với copyID của quyển sách
    public BookCopy getCopyByID(int copyID) {
        for (BookCopy copy : copies) {
            if (copy.getCopyID() == copyID) {
                return copy;
            }
        }
        return null;
    }

    //In ra tất cả bản copy và trạng thái tương ứng của quyển sách
    public void displayAllCopies() {
        //System.out.println(this.toString());
        for (BookCopy copy : copies) {
            System.out.println(copy.toString());
        }
    }

    //Xóa 1 bản copy (nếu mất/ hỏng...)
    public void removeCopy(BookCopy copy) {
        if(copy != null) {
            System.out.println("Copy with id : " + copy.getCopyID() + " has been removed.");
            copies.remove(copy);
        } else {
            System.out.println("Action failed");
        }
    }

    @Override
    public String toString() {
        return String.format("%-15s %-70s %-25s %-35s %-35s %-10s %-10s %-10s%n",
                this.getISBN(), this.getTitle(), this.getAuthor(), this.getPublisher(), this.getCategory(),
                this.getYear(), this.getCopiesQuantity(), this.getStatus());
    }

}
