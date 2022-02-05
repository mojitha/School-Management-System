package com.student;

import com.connection.ConnectSM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs,rs1;
    
    public static boolean addNewStudent(Student currStudent) {
        try {
            con = ConnectSM.connect();
            String queryA = "INSERT INTO User (userID,name,userName,birthDate,registeredDate,email,phone,address) VALUES (?,?,?,?,?,?,?,?) ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, currStudent.getUserID());
            ps.setString(2, currStudent.getName());
            ps.setString(3, currStudent.getUserName());
            ps.setString(4, currStudent.getBirthDate());
            ps.setString(5, currStudent.getRegisteredDate());
            ps.setString(6, currStudent.getEmail());
            ps.setString(7, currStudent.getPhone());
            ps.setString(8, currStudent.getAddress());
            
            int countA = ps.executeUpdate();
            
            if(countA > 0) {
                try {
                    String queryB = "INSERT INTO Student (userID,grade,class,religion) VALUES (?,?,?,?) ;" ;
                    ps = con.prepareStatement(queryB);
                    ps.setString(1, currStudent.getUserID());
                    ps.setString(2, currStudent.getGrade());
                    ps.setString(3, currStudent.getStuClass());
                    ps.setString(4, currStudent.getReligion());
                    
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

    public static String returnID() {
        try {
            con = ConnectSM.connect();
            String prevID = "";
            String queryC = "SELECT MAX(userID) FROM User ;" ;
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
    
    // check class availablility
    public static boolean checkClassAvailability(String grade, String stuClass) {
        int stuCount = 0;
        
        try {
            con = ConnectSM.connect();
            String queryD = "SELECT count(userID) FROM Student WHERE grade = ? AND class = ? ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, grade);
            ps.setString(2, stuClass);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                stuCount = rs.getInt(1);
            }
            
            return stuCount < 40;
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

    public static ObservableList<Student> loadStudents(String year, String grade, String stuClass) {
        ObservableList<Student> list = FXCollections.observableArrayList();
        
        try {
            con = ConnectSM.connect();
            String queryI = "SELECT User.*,Student.grade,Student.class,Student.religion "
                        + "FROM User,Student "
                        + "WHERE User.userID = Student.userID "
                        + "AND User.userID IN "
                            + "(SELECT s.userID FROM Student s,StudentYear sy "
                            + "WHERE s.userID = sy.userID "
                            + "AND sy.year = ? "
                            + "AND s.grade = ? "
                            + "AND s.class = ? ) ;" ;
            
            ps = con.prepareStatement(queryI);
            ps.setString(1, year);
            ps.setString(2, grade);
            ps.setString(3, stuClass);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Student student = new Student();
                
                student.setUserID(rs.getString(1));
                student.setName(rs.getString(2));
                student.setUserName(rs.getString(3));
                student.setPassword(rs.getString(4));
                student.setBirthDate(rs.getString(5));
                student.setRegisteredDate(rs.getString(6));
                student.setEmail(rs.getString(7));
                student.setPhone(rs.getString(8));
                student.setAddress(rs.getString(9));
                student.setBio(rs.getString(10));
                student.setImg(rs.getString(11));
                student.setStatus(rs.getString(12));
                student.setGrade(rs.getString(13));
                student.setStuClass(rs.getString(14));
                student.setReligion(rs.getString(15));
                
                list.add(student);
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

    public static ObservableList<Student> getLastStudent() {
        ObservableList<Student> list = FXCollections.observableArrayList();
        String lastStudentID = "";
        
        try {
            con = ConnectSM.connect();
            String queryJ = "SELECT MAX(userID) FROM Student ;" ;
            ps = con.prepareStatement(queryJ);
            rs = ps.executeQuery();
            
            while(rs.next()) {
               lastStudentID = rs.getString(1);
            }
            
            try {
                String queryK = "SELECT User.*,Student.grade,Student.class,Student.religion "
                            + "FROM User,Student "
                            + "WHERE User.userID = Student.userID AND User.userID = ? ;" ;
                ps = con.prepareStatement(queryK);
                ps.setString(1, lastStudentID);
                rs = ps.executeQuery();

                while(rs.next()) {
                   Student student = new Student();
                
                    student.setUserID(rs.getString(1));
                    student.setName(rs.getString(2));
                    student.setUserName(rs.getString(3));
                    student.setPassword(rs.getString(4));
                    student.setBirthDate(rs.getString(5));
                    student.setRegisteredDate(rs.getString(6));
                    student.setEmail(rs.getString(7));
                    student.setPhone(rs.getString(8));
                    student.setAddress(rs.getString(9));
                    student.setBio(rs.getString(10));
                    student.setImg(rs.getString(11));
                    student.setStatus(rs.getString(12));
                    student.setGrade(rs.getString(13));
                    student.setStuClass(rs.getString(14));
                    student.setReligion(rs.getString(15));

                    list.add(student);
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

    public static ObservableList<Student> getStudentList(String keyword) {
        ObservableList<Student> list = FXCollections.observableArrayList();
        String userID = "";
        
        try {
            con = ConnectSM.connect();
            String queryL = "SELECT * "
                            + "FROM User "
                            + "WHERE userID LIKE ? "
                                + "OR name LIKE ? "
                                + "OR birthDate LIKE ? "
                                + "OR registeredDate LIKE ? "
                                + "OR email LIKE ? "
                                + "OR phone LIKE ? "
                                + "OR address LIKE ? ;" ;
            
            ps = con.prepareStatement(queryL);
            
            keyword = "%"+keyword+"%";
            
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
            ps.setString(5, keyword);
            ps.setString(6, keyword);
            ps.setString(7, keyword);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                Student student = new Student();
                
                    student.setUserID(rs.getString(1));
                    student.setName(rs.getString(2));
                    student.setUserName(rs.getString(3));
                    student.setPassword(rs.getString(4));
                    student.setBirthDate(rs.getString(5));
                    student.setRegisteredDate(rs.getString(6));
                    student.setEmail(rs.getString(7));
                    student.setPhone(rs.getString(8));
                    student.setAddress(rs.getString(9));
                    student.setBio(rs.getString(10));
                    student.setImg(rs.getString(11));
                    student.setStatus(rs.getString(12));
            
                userID = rs.getString(1);
                String queryM = "SELECT grade,class,religion FROM Student WHERE userID = ? " ;
                ps = con.prepareStatement(queryM);
                ps.setString(1, userID);
                rs1 = ps.executeQuery();
                
                while(rs1.next()) {
                
                    student.setGrade(rs1.getString(1));
                    student.setStuClass(rs1.getString(2));
                    student.setReligion(rs1.getString(3));

                    list.add(student);
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

    public static String getStudentCount(String grade, String year, String stuClass) {
        String count = "";
        
        try {
            con = ConnectSM.connect();
            String queryA = "SELECT count(st.userID) FROM StudentYear sy,Student st WHERE sy.year = ? "
                    + "AND sy.userID = st.userID "
                    + "AND st.grade = ? "
                    + "AND st.class = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, year);
            ps.setString(2, grade);
            ps.setString(3, stuClass);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                count = rs.getString(1);
            }
            
            return count;
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

    public static void setCurrentStudent(String studentID) {
        Student currStudent = new Student();
        
        try {
            con = ConnectSM.connect();
            String queryC = "SELECT * FROM User WHERE userID = ? ;" ;
            
            ps = con.prepareStatement(queryC);
            ps.setString(1, currStudent.getUserID());
            rs = ps.executeQuery();
           
            while(rs.next()) {
                currStudent.setUserID(rs.getString(1));
                currStudent.setName(rs.getString(2));
                currStudent.setUserName(rs.getString(3));
                currStudent.setPassword(rs.getString(4));
                currStudent.setBirthDate(rs.getString(5));
                currStudent.setRegisteredDate(rs.getString(6));
                currStudent.setEmail(rs.getString(7));
                currStudent.setPhone(rs.getString(8));
                currStudent.setAddress(rs.getString(9));
                currStudent.setBio(rs.getString(10));
                currStudent.setImg(rs.getString(11));
                currStudent.setStatus(rs.getString(12));
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

    public static boolean updateStudent(Student studentToUp) {
        try {
            con = ConnectSM.connect();
            String queryD = "UPDATE User SET name = ?, birthDate = ?, address = ?, phone = ?, email = ? WHERE userID = ? ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, studentToUp.getName());
            ps.setString(2, studentToUp.getBirthDate());
            ps.setString(3, studentToUp.getAddress());
            ps.setString(4, studentToUp.getPhone());
            ps.setString(5, studentToUp.getEmail());
            ps.setString(6, studentToUp.getUserID());
            
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
    
}
