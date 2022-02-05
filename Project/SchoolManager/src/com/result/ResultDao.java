package com.result;

import com.connection.ConnectSM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ResultDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs,rs1,rs2,rs3;
    
    public static ObservableList getResults(String grade, String subject, String year, String term) {
        ObservableList list = FXCollections.observableArrayList();
        String subjectID = "";
        String examID = "";
        String name = "";
        String studentID = "";
        String mark = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT subjectID FROM Subject WHERE subject = ? AND grade = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, subject);
            ps.setString(2, grade);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                subjectID = rs.getString(1);
                
                Result result = new Result();
                
                String queryB = "SELECT examID FROM Exam WHERE subjectID = ? AND year = ? AND term = ? ;" ;
                ps = con.prepareStatement(queryB);
                ps.setString(1, subjectID);
                ps.setString(2, year);
                ps.setString(3, term);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    examID = rs1.getString(1);
                    
                    String queryC = "SELECT * FROM StudentFaceExams WHERE examID = ? AND subjectID = ? " ;
                    ps = con.prepareStatement(queryC);
                    ps.setString(1, examID);
                    ps.setString(2, year);
                    rs2 = ps.executeQuery();
                    
                    while(rs2.next()) {
                        studentID = rs2.getString(1);
                        mark = rs2.getString(3);
                        
                        String queryD = "SELECT name FROM User WHERE userID = ? " ;
                        ps = con.prepareStatement(queryD);
                        ps.setString(1, studentID);
                        rs3 = ps.executeQuery();
                        
                        while(rs3.next()) {
                            name = rs3.getString(1);
                            
                            result.setExamID(examID);
                            result.setStudentID(studentID);
                            result.setName(name);
                            result.setResult(mark);
                        }
                    }
                }
                
                list.add(result);
            }
            
            return list;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs3 != null) try { rs3.close(); } catch(Exception e) {}
            if(rs2 != null) try { rs2.close(); } catch(Exception e) {}
            if(rs1 != null) try { rs1.close(); } catch(Exception e) {}
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return null;
    }
    
}
