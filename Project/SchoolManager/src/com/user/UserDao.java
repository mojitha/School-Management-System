package com.user;

import com.connection.ConnectSM;
import com.string.Strings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private static Connection con;
    private static PreparedStatement ps;
    private static ResultSet rs,rs1;
    private static FileInputStream fis;
    
    public static char authenticateUser(User user) {
        try {
            String uname = user.getUserName();
            String pwd = user.getPassword();
            String uid = null;
            String dbuname = null;
            String dbpwd = null;
        
            con = ConnectSM.connect();
            String queryA = "SELECT userID,userName,password FROM User WHERE userName = ? ;" ;
            ps = con.prepareStatement(queryA);
            ps.setString(1, uname);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                uid = rs.getString(1);
                dbuname = rs.getString(2);
                dbpwd = rs.getString(3);
            }
                
            if(uname.equalsIgnoreCase(dbuname)) {
                if(pwd.equals(dbpwd)) {
                    user.setUserID(uid);
                    return 'w';
                }
                else
                    return 'p';
            }
            else
                return 'u';
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(rs != null) try { rs.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return 'f';
    }

    public static String getDesignation(User user) {
        try {
            String dbdesignation = null;
            
            con = ConnectSM.connect();
            String queryB = "SELECT designation FROM NonAcademicStaff WHERE userID = ? ;" ;
            ps = con.prepareStatement(queryB);
            ps.setString(1, user.getUserID());
            rs = ps.executeQuery();
           
            while(rs.next())
                dbdesignation = rs.getString(1);
            
            setCurrentUser(user);
            return dbdesignation;
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
    
    public static void setCurrentUser(User user) {
        try {
            NonAcademicStaff currentUser = NonAcademicStaff.getInstance();
            currentUser.setUserID(user.getUserID());
            
            con = ConnectSM.connect();
            String queryC = "SELECT u.*,nas.designation,nas.qualification FROM User u,NonAcademicStaff nas WHERE u.userID = nas.userID AND u.userID = ? ;" ;
            
            ps = con.prepareStatement(queryC);
            ps.setString(1, currentUser.getUserID());
            rs = ps.executeQuery();
           
            while(rs.next()) {
                currentUser.setUserID(rs.getString(1));
                currentUser.setName(rs.getString(2));
                currentUser.setUserName(rs.getString(3));
                currentUser.setPassword(rs.getString(4));
                currentUser.setBirthDate(rs.getString(5));
                currentUser.setRegisteredDate(rs.getString(6));
                currentUser.setEmail(rs.getString(7));
                currentUser.setPhone(rs.getString(8));
                currentUser.setAddress(rs.getString(9));
                currentUser.setBio(rs.getString(10));
                currentUser.setImg(rs.getString(11));
                currentUser.setStatus(rs.getString(12));
                currentUser.setDesignation(rs.getString(13));
                currentUser.setQualification(rs.getString(14));
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
    
    // updates profile for edit profile
    public static boolean updateProfile(User user) throws FileNotFoundException {
        try {
            con = ConnectSM.connect();
            String queryD = "UPDATE User SET name = ?, userName = ?, password = ?, phone = ?, address = ?, bio = ?, img = ? WHERE userID = ? ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getBio());

            if(NonAcademicStaff.getFile() != null) {
                File imgFile = NonAcademicStaff.getFile();
                fis = new FileInputStream(imgFile);
                ps.setBinaryStream(7, fis, (int)imgFile.length());
            }
            else
                ps.setString(7, null);
            
            ps.setString(8, user.getUserID());
            
            int count = ps.executeUpdate();
            if(count > 0)
                return true;
        }
        catch(SQLException sqle) {
            System.out.println(sqle);
        }
        finally {
            if(fis != null) try { fis.close(); } catch(Exception e) {}
            if(ps != null) try { ps.close(); } catch(Exception e) {}
            if(con != null) try { con.close(); } catch(Exception e) {}
        }
        
        return false;
    }

    public static void saveProfilePicture(String userID) throws FileNotFoundException, IOException {
        try {
            con = ConnectSM.connect();
            String queryE = "SELECT img FROM User WHERE userID = ? ;" ;
            ps = con.prepareStatement(queryE);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                File image = new File(Strings.imageInventoryManagerDisplayPictureURL);
                try (FileOutputStream fos = new FileOutputStream(image)) {
                    byte[] buffer = new byte[1];
                    InputStream is = rs.getBinaryStream(1);
                    while (is.read(buffer) > 0) {
                        fos.write(buffer);
                    }
                }
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

    // loads profile picture
    public static Blob getProfilePictureBlob(String userID) {
        Blob profilePictureBlob = null;
        
        try {
            con = ConnectSM.connect();
            String queryF = "SELECT img FROM user WHERE userID = ? ;";
            ps = con.prepareStatement(queryF);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                profilePictureBlob = rs.getBlob(1);
            }
            
            return profilePictureBlob;
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

    public static boolean updateUser(User staffToUp) {
        try {
            con = ConnectSM.connect();
            String queryD = "UPDATE User SET name = ?, birthDate = ?, address = ?, phone = ?, email = ? WHERE userID = ? ;" ;
            ps = con.prepareStatement(queryD);
            ps.setString(1, staffToUp.getName());
            ps.setString(2, staffToUp.getBirthDate());
            ps.setString(3, staffToUp.getAddress());
            ps.setString(4, staffToUp.getPhone());
            ps.setString(5, staffToUp.getEmail());
            ps.setString(6, staffToUp.getUserID());
            
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

    public static boolean updateDesignation(String staffID, String designation) {
        String dbUserID = "";
        int checkFlag = 0;
        
        try {
            con = ConnectSM.connect();
            
            if(checkFlag == 0) {
                String queryD = "SELECT userID FROM AcademicStaff WHERE userID = ? ;" ;
                ps = con.prepareStatement(queryD);
                ps.setString(1, staffID);
                rs = ps.executeQuery();

                while(rs.next()) {
                    dbUserID = rs.getString(1);

                    if(dbUserID.equals(staffID)) {
                        String queryF = "UPDATE AcademicStaff SET designation = ? WHERE userID = ? ;" ;
                        ps = con.prepareStatement(queryF);
                        ps.setString(1, designation);
                        ps.setString(2, staffID);

                        int countA = ps.executeUpdate();
                        if(countA > 0)
                            return true;
                    }
                }
                
                checkFlag = 1;
            }
            
            if(checkFlag == 1) {
                String queryE = "SELECT userID FROM NonAcademicStaff WHERE userID = ? ;" ;
                ps = con.prepareStatement(queryE);
                ps.setString(1, staffID);
                rs1 = ps.executeQuery();

                while(rs1.next()) {
                    dbUserID = rs1.getString(1);

                    if(dbUserID.equals(staffID)) {
                        String queryG = "UPDATE NonAcademicStaff SET designation = ? WHERE userID = ? ;" ;
                        ps = con.prepareStatement(queryG);
                        ps.setString(1, designation);
                        ps.setString(2, staffID);

                        int countB = ps.executeUpdate();
                        if(countB > 0)
                            return true;
                    }
                }
            }
            
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
        
        return false;
    }
    
}