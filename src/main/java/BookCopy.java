public class BookCopy {
    private static int iDGenerator = 0;
    private int copyID;
    private String status;
    private Book book;

    public BookCopy(Book book) {
        this.copyID = iDGenerator++;
        this.status = "Available";
        this.book = book;
    }

    public int getCopyID() {
        return copyID;
    }

    public void setCopyID(int copyID) {
        this.copyID = copyID;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    //Kiểm tra nếu bản copy bị hỏng hóc thì xóa đi
    public boolean isNotAvailable() {
        return status.equals("No longer available");
    }

    public void updateStatus(String status) {
        this.status = status;
        System.out.println("This copy is now " + status);
    }
}
