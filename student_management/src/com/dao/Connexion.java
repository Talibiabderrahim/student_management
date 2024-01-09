package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {
    private static Connection cnx = null;

    private static void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/universitydb";
            String user = "root";
            String password = "";

            cnx = DriverManager.getConnection(url, user, password);

            System.out.println("[+] Connection successful");
        }
        catch(Exception e){
            System.out.println("[-] Connection failed" + e.getMessage());
        }
    }
    public static Connection getConnexion()
    {
        if(cnx == null) connect();
        return cnx;
    }

}
