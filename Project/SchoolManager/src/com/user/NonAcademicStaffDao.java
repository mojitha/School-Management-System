package com.user;

import com.connection.ConnectSM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NonAcademicStaffDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs,rs1;
    
    public static NonAcademicStaff getNonAcademicStaff(NonAcademicStaff user) {
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT u.*,ns.designation,ns.qualification FROM User u,NonAcademicStaff ns WHERE u.userID = ns.userID AND u.userID = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, user.getUserID());
            rs = ps.executeQuery();
            
            while(rs.next()) {
                user.setUserID(rs.getString(1));
                user.setName(rs.getString(2));
                user.setUserName(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setBirthDate(rs.getString(5));
                user.setRegisteredDate(rs.getString(6));
                user.setEmail(rs.getString(7));
                user.setPhone(rs.getString(8));
                user.setAddress(rs.getString(9));
                user.setBio(rs.getString(10));
                user.setImg(rs.getString(11));
                user.setStatus(rs.getString(12));
                user.setDesignation(rs.getString(13));
                user.setQualification(rs.getString(14));
            }
            
            return user;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static boolean addNonAcademicStaff(NonAcademicStaff currNonac) {
        try {
            con = ConnectSM.connect();
            String queryB = "INSERT INTO User (userID,name,userName,birthDate,registeredDate,email,phone,address) VALUES (?,?,?,?,?,?,?,?) ;" ;
            ps = con.prepareStatement(queryB);
            ps.setString(1, currNonac.getUserID());
            ps.setString(2, currNonac.getName());
            ps.setString(3, currNonac.getUserName());
            ps.setString(4, currNonac.getBirthDate());
            ps.setString(5, currNonac.getRegisteredDate());
            ps.setString(6, currNonac.getEmail());
            ps.setString(7, currNonac.getPhone());
            ps.setString(8, currNonac.getAddress());
            
            int countA = ps.executeUpdate();
            
            if(countA > 0) {
                try {
                    String queryC = "INSERT INTO NonAcademicStaff (userID,designation,qualification) VALUES (?,?,?) ;" ;
                    ps = con.prepareStatement(queryC);
                    ps.setString(1, currNonac.getUserID());
                    ps.setString(2, currNonac.getDesignation());
                    ps.setString(3, currNonac.getQualification());
                    
                    int countB = ps.executeUpdate();
                    
                    if(countB > 0)
                        return true;
                }
                catch(SQLException sqle) {
                    System.out.println(sqle);
                }
            }
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }

    public static ObservableList<NonAcademicStaff> loadNonAcadamic() {
        ObservableList<NonAcademicStaff> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT u.*,nas.designation,nas.qualification FROM User u,NonAcademicStaff nas WHERE u.userID = nas.userID ;" ;
            ps = con.prepareStatement(queryA);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                NonAcademicStaff nonac = new NonAcademicStaff();
                
                nonac.setUserID(rs.getString(1));
                nonac.setName(rs.getString(2));
                nonac.setUserName(rs.getString(3));
                nonac.setPassword(rs.getString(4));
                nonac.setBirthDate(rs.getString(5));
                nonac.setRegisteredDate(rs.getString(6));
                nonac.setEmail(rs.getString(7));
                nonac.setPhone(rs.getString(8));
                nonac.setAddress(rs.getString(9));
                nonac.setBio(rs.getString(10));
                nonac.setImg(rs.getString(11));
                nonac.setStatus(rs.getString(12));
                nonac.setDesignation(rs.getString(13));
                nonac.setQualification(rs.getString(14));
                
                list.add(nonac);
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

    public static ObservableList<?> getNonAcademicList(String keyword) {
        ObservableList<NonAcademicStaff> list = FXCollections.observableArrayList();
        String userID = "";
        
        try {
            con = ConnectSM.connect();
            String queryB = "SELECT userID FROM User WHERE userID LIKE ? OR "
                                                    + "name LIKE ? OR "
                                                    + "userName LIKE ? OR "
                                                    + "birthDate LIKE ? OR "
                                                    + "registeredDate LIKE ? OR "
                                                    + "email LIKE ? OR "
                                                    + "phone LIKE ? OR "
                                                    + "address LIKE ? ;" ;
            ps = con.prepareStatement(queryB);
            
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
                userID = rs.getString(1);
                
                String queryA = "SELECT u.*,nas.designation,nas.qualification FROM User u,NonAcademicStaff nas WHERE u.userID = nas.userID AND u.userID = ? ;" ;
                ps = con.prepareStatement(queryA);
                ps.setString(1, userID);
                rs1 = ps.executeQuery();
            
                while(rs1.next()) {
                    NonAcademicStaff nonac = new NonAcademicStaff();

                    nonac.setUserID(rs1.getString(1));
                    nonac.setName(rs1.getString(2));
                    nonac.setUserName(rs1.getString(3));
                    nonac.setPassword(rs1.getString(4));
                    nonac.setBirthDate(rs1.getString(5));
                    nonac.setRegisteredDate(rs1.getString(6));
                    nonac.setEmail(rs1.getString(7));
                    nonac.setPhone(rs1.getString(8));
                    nonac.setAddress(rs1.getString(9));
                    nonac.setBio(rs1.getString(10));
                    nonac.setImg(rs1.getString(11));
                    nonac.setStatus(rs1.getString(12));
                    nonac.setDesignation(rs1.getString(13));
                    nonac.setQualification(rs1.getString(14));

                    list.add(nonac);
                }
            }   
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs1 != null) try { rs1.close(); } catch(Exception e) {}
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
}
