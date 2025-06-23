package com.tekarch.dbtesting;


//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
////import com.mysql.cj.jdbc.ConnectionImpl;
//import com.mysql.jdbc.*;
////import com.mysql.jdbc.Driver;
//import com.mysql.cj.jdbc.Driver;
//
//public class SampleDBTest {
//
//	public static void main(String[] args) throws Throwable {
//		// TODO Auto-generated method stub
//		
//		//Load or register driver
//		Driver
//		
//		
//		driverRef = new Driver();
//		DriverManager.registerDriver(driverRef);
//		
//		//connect to DB
//		Connection con = DriverManager.getConnection("jdbc:mysql://49.249.28.218:3333/ninza_hrm", "root@%", "root");
//		
//		//create statement
//		Statement stat = con.createStatement();
//		String query = "Select * from project";
//		
//		//execute query
//		ResultSet result = stat.executeQuery(query);
//		
//		//process data
//		while(result.next())
//		{
//			System.out.println(result.getString(1)+ "\t" + result.getString(2)+"\t"+result.getString(3)+"\t"+result.getString(4)+"\t"+result.getString(5));
//			
//		}
//		
//		
//		//close connection
//		con.close();
//
//	}
//
//}
//
//


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SampleDBTest {

    public static void main(String[] args) {
        Connection conn = null;

        try {
            // Register JDBC driver (optional for modern JDBC versions)
            Class.forName("com.mysql.cj.jdbc.Driver"); // updated driver class name

            // Open a connection
            conn = DriverManager.getConnection(
                "jdbc:mysql://49.249.28.218:3307/ninza_hrm",
                "root@%", // or whatever your username is
                "root"  // your password
            );

            System.out.println("Connected successfully!");

            // You can now create Statement, execute queries, etc.
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM project");
            
         // execute query
    		//ResultSet result = stmt.executeQuery(query);
    		
    		//process data
    		while(result.next())
    		{
    			System.out.println(result.getString(1)+ "\t" + result.getString(2)+"\t"+result.getString(3)+"\t"+result.getString(4)+"\t"+result.getString(5));
    			
    		}
    		
    		ResultSet rs = stmt.executeQuery("SHOW TABLES");
    		//ResultSet rs1 = stmt.executeQuery("Select * from project");
    		while (rs.next()) {
    		    System.out.println(rs.getString(1));
    		}
    		
            
            

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Clean up
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}




