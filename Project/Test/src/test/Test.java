package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
   
class Conn {
    public static Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolmanager","root","");
            
            System.out.println("Database connection successful");
            return con;
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return null;
    }
}

public class Test {
    public static void main(String[] args) throws SQLException {
        Connection con = Conn.createConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String queryA = "SELECT * FROM Item ;" ; 
        rs = con.prepareStatement(queryA).executeQuery();
        
        while(rs.next())
            System.out.println(rs.getString(2));
    }
}