package com.test1.jsf.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 

public class DatabaseConnection {
    protected static Connection initializeDatabase()
            throws SQLException, ClassNotFoundException
        {

    Class.forName("com.mysql.cj.jdbc.Driver");
    String url="jdbc:mysql://localhost:3306/infinite";
    String user="root";
    String password="root@123";
    Connection con=DriverManager.getConnection(url,user,password);
    if(con!=null){
        System.out.println("connection successfull");

    }
    else{
        System.out.println("connection unsuccess");
    }
    return con;

 

}

 

    
}
