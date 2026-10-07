import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Register Member");
            System.out.println("7. View Members");
            System.out.println("8. Issue Book");
            System.out.println("9. Return Book");
            System.out.println("10. View Issued Books");
            System.out.println("11. Exit");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addBook(sc);
                    break;

                case 2:
                    viewBooks();
                    break;

                case 3:
                    searchBook(sc);
                    break;

                case 4:
                    updateBook(sc);
                    break;

                case 5:
                    deleteBook(sc);
                    break;

                case 6:
                    registerMember(sc);
                    break;

                case 7:
                    viewMembers();
                    break;

                case 8:
                    issueBook(sc);
                    break;

                case 9:
                    returnBook(sc);
                    break;

                case 10:
                    viewIssuedBooks();
                    break;

                case 11:
                    System.out.println("Thank you for using Library Management System.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // ================= ADD BOOK =================

    public static void addBook(Scanner sc) {

        System.out.println("\n===== ADD BOOK =====");

        System.out.print("Enter book title: ");
        String title = sc.nextLine();

        System.out.print("Enter author: ");
        String author = sc.nextLine();

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        try {

            Connection connection = DBConnection.getConnection();

            String query =
                    "INSERT INTO books(title, author, category, quantity) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, quantity);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book added successfully!");
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Error while adding book.");
            e.printStackTrace();
        }
    }


    // ================= VIEW BOOKS =================

    public static void viewBooks() {

        System.out.println("\n===== LIBRARY BOOKS =====");

        try {

            Connection connection = DBConnection.getConnection();

            Statement statement = connection.createStatement();

            ResultSet rs =
                    statement.executeQuery("SELECT * FROM books");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        rs.getInt("book_id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | " +
                        rs.getString("category") + " | Quantity: " +
                        rs.getInt("quantity")
                );
            }

            if (!found) {
                System.out.println("No books found.");
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Error while viewing books.");
            e.printStackTrace();
        }
    }


    // ================= SEARCH BOOK =================

    public static void searchBook(Scanner sc) {

        System.out.println("\n===== SEARCH BOOK =====");

        System.out.print("Enter book title: ");
        String title = sc.nextLine();

        try {

            Connection connection = DBConnection.getConnection();

            String query =
                    "SELECT * FROM books WHERE title LIKE ?";

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setString(1, "%" + title + "%");

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        rs.getInt("book_id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | " +
                        rs.getString("category") + " | Quantity: " +
                        rs.getInt("quantity")
                );
            }

            if (!found) {
                System.out.println("Book not found.");
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Error while searching book.");
            e.printStackTrace();
        }
    }


    // ================= UPDATE BOOK =================

    public static void updateBook(Scanner sc) {

        System.out.println("\n===== UPDATE BOOK =====");

        System.out.print("Enter book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new title: ");
        String title = sc.nextLine();

        System.out.print("Enter new author: ");
        String author = sc.nextLine();

        System.out.print("Enter new category: ");
        String category = sc.nextLine();

        System.out.print("Enter new quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        try {

            Connection connection = DBConnection.getConnection();

            String query =
                    "UPDATE books SET title=?, author=?, " +
                    "category=?, quantity=? WHERE book_id=?";

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, quantity);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book updated successfully!");
            } else {
                System.out.println("Book ID not found.");
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Error while updating book.");
            e.printStackTrace();
        }
    }


    // ================= DELETE BOOK =================

    public static void deleteBook(Scanner sc) {

        System.out.println("\n===== DELETE BOOK =====");

        System.out.print("Enter book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {

            Connection connection = DBConnection.getConnection();

            String query =
                    "DELETE FROM books WHERE book_id=?";

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book deleted successfully!");
            } else {
                System.out.println("Book ID not found.");
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Error while deleting book.");
            e.printStackTrace();
        }
    }


    // ================= REGISTER MEMBER =================

    public static void registerMember(Scanner sc) {

        System.out.println("\n===== REGISTER MEMBER =====");

        System.out.print("Enter member name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter address: ");
        String address = sc.nextLine();

        try {

            Connection connection = DBConnection.getConnection();

            String query =
                    "INSERT INTO members(name, email, phone, address) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, address);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Member registered successfully!");
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Error while registering member.");
            e.printStackTrace();
        }
    }


    // ================= VIEW MEMBERS =================

    public static void viewMembers() {

        System.out.println("\n===== REGISTERED MEMBERS =====");

        try {

            Connection connection = DBConnection.getConnection();

            Statement statement = connection.createStatement();

            ResultSet rs =
                    statement.executeQuery("SELECT * FROM members");

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        rs.getInt("member_id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("email") + " | " +
                        rs.getString("phone") + " | " +
                        rs.getString("address")
                );
            }

            if (!found) {
                System.out.println("No members registered.");
            }

            connection.close();

        } catch (Exception e) {
            System.out.println("Error while viewing members.");
            e.printStackTrace();
        }
    }


    // ================= ISSUE BOOK =================

    public static void issueBook(Scanner sc) {

        System.out.println("\n===== ISSUE BOOK =====");

        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();
        sc.nextLine();

        try {

            Connection connection = DBConnection.getConnection();

            // Check book
            String bookQuery =
                    "SELECT quantity FROM books WHERE book_id=?";

            PreparedStatement bookPs =
                    connection.prepareStatement(bookQuery);

            bookPs.setInt(1, bookId);

            ResultSet bookRs =
                    bookPs.executeQuery();

            if (!bookRs.next()) {

                System.out.println("Book ID not found.");
                connection.close();
                return;
            }

            int quantity =
                    bookRs.getInt("quantity");

            if (quantity <= 0) {

                System.out.println("Book is not available.");
                connection.close();
                return;
            }


            // Check member
            String memberQuery =
                    "SELECT member_id FROM members WHERE member_id=?";

            PreparedStatement memberPs =
                    connection.prepareStatement(memberQuery);

            memberPs.setInt(1, memberId);

            ResultSet memberRs =
                    memberPs.executeQuery();

            if (!memberRs.next()) {

                System.out.println("Member ID not found.");
                connection.close();
                return;
            }


            // Issue book
            String issueQuery =
                    "INSERT INTO issued_books " +
                    "(book_id, member_id, issue_date, status, fine) " +
                    "VALUES (?, ?, CURDATE(), 'ISSUED', 0)";

            PreparedStatement issuePs =
                    connection.prepareStatement(issueQuery);

            issuePs.setInt(1, bookId);
            issuePs.setInt(2, memberId);

            issuePs.executeUpdate();


            // Reduce quantity
            String updateQuery =
                    "UPDATE books " +
                    "SET quantity = quantity - 1 " +
                    "WHERE book_id=?";

            PreparedStatement updatePs =
                    connection.prepareStatement(updateQuery);

            updatePs.setInt(1, bookId);

            updatePs.executeUpdate();

            System.out.println("Book issued successfully!");

            connection.close();

        } catch (Exception e) {

            System.out.println("Error while issuing book.");
            e.printStackTrace();
        }
    }


    // ================= RETURN BOOK + FINE =================

    public static void returnBook(Scanner sc) {

        System.out.println("\n===== RETURN BOOK =====");

        System.out.print("Enter Issue ID: ");
        int issueId = sc.nextInt();
        sc.nextLine();

        try {

            Connection connection = DBConnection.getConnection();

            String query =
                    "SELECT book_id, issue_date, status " +
                    "FROM issued_books WHERE issue_id=?";

            PreparedStatement ps =
                    connection.prepareStatement(query);

            ps.setInt(1, issueId);

            ResultSet rs =
                    ps.executeQuery();

            if (!rs.next()) {

                System.out.println("Issue ID not found.");
                connection.close();
                return;
            }

            int bookId =
                    rs.getInt("book_id");

            java.sql.Date issueDate =
                    rs.getDate("issue_date");

            String status =
                    rs.getString("status");

            if ("RETURNED".equals(status)) {

                System.out.println(
                        "This book has already been returned."
                );

                connection.close();
                return;
            }


            // Calculate borrowed days
            long difference =
                    System.currentTimeMillis()
                    - issueDate.getTime();

            long days =
                    difference /
                    (1000L * 60 * 60 * 24);

            // 14 days allowed
            double fine = 0;

            if (days > 14) {

                long lateDays = days - 14;

                fine = lateDays * 5;
            }


            // Update issue record
            String returnQuery =
                    "UPDATE issued_books SET " +
                    "return_date=CURDATE(), " +
                    "status='RETURNED', " +
                    "fine=? " +
                    "WHERE issue_id=?";

            PreparedStatement returnPs =
                    connection.prepareStatement(returnQuery);

            returnPs.setDouble(1, fine);
            returnPs.setInt(2, issueId);

            returnPs.executeUpdate();


            // Increase book quantity
            String bookUpdate =
                    "UPDATE books " +
                    "SET quantity = quantity + 1 " +
                    "WHERE book_id=?";

            PreparedStatement bookPs =
                    connection.prepareStatement(bookUpdate);

            bookPs.setInt(1, bookId);

            bookPs.executeUpdate();


            System.out.println(
                    "Book returned successfully!"
            );

            System.out.println(
                    "Days borrowed: " + days
            );

            System.out.println(
                    "Fine: ₹" + fine
            );

            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Error while returning book."
            );

            e.printStackTrace();
        }
    }


    // ================= VIEW ISSUED BOOKS =================

    public static void viewIssuedBooks() {

        System.out.println("\n===== ISSUED BOOKS =====");

        try {

            Connection connection =
                    DBConnection.getConnection();

            String query =
                    "SELECT i.issue_id, " +
                    "b.title, " +
                    "m.name, " +
                    "i.issue_date, " +
                    "i.return_date, " +
                    "i.status, " +
                    "i.fine " +
                    "FROM issued_books i " +
                    "JOIN books b ON i.book_id=b.book_id " +
                    "JOIN members m ON i.member_id=m.member_id";

            Statement statement =
                    connection.createStatement();

            ResultSet rs =
                    statement.executeQuery(query);

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println(
                        rs.getInt("issue_id")
                        + " | Book: "
                        + rs.getString("title")
                        + " | Member: "
                        + rs.getString("name")
                        + " | Issue Date: "
                        + rs.getDate("issue_date")
                        + " | Return Date: "
                        + rs.getDate("return_date")
                        + " | Status: "
                        + rs.getString("status")
                        + " | Fine: ₹"
                        + rs.getDouble("fine")
                );
            }

            if (!found) {
                System.out.println("No issued books found.");
            }

            connection.close();

        } catch (Exception e) {

            System.out.println(
                    "Error while viewing issued books."
            );

            e.printStackTrace();
        }
    }
}