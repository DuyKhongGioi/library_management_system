import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

public class Library {
    private static final String name = "ZLibrary";
    private static final String address = "144 Xuan Thuy, Cau Giay, Ha Noi, Viet Nam";
    private static final String dataPath = "Books.db";
    protected List<Book> books;
    protected List<Member> members;
    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        loadBooksFromDatabase();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }


    // Kiểm tra ISBN đã tồn tại trong cơ sở dữ liệu hay chưa
    private boolean isISBNExists(Connection conn, String isbn) {
        String checkSql = "SELECT 1 FROM Books WHERE ISBN = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Trả về true nếu tìm thấy
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra ISBN: " + e.getMessage());
        }
        return false;
    }

    //Tải sách từ database
    private void loadBooksFromDatabase() {
        // Lấy đường dẫn thực tế đến file database từ thư mục resources
        String url = null;
        try {
            // Sử dụng ClassLoader để lấy đường dẫn đến file trong resources
            url = "jdbc:sqlite:" + getClass().getClassLoader().getResource(dataPath).getPath();
        } catch (NullPointerException e) {
            System.out.println("Không tìm thấy file database trong thư mục resources.");
            return;
        }

        String sql = "SELECT * FROM Books"; // Giả sử bảng sách có tên là 'books'

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Duyệt qua từng dòng trong kết quả truy vấn
            while (rs.next()) {
                // Tạo đối tượng Book từ dữ liệu database
                String ISBN = rs.getString("ISBN");
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                String publisher = rs.getString("Publisher");
                String category = rs.getString("Category");
                String year = rs.getString("Year");
                int copies = rs.getInt("copiesQuantity");
                String status = rs.getString("Status");

                Book book = new Book(title, author, publisher, ISBN, category,
                        copies, status, year);
                this.books.add(book); // Thêm sách vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tải sách từ cơ sở dữ liệu: " + e.getMessage());
        }
    }

    //Add 1 quyển vào database
    public void insertBook(Book book) {
        String url = "jdbc:sqlite:" + getClass().getClassLoader().getResource(dataPath).getPath(); // Đường dẫn tới file Books.db

        // Câu lệnh SQL để chèn một sách vào bảng Books
        String sql = "INSERT INTO Books (ISBN, title, author, publisher, category, year, copiesQuantity, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Thiết lập các giá trị cho câu lệnh INSERT
            pstmt.setString(1, book.getISBN());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setString(5, book.getCategory());
            pstmt.setString(6, book.getYear());
            pstmt.setInt(7, book.getCopiesQuantity());
            pstmt.setString(8, book.getStatus());

            // Thực hiện câu lệnh INSERT
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Đã chèn sách thành công với ISBN: " + book.getISBN());
            } else {
                System.out.println("Không thể chèn sách với ISBN: " + book.getISBN());
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi chèn sách vào cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Phương thức để xóa một quyển sách khỏi cơ sở dữ liệu theo ISBN
    public void deleteBookByISBN(String isbn) {
        String url = "jdbc:sqlite:" + getClass().getClassLoader().getResource(dataPath).getPath(); // Đường dẫn tới file Books.db

        // Câu lệnh SQL để xóa một sách khỏi bảng Books theo ISBN
        String sql = "DELETE FROM Books WHERE ISBN = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Thiết lập giá trị cho câu lệnh DELETE
            pstmt.setString(1, isbn);

            // Thực hiện câu lệnh DELETE
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Đã xóa sách thành công với ISBN: " + isbn);
            } else {
                System.out.println("Không tìm thấy sách với ISBN: " + isbn);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa sách khỏi cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Phương thức để cập nhật một thuộc tính cụ thể của sách trong cơ sở dữ liệu
    public void updateBookField(String isbn, String fieldName, Object newValue) {
        String url = "jdbc:sqlite:" + getClass().getClassLoader().getResource(dataPath).getPath(); // Đường dẫn tới file Books.db

        // Câu lệnh SQL để cập nhật một trường cụ thể của sách trong bảng Books theo ISBN
        String sql = "UPDATE Books SET " + fieldName + " = ? WHERE ISBN = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Thiết lập giá trị mới cho câu lệnh UPDATE
            pstmt.setObject(1, newValue);
            pstmt.setString(2, isbn);

            // Thực hiện câu lệnh UPDATE
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Đã cập nhật thành công " + fieldName + " cho ISBN: " + isbn);
            } else {
                System.out.println("Không tìm thấy sách với ISBN: " + isbn);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật " + fieldName + " trong cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Thêm sách
    public void addBook(Book book) {
        if(book != null) {
            this.books.add(book);
            this.insertBook(book);
            System.out.println("Book added.");
        } else {
            System.out.println("Action failed.");
        }

    }
    //Thêm số lượng bản copy có sẵn cho sách
    public void addCopies(Book book, int copies) {
        for (int i = 0; i < copies; i++) {
            BookCopy copy = new BookCopy(book);
            book.addCopy(copy);
        }
        book.setCopiesQuantity(copies);
        System.out.println("Book's copies have been added to library.");
    }

    //Xóa sách
    public void removeBook(Book book) {
        if(book != null) {
            this.books.remove(book);
            this.deleteBookByISBN(book.getISBN());
            System.out.println("Book removed.");
        } else {
            System.out.println("Action failed.");
        }
    }

    //Thêm thành viên
    public void addMember(Member member) {
        if (member != null) {
            this.members.add(member);
            System.out.println("Member added.");
        } else {
            System.out.println("Action failed.");
        }
    }

    //Xóa thành viên
    public void removeMember(Member member) {
        this.members.remove(member);
        System.out.println("Member removed : " + member.getName());
    }

}
