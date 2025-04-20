/*
 * Author : Jananii P
 * Date : 19/04/25
 * Description : Utility class to establish a connection with the MySQL database 
 *               for the e-commerce application.  
 *               
 */
package com.hexaware.ecommerce.user.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

         
            String url = "jdbc:mysql://localhost:3306/ecommerce";
            String username = "root";
            String password = "Jananii@123";

            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}