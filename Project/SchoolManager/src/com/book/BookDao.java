package com.book;

import com.connection.ConnectSM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs;
    
    // gets last bookID
    public static String returnID() {
        try {
            con = ConnectSM.connect();
            String prevID = "";
            String queryA = "SELECT MAX(resourceID) FROM Book ;" ;
            ps = con.prepareStatement(queryA);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                prevID = rs.getString(1);
            }
            
            return prevID;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static ObservableList getCategories() {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryB = "SELECT bcategory FROM BookCategories ;" ;
            ps = con.prepareStatement(queryB);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    // save the new Book
    public static boolean addBook(Book currBook) {
        try {
            con = ConnectSM.connect();
            String queryC = "INSERT INTO Book (resourceID,title,language,category,author,price,updatedOn,copies) VALUES (?,?,?,?,?,?,?,?);" ;
            ps = con.prepareStatement(queryC);
            ps.setString(1, currBook.getResourceID());
            ps.setString(2, currBook.getTitle());
            ps.setString(3, currBook.getLanguage());
            ps.setString(4, currBook.getCategory());
            ps.setString(5, currBook.getAuthor());
            ps.setString(6, currBook.getPrice());
            ps.setString(7, currBook.getUpdatedOn());
            ps.setString(8, currBook.getCopies());
            
            int count = ps.executeUpdate();
            
            return count > 0;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }

    public static ObservableList<String> getLanguage() {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryD = "SELECT DISTINCT(language) FROM Book ;" ;
            ps = con.prepareStatement(queryD);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(rs.getString(1));
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    // loads last Book in the Book table
    public static ObservableList<Book> getLastBook() {
        ObservableList<Book> list = FXCollections.observableArrayList();
        String lastBookID = "";
        
        try {
            con = ConnectSM.connect();
            String queryE = "SELECT MAX(resourceID) FROM Book ;" ;
            ps = con.prepareStatement(queryE);
            rs = ps.executeQuery();
            
            while(rs.next()) {
               lastBookID = rs.getString(1);
            }
            
            try {
                String queryF = "SELECT * FROM Book WHERE resourceID = ? ;" ;
                ps = con.prepareStatement(queryF);
                ps.setString(1, lastBookID);
                rs = ps.executeQuery();

                while(rs.next()) {
                   Book book = new Book();
                   
                    book.setResourceID(rs.getString(1));
                    book.setTitle(rs.getString(2));
                    book.setLanguage(rs.getString(3));
                    book.setCategory(rs.getString(4));
                    book.setAuthor(rs.getString(5));
                    book.setPrice(rs.getString(6));
                    book.setUpdatedOn(rs.getString(7));
                    book.setCopies(rs.getString(8));
                    
                    list.add(book);
                }
                
                return list;
            }
            catch(SQLException sqle) {
                System.out.println(sqle);
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static boolean removeLast() {
        try {
            con = ConnectSM.connect();
            String queryG = "SELECT MAX(resourceID) FROM Book ;" ;
            ps = con.prepareStatement(queryG);
            rs = ps.executeQuery();
            
            String bookID = null;
            
            while(rs.next())
                bookID = rs.getString(1);
            
            try {
                String queryH = "DELETE FROM Book WHERE resourceID = ? ;" ;
                ps = con.prepareStatement(queryH);
                ps.setString(1, bookID);
                
                int count = ps.executeUpdate();
                
                if(count > 0)
                    return true;
            }
            catch(SQLException sqle) {
                System.out.println(sqle);
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }

    public static ObservableList<Book> loadBooks(String category, String price, String language, String copies) {
        ObservableList<Book> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryI = "SELECT * FROM Book WHERE category LIKE ? OR price LIKE ? OR language LIKE ? OR copies LIKE ? ;" ;
            ps = con.prepareStatement(queryI);
            ps.setString(1, category);
            ps.setString(2, price);
            ps.setString(3, language);
            ps.setString(4, copies);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Book book = new Book();
                
                book.setResourceID(rs.getString(1));
                book.setTitle(rs.getString(2));
                book.setLanguage(rs.getString(3));
                book.setCategory(rs.getString(4));
                book.setAuthor(rs.getString(5));
                book.setPrice(rs.getString(6));
                book.setUpdatedOn(rs.getString(7));
                book.setCopies(rs.getString(8));
                
                list.add(book);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static ObservableList<Book> getBookList(String keyword) {
        ObservableList<Book> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryJ = "SELECT * FROM Book WHERE resourceID LIKE ? OR "
                                + "title LIKE ? OR "
                                + "language LIKE ? OR "
                                + "category LIKE ? OR "
                                + "author LIKE ? OR "
                                + "price LIKE ? OR "
                                + "updatedOn LIKE ? OR "
                                + "copies LIKE ? ;" ;
            ps = con.prepareStatement(queryJ);
            
            keyword = "%"+keyword+"%";
            
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
            ps.setString(5, keyword);
            ps.setString(6, keyword);
            ps.setString(7, keyword);
            ps.setString(8, keyword);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Book book = new Book();
                
                book.setResourceID(rs.getString(1));
                book.setTitle(rs.getString(2));
                book.setLanguage(rs.getString(3));
                book.setCategory(rs.getString(4));
                book.setAuthor(rs.getString(5));
                book.setPrice(rs.getString(6));
                book.setUpdatedOn(rs.getString(7));
                book.setCopies(rs.getString(8));
                
                list.add(book);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static String getBooksCount() {
        String booksCount = "";
        
        try {
            con = ConnectSM.connect();
            String queryK = "SELECT count(resourceID) FROM Book ;" ;
            ps = con.prepareStatement(queryK);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                booksCount = rs.getInt(1)+"";
                
                return booksCount;
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static String getBooksCostPerMonth(String monthInt, String year) {
        String booksPrice = "";
        String dateX = "%"+year+"-"+monthInt+"%";
        
        try {
            con = ConnectSM.connect();
            String queryL = "SELECT SUM(price) FROM Book WHERE updatedOn LIKE ? ;" ;
            ps = con.prepareStatement(queryL);
            ps.setString(1, dateX);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                booksPrice = rs.getString(1);
                
                return booksPrice;
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    
    
    
}
