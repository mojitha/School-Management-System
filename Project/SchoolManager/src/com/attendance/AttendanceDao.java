package com.attendance;

import com.connection.ConnectSM;
import com.string.Strings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class AttendanceDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs,rs1;
    
    public static ObservableList<Attendance> getAttendanceListMark(String grade, String stuClass, String todayDate) {
        ObservableList<Attendance> list = FXCollections.observableArrayList();
        String userID = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT userID FROM Attendance WHERE date = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, todayDate);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
                
                String queryB = "SELECT u.userID,u.name,a.presentAbsent "
                        + "FROM Attendance a,User u,Student s "
                        + "WHERE a.userID = u.userID AND u.userID = s.userID "
                        + "AND a.userID = ? AND a.date = ? AND s.grade = ? AND s.class = ? ;" ;
                ps = con.prepareStatement(queryB);
                ps.setString(1, userID);
                ps.setString(2, todayDate);
                ps.setString(3, grade);
                ps.setString(4, stuClass);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    Attendance attendance = new Attendance();

                        CheckBox attendanceCheck = new CheckBox();
                            attendance.setUserID(rs1.getString(1));
                            attendance.setName(rs1.getString(2));
                            attendance.setPresentAbsent(rs1.getString(3));
                            attendance.setSelect(attendanceCheck);
                        if(rs1.getString(3).equals("1"))
                            attendanceCheck.setSelected(true);
                        
                    list.add(attendance);
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

    public static int getPresent(String grade, String stuClass, String todayDate) {
        String userID = "";
        int count = 0;
        
        try {
            con = ConnectSM.connect();
            String queryC = "SELECT userID FROM Attendance WHERE date = ? AND presentAbsent = 1 ;" ;
            ps = con.prepareStatement(queryC);
            ps.setString(1, todayDate);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
                
                String queryD = "SELECT * FROM Student WHERE userID = ? AND grade = ? AND class = ? ;";
                ps = con.prepareStatement(queryD);
                ps.setString(1, userID);
                ps.setString(2, grade);
                ps.setString(3, stuClass);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    count++;
                }
            }
            
            return count;
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
        
        return count;
    }

    public static int getAbsent(String grade, String stuClass, String todayDate) {
        String userID = "";
        int count = 0;
        
        try {
            con = ConnectSM.connect();
            String queryE = "SELECT userID FROM Attendance WHERE date = ? AND presentAbsent = 0 ;" ;
            ps = con.prepareStatement(queryE);
            ps.setString(1, todayDate);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
                
                String queryF = "SELECT * FROM Student WHERE userID = ? AND grade = ? AND class = ? ;";
                ps = con.prepareStatement(queryF);
                ps.setString(1, userID);
                ps.setString(2, grade);
                ps.setString(3, stuClass);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    count++;
                }
            }
            
            return count;
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
        
        return count;
    }

    public static ObservableList<Attendance> getAttendanceList(String grade, String stuClass, String todayDate) {
        ObservableList<Attendance> list = FXCollections.observableArrayList();
        String userID = "";
        
        try {
            con = ConnectSM.connect();
            String queryG = "SELECT userID FROM Student WHERE grade = ? AND class = ? ;" ;
            ps = con.prepareStatement(queryG);
            ps.setString(1, grade);
            ps.setString(2, stuClass);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
                
                String queryH = "SELECT u.userID,u.name,a.presentAbsent "
                        + "FROM Attendance a,User u "
                        + "WHERE a.userID = u.userID "
                        + "AND a.userID = ? AND a.date = ? ;" ;
                ps = con.prepareStatement(queryH);
                ps.setString(1, userID);
                ps.setString(2, todayDate);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    Attendance attendance = new Attendance();

                        attendance.setUserID(rs1.getString(1));
                        attendance.setName(rs1.getString(2));
                        attendance.setPresentAbsent(rs1.getString(3));
                        attendance.setDate(todayDate);
                        attendance.setGrade(grade);
                        attendance.setStuClass(stuClass);
                        
                    list.add(attendance);
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

    public static int getPercentageToday(LocalDate today) {
        int studentsTotal = 0;
        int presentToday = 0;
        double percentage = 0;
        int percentageInt = 0;
        String todayStr = today+"";
        
        try {
            con = ConnectSM.connect();
            String queryI = "SELECT count(userID) FROM Attendance WHERE date = ? ;" ;
            ps = con.prepareStatement(queryI);
            ps.setString(1, todayStr);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                studentsTotal = rs.getInt(1);
            }
            
                try {
                    String queryJ = "SELECT COUNT(userID) FROM Attendance WHERE date = ? AND presentAbsent = 1 ;" ;
                    ps = con.prepareStatement(queryJ);
                    ps.setString(1, todayStr);
                    rs = ps.executeQuery();
                    
                    while(rs.next()) {
                        presentToday = rs.getInt(1);
                    }
                }
                catch(SQLException sqle) {
                    System.out.println(sqle);
                }
                
                percentage = (double) presentToday / studentsTotal * 100;
                percentageInt = (int) percentage;
                
            return percentageInt;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return -1;
    }

    public static int getStudentCount() {
        int stuCount = 0;
        
        try {
            con = ConnectSM.connect();
            String queryK = "SELECT count(userID) FROM Student ;" ;
            ps = con.prepareStatement(queryK);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                stuCount = rs.getInt(1);
            }
            
            return stuCount;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return -1;
    }

    public static int getClassCount() {
        int classCount = 0;
        
        try {
            con = ConnectSM.connect();
            String queryL = "SELECT COUNT(DISTINCT class) FROM Student GROUP BY grade ;" ;
            ps = con.prepareStatement(queryL);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                classCount += rs.getInt(1);
            }
            
            return classCount;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return -1;
    }

    public static ObservableList<String> getClassesThatNotYetMarked(LocalDate tod) {
        ObservableList<String> listMarked = FXCollections.observableArrayList();
        
        String today = tod+"";
        String userID = "";
        String grade = "";
        String stuClass = "";
        String gradeClassLine = "";
        
        try {
            con = ConnectSM.connect();
            String queryM = "SELECT userID FROM Attendance WHERE date = ? ;" ;
            ps = con.prepareStatement(queryM);
            ps.setString(1, today);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
                
                String queryN = "SELECT grade,class FROM Student WHERE userID = ? ;" ;
                ps = con.prepareStatement(queryN);
                ps.setString(1, userID);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                    grade = rs1.getString(1);
                    stuClass = rs1.getString(2);
                    
                    gradeClassLine = grade+" "+stuClass;
                    
                    if(!listMarked.contains(gradeClassLine))
                        listMarked.add(grade+" "+stuClass);    
                }
            }
            
            return listMarked;
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

    public static ObservableList<Attendance> loadUnmarkedStudents(String grade, String stuClass, String todayDate) {
        ObservableList<Attendance> list = FXCollections.observableArrayList();
        String userID = "";
        String name = "";
        
        try {
            con = ConnectSM.connect();
            String queryO = "SELECT s.userID,u.name FROM Student s,User u "
                        + "WHERE u.userID = s.userID AND "
                        + "s.grade = ? AND s.class = ? ;";
            ps = con.prepareStatement(queryO);
            ps.setString(1, grade);
            ps.setString(2, stuClass);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                userID = rs.getString(1);
                name = rs.getString(2);
                
                Attendance attendance = new Attendance();
                    CheckBox attendanceCheck = new CheckBox();
                    
                        attendance.setUserID(userID);
                        attendance.setName(name);
                        attendance.setDate(todayDate);
                        attendance.setSelect(attendanceCheck);
                        attendanceCheck.setSelected(true);
                        
                list.add(attendance);
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

    // saving and updating attendance table
    public static boolean saveAndUpdate(ObservableList<Attendance> oblist, String toDayDate, String context) {
        Iterator listIterator = oblist.iterator();
        String userID = "";
        String presentAbsent = "";
        
        switch (context) {
            case Strings.context_insert:
                try {
                    while(listIterator.hasNext()) {
                        Attendance attendance = (Attendance) listIterator.next();
                        userID = attendance.getUserID();
                        presentAbsent = attendance.getPresentAbsent();
                        
                        con = ConnectSM.connect();
                        String queryQ = "INSERT INTO Attendance(userID,date,presentAbsent) VALUES(?,?,?) ;";
                        ps = con.prepareStatement(queryQ);
                        ps.setString(1, userID);
                        ps.setString(2, toDayDate);
                        ps.setString(3, presentAbsent);
                        ps.executeUpdate();
                    }
                    
                    return true;
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
            case Strings.context_update:
                try {
                    while(listIterator.hasNext()) {
                        Attendance attendance = (Attendance) listIterator.next();
                        userID = attendance.getUserID();
                        presentAbsent = attendance.getPresentAbsent();
                        
                        con = ConnectSM.connect();
                        String queryR = "UPDATE Attendance SET userID = ?,date = ?,presentAbsent = ? "
                                + "WHERE userID = ? AND date = ? ;";
                        ps = con.prepareStatement(queryR);
                        ps.setString(1, userID);
                        ps.setString(2, toDayDate);
                        ps.setString(3, presentAbsent);
                        ps.setString(4, userID);
                        ps.setString(5, toDayDate);
                        ps.executeUpdate();
                    }
                    
                    return true;
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
        
        return false;
    }

    public static String[] getMonthlyPresent(String studentID, String date) {
        String strArr[] = {"","",""};
        String dateOf = date.substring(0, 7);
        int total = 0;
        int present = 0;
        float percent = 0;
        int percentInt = 0;
        
        try {
            con = ConnectSM.connect();
            String queryS = "SELECT COUNT(userID) FROM Attendance WHERE userID = ? AND date LIKE ? ;" ;
            ps = con.prepareStatement(queryS);
            ps.setString(1, studentID);
            ps.setString(2, "%"+dateOf+"%");
            rs = ps.executeQuery();
            
            while(rs.next()) {
                strArr[0] = rs.getInt(1)+"";
                total = Integer.parseInt(strArr[0]);
            }
            
            String queryT = "SELECT COUNT(userID) FROM Attendance WHERE userID = ? AND date LIKE ? AND presentAbsent = 1 ;" ;
            ps = con.prepareStatement(queryT);
            ps.setString(1, studentID);
            ps.setString(2, "%"+dateOf+"%");
            rs1 = ps.executeQuery();
                
            while(rs1.next()) {
                strArr[1] = rs1.getInt(1)+"";
                present = Integer.parseInt(strArr[1]);
            }
            
            percent = (float) present / total * 100;
            percentInt = (int) percent;
            strArr[2] = percentInt+"%";
            
            return strArr;
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
