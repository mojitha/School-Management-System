package com.staff;

import com.connection.ConnectSM;
import com.string.Strings;
import com.user.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static String returnID() {
        try {
            con = ConnectSM.connect();
            String prevID = "";
            String queryA = "SELECT MAX(userID) FROM User ;" ;
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

    public static void setCurrentStaffMember(String userID) {
        try {
            con = ConnectSM.connect();
            String queryR = "SELECT * FROM User WHERE userID = ? ;" ;
            ps = con.prepareStatement(queryR);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                User currUser = new User();
                
                currUser.setUserID(rs.getString(1));
                currUser.setName(rs.getString(2));
                currUser.setUserName(rs.getString(3));
                currUser.setPassword(rs.getString(4));
                currUser.setBirthDate(rs.getString(5));
                currUser.setRegisteredDate(rs.getString(6));
                currUser.setEmail(rs.getString(7));
                currUser.setPhone(rs.getString(8));
                currUser.setAddress(rs.getString(9));
                currUser.setBio(rs.getString(10));
                currUser.setImg(rs.getString(11));
                currUser.setStatus(rs.getString(12));
                
                System.out.println(Strings.savedCurrentUser);
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
    }
    
    
}
