package com.exam;

import com.connection.ConnectSM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExamDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs,rs1;

    public static String returnID() {
        try {
            con = ConnectSM.connect();
            String prevID = "";
            String queryA = "SELECT MAX(examID) FROM Exam ;" ;
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

    public static boolean addExam(Exam currExam) {
        try {
            con = ConnectSM.connect();
            String queryB = "INSERT INTO Exam(examID,examDate,grade,subjectID,startTime,endTime,year,term,status) "
                    + "VALUES(?,?,?,?,?,?,?,?,?) ;" ;
            ps = con.prepareStatement(queryB);
            
            ps.setString(1, currExam.getExamID());
            ps.setString(2, currExam.getExamDate());
            ps.setString(3, currExam.getGrade());
            ps.setString(4, currExam.getSubjectID());
            ps.setString(5, currExam.getStartTime());
            ps.setString(6, currExam.getEndTime());
            ps.setString(7, currExam.getYear());
            ps.setString(8, currExam.getTerm());
            ps.setString(9, currExam.getStatus());
            
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

    public static ObservableList<Exam> getExams(String grade, String year, String term) {
        ObservableList<Exam> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryC = "SELECT * FROM Exam WHERE grade = ? AND year = ? AND term = ? ;" ;
            ps = con.prepareStatement(queryC);
            ps.setString(1, grade);
            ps.setString(2, year);
            ps.setString(3, term);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Exam exam = new Exam();
                
                exam.setExamID(rs.getString(1));
                exam.setExamDate(rs.getString(2));
                exam.setGrade(rs.getString(3));
                exam.setSubjectID(rs.getString(4));
                exam.setStartTime(rs.getString(5));
                exam.setEndTime(rs.getString(6));
                exam.setYear(rs.getString(7));
                exam.setTerm(rs.getString(8));
                exam.setStatus(rs.getString(9));
                
                list.add(exam);
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

    public static String getExam(String grade, String subject, String year, String term) {
        String subjectID = "";
        String examID = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT subjectID FROM Subject WHERE subject = ? AND grade = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, subject);
            ps.setString(2, grade);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                subjectID = rs.getString(1);
                
                String queryB = "SELECT examID FROM Exam WHERE subjectID = ? AND grade = ? AND year = ? AND term = ? ;" ;
                ps = con.prepareStatement(queryB);
                ps.setString(1, subjectID);
                ps.setString(2, grade);
                ps.setString(3, year);
                ps.setString(4, term);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    examID = rs1.getString(1);
                }
                
                return examID;
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

    public static String getExamTookCount(String examID, String subjectID) {
        String stuCount = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT count(userID) FROM StudentFaceExams WHERE examID = ? AND subjectID = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, examID);
            ps.setString(2, subjectID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                stuCount = rs.getInt(1)+"";
                
                return stuCount;
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

    public static String getExamPassCount(String examID, String subjectID) {
        String passStuCount = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT count(userID) FROM StudentFaceExams WHERE examID = ? AND subjectID = ? AND result > 35 ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, examID);
            ps.setString(2, subjectID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                passStuCount = rs.getInt(1)+"";
                
                return passStuCount;
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

    public static String getExamMeritCount(String examID, String subjectID) {
        String meritStuCount = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT count(userID) FROM StudentFaceExams WHERE examID = ? AND subjectID = ? AND result > 85 ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, examID);
            ps.setString(2, subjectID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                meritStuCount = rs.getInt(1)+"";
                
                return meritStuCount;
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

    public static String getTotalMarks(String examID, String subjectID) {
        String marksSum = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT SUM(result) FROM StudentFaceExams WHERE examID = ? AND subjectID = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, examID);
            ps.setString(2, subjectID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                marksSum = rs.getString(1);
                
                return marksSum;
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

    public static void setCurrentExam(String examID) {
        Exam currExam = new Exam();
        
        try {
            con = ConnectSM.connect();
            String queryC = "SELECT * FROM Exam WHERE examID = ? ;" ;
            
            ps = con.prepareStatement(queryC);
            ps.setString(1, currExam.getExamID());
            rs = ps.executeQuery();
           
            while(rs.next()) {
                currExam.setExamID(rs.getString(1));
                currExam.setExamDate(rs.getString(2));
                currExam.setGrade(rs.getString(3));
                currExam.setSubjectID(rs.getString(4));
                currExam.setStartTime(rs.getString(5));
                currExam.setEndTime(rs.getString(6));
                currExam.setYear(rs.getString(7));
                currExam.setTerm(rs.getString(8));
                currExam.setStatus(rs.getString(9));
            }
            
        }
        catch(Exception e) {
            System.out.println(e);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
    }

    public static String getExamStatus(String examID) {
        String examStatus = "";
        
        try {
            con = ConnectSM.connect();
            String queryC = "SELECT status FROM Exam WHERE examID = ? ;" ;
            
            ps = con.prepareStatement(queryC);
            ps.setString(1, examID);
            rs = ps.executeQuery();
           
            while(rs.next()) {
                examStatus = rs.getString(1);
            }
            
            return examStatus;
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

    public static boolean updateExam(Exam currExam) {
        try {
            con = ConnectSM.connect();
            String queryD = "UPDATE Exam SET subjectID = ?, grade = ?, year = ?, term = ?, startTime = ?, endTime = ?, examDate = ? WHERE examID = ? ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, currExam.getSubjectID());
            ps.setString(2, currExam.getGrade());
            ps.setString(3, currExam.getYear());
            ps.setString(4, currExam.getTerm());
            ps.setString(5, currExam.getStartTime());
            ps.setString(6, currExam.getEndTime());
            ps.setString(7, currExam.getExamDate());
            ps.setString(8, currExam.getExamID());
            
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

    public static boolean removeLast() {
        try {
            con = ConnectSM.connect();
            String queryH = "SELECT MAX(examID) FROM Exam ;" ;
            ps = con.prepareStatement(queryH);
            rs = ps.executeQuery();
            
            String examID = null;
            
            while(rs.next())
                examID = rs.getString(1);
            
            try {
                String queryI = "DELETE FROM Exam WHERE examID = ? ;" ;
                ps = con.prepareStatement(queryI);
                ps.setString(1, examID);
                
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

    
}
