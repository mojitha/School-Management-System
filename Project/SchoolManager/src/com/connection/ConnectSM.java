package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSM {
    private static Connection con;
    
    public static Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolmanager","root","");
        }
        catch(ClassNotFoundException | SQLException cnfe) {
            System.out.println(cnfe);
        }
        
        return con;
    }
    
}

