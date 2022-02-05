package com.subject;

import com.connection.ConnectSM;
import com.string.Strings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubjectDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs,rs1,rs2;
    
    public static ObservableList<Subject> loadSubjects(String grade) {
        ObservableList<Subject> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT * FROM Subject WHERE grade = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, grade);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Subject subject = new Subject();
                
                subject.setSubjectID(rs.getString(1));
                subject.setSubject(rs.getString(2));
                subject.setGrade(rs.getString(3));
                
                list.add(subject);
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

    public static String getSubjectID(String grade, String subject) {
        String subjectID = "";
                
        try {
            con = ConnectSM.connect();
            String queryB = "SELECT subjectID FROM Subject WHERE grade = ? AND subject = ? ;" ;
            ps = con.prepareStatement(queryB);
            ps.setString(1, grade);
            ps.setString(2, subject);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                subjectID = rs.getString(1);
            }
            
            return subjectID;
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

    public static String getSubjectName(String grade, String subjectID) {
        String subject = "";
                
        try {
            con = ConnectSM.connect();
            String queryB = "SELECT subject FROM Subject WHERE grade = ? AND subjectID = ? ;" ;
            ps = con.prepareStatement(queryB);
            ps.setString(1, grade);
            ps.setString(2, subjectID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                subject = rs.getString(1);
            }
            
            return subject;
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
    
    public static String returnID() {
        try {
            con = ConnectSM.connect();
            String prevID = "";
            String queryC = "SELECT MAX(subjectID) FROM Subject ;" ;
            ps = con.prepareStatement(queryC);
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

    public static boolean addSubject(Subject currSubject) {
        try {
            con = ConnectSM.connect();
            String queryD = "INSERT INTO Subject(subjectID,subject,grade) VALUES (?,?,?);" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, currSubject.getSubjectID());
            ps.setString(2, currSubject.getSubject());
            ps.setString(3, currSubject.getGrade());
            
            int count = ps.executeUpdate();
            
            return count > 0;
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

    public static ObservableList<String> getSubjects() {
        ObservableList<String> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryE = "SELECT DISTINCT subject FROM Subject ;" ;
            ps = con.prepareStatement(queryE);
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
    
    public static ObservableList<Subject> loadSubjectsTable(String grade) {
        ObservableList<Subject> list = FXCollections.observableArrayList();
        String subjectID = "";
        String userID = "";
        String teacher = "";
        
        try {
            con = ConnectSM.connect();
            String queryF = "SELECT * FROM Subject WHERE grade = ? ;" ;
            ps = con.prepareStatement(queryF);
            ps.setString(1, grade);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Subject subject = new Subject();
                
                subject.setSubjectID(rs.getString(1));
                subject.setSubject(rs.getString(2));
                subject.setGrade(rs.getString(3));
                
                    subjectID = rs.getString(1);
                    String queryG = "SELECT userID FROM AcademicTeachSubjects WHERE subjectID = ? ;" ;
                    ps = con.prepareStatement(queryG);
                    ps.setString(1, subjectID);
                    rs1 = ps.executeQuery();
                    
                    while(rs1.next()) {
                        userID = rs1.getString(1);
                        
                        String queryH = "SELECT name FROM User WHERE userID = ? ;" ;
                        ps = con.prepareStatement(queryH);
                        ps.setString(1, userID);
                        rs2 = ps.executeQuery();
                        
                        while(rs2.next()) {
                            teacher = rs2.getString(1);
                            
                            subject.setTeacher(teacher);
                        }
                    }
                    
                list.add(subject);
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

    public static void setCurrentSubject(String subjectID) {
        try {
            con = ConnectSM.connect();
            String queryR = "SELECT * FROM Subject WHERE subjectID = ? ;" ;
            ps = con.prepareStatement(queryR);
            ps.setString(1, subjectID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Subject currUser = new Subject();
                
                currUser.setSubjectID(rs.getString(1));
                currUser.setSubject(rs.getString(2));
                currUser.setGrade(rs.getString(3));
                
                System.out.println(Strings.savedCurrentSubject);
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

    public static boolean updateSubject(Subject subjectToUp) {
        try {
            con = ConnectSM.connect();
            String queryD = "UPDATE Subject SET subject = ?, grade = ? WHERE subjectID = ? ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, subjectToUp.getSubject());
            ps.setString(2, subjectToUp.getGrade());
            ps.setString(3, subjectToUp.getSubjectID());
            
            int count = ps.executeUpdate();
            if(count > 0)
                return true;
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

    public static void updateTeacherSubject(String subjectID, String teacherID, String todayDate) {
        try {
            con = ConnectSM.connect();
            String queryD = "UPDATE AcademicTeachSubjects SET userID = ?, assignedDate = ? WHERE subjectID = ? ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, teacherID);
            ps.setString(2, todayDate);
            ps.setString(3, subjectID);
            int count = ps.executeUpdate();
            
            if(count > 0)
                System.out.println(Strings.assignedTeacherSubject);
            else
                System.out.println(Strings.notAssignedTeacherSubject);
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
    }

    public static void insertTeacherSubject(String subjectID, String teacherID, String todayDate) {
        try {
            con = ConnectSM.connect();
            String queryD = "INSERT INTO AcademicTeachSubjects(userID,subjectID,assignedDate) VALUES(?,?,?) ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, teacherID);
            ps.setString(2, subjectID);
            ps.setString(3, todayDate);
            int count = ps.executeUpdate();
            
            if(count > 0)
                System.out.println(Strings.assignedTeacherSubject);
            else
                System.out.println(Strings.notAssignedTeacherSubject);
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
    }
    
    
}
