package com.user;

import com.connection.ConnectSM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AcademicStaffDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs,rs1,rs2;
    
    public static boolean addNonAcademicStaff(AcademicStaff currAc) {
        try {
            con = ConnectSM.connect();
            String queryA = "INSERT INTO User (userID,name,userName,birthDate,registeredDate,email,phone,address) VALUES (?,?,?,?,?,?,?,?) ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, currAc.getUserID());
            ps.setString(2, currAc.getName());
            ps.setString(3, currAc.getUserName());
            ps.setString(4, currAc.getBirthDate());
            ps.setString(5, currAc.getRegisteredDate());
            ps.setString(6, currAc.getEmail());
            ps.setString(7, currAc.getPhone());
            ps.setString(8, currAc.getAddress());
            
            int countA = ps.executeUpdate();
            
            if(countA > 0) {
                try {
                    String queryB = "INSERT INTO AcademicStaff (userID,designation,qualification) VALUES (?,?,?) ;" ;
                    ps = con.prepareStatement(queryB);
                    ps.setString(1, currAc.getUserID());
                    ps.setString(2, currAc.getDesignation());
                    ps.setString(3, currAc.getQualification());
                    
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

    public static ObservableList<?> getStaff(String grade, String subject) {
        ObservableList<AcademicStaff> list = FXCollections.observableArrayList();
        String subjectID = "";
        String acstaffID = "";
        
        try {
            con = ConnectSM.connect();
            String queryC = "SELECT subjectID FROM Subject WHERE subject = ? AND grade = ? ;" ;
            ps = con.prepareStatement(queryC);
            ps.setString(1, subject);
            ps.setString(2, grade);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                subjectID = rs.getString(1);
                
                String queryD = "SELECT userID FROM AcademicTeachSubjects WHERE subjectID = ? ;" ;
                ps = con.prepareStatement(queryD);
                ps.setString(1, subjectID);
                rs1 = ps.executeQuery();

                while(rs1.next()) {
                    acstaffID = rs1.getString(1);
                
                    String queryE = "SELECT u.*,acs.designation,acs.qualification,acs.availability "
                            + "FROM User u,AcademicStaff acs "
                            + "WHERE u.userID = acs.userID AND u.userID = ? ;" ;
                    ps = con.prepareStatement(queryE);
                    ps.setString(1, acstaffID);
                    rs2 = ps.executeQuery();

                    while(rs2.next()) {
                        AcademicStaff teacher = new AcademicStaff();

                            teacher.setUserID(rs2.getString(1));
                            teacher.setName(rs2.getString(2));
                            teacher.setUserName(rs2.getString(3));
                            teacher.setPassword(rs2.getString(4));
                            teacher.setBirthDate(rs2.getString(5));
                            teacher.setRegisteredDate(rs2.getString(6));
                            teacher.setEmail(rs2.getString(7));
                            teacher.setPhone(rs2.getString(8));
                            teacher.setAddress(rs2.getString(9));
                            teacher.setBio(rs2.getString(10));
                            teacher.setImg(rs2.getString(11));
                            teacher.setStatus(rs2.getString(12));
                            teacher.setDesignation(rs2.getString(13));
                            teacher.setQualification(rs2.getString(14));
                            teacher.setAvailability(rs2.getString(15));

                        list.add(teacher);
                    }
                }
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs2 != null) try { rs2.close(); } catch(Exception e) {}
            if(rs1 != null) try { rs1.close(); } catch(Exception e) {}
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static ObservableList<?> getAcademicList(String keyword) {
        ObservableList<AcademicStaff> list = FXCollections.observableArrayList();
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
                String queryX = "SELECT u.*,acs.designation,acs.qualification "
                            + "FROM User u,AcademicStaff acs "
                            + "WHERE u.userID = acs.userID AND u.userID = ? ;" ;
                ps = con.prepareStatement(queryX);
                ps.setString(1, userID);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    AcademicStaff teacher = new AcademicStaff();

                        teacher.setUserID(rs1.getString(1));
                        teacher.setName(rs1.getString(2));
                        teacher.setUserName(rs1.getString(3));
                        teacher.setPassword(rs1.getString(4));
                        teacher.setBirthDate(rs1.getString(5));
                        teacher.setRegisteredDate(rs1.getString(6));
                        teacher.setEmail(rs1.getString(7));
                        teacher.setPhone(rs1.getString(8));
                        teacher.setAddress(rs1.getString(9));
                        teacher.setBio(rs1.getString(10));
                        teacher.setImg(rs1.getString(11));
                        teacher.setStatus(rs1.getString(12));
                        teacher.setDesignation(rs1.getString(13));
                        teacher.setQualification(rs1.getString(14));

                    list.add(teacher);
                }
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle + "aca");
        }
        finally {
            if(rs1 != null) try { rs1.close(); } catch(Exception e) {}
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }

    public static ObservableList<String> getTeachers() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String userID = "";
        String name = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT userID FROM AcademicStaff ;" ;
            ps = con.prepareStatement(queryA);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
                
                String queryB = "SELECT name FROM User WHERE userID = ? ;" ;
                ps = con.prepareStatement(queryB);
                ps.setString(1, userID);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    name = rs1.getString(1);
                    
                    list.add(name);
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

    public static int getAvailability(String teacher) {
        String userID = "";
        int subCount = 0;
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT userID FROM User WHERE name = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, teacher);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
                
                String queryB = "SELECT COUNT(subjectID) FROM AcademicTeachSubjects WHERE userID = ? ;" ;
                ps = con.prepareStatement(queryB);
                ps.setString(1, userID);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    subCount = rs1.getInt(1);
                }
            }
            
            return subCount;
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
        
        return subCount;
    }

    public static String getTeacherID(String teacher) {
        String userID = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT userID FROM User WHERE name = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, teacher);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
            }
            
            return userID;
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

    public static int getStaffCount(String subjectID) {
        int countStaff = 0;
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT COUNT(userID) FROM AcademicTeachSubjects WHERE subjectID = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, subjectID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                countStaff = rs.getInt(1);
            }
            
            return countStaff;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return countStaff;
    }




}
