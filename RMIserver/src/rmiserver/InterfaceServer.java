/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Faculudade2015-2016
 */
public class InterfaceServer extends UnicastRemoteObject implements Interface
{
    
    public Connection db;
    
	public InterfaceServer() throws RemoteException
	{
		System.out.println("Novo server....");
	}
	public Boolean auth(String user, String password) throws RemoteException
	{
            System.out.println(user);
		// Authentication/Login user
        Connection DBConn = null;           // MySQL connection handle
        DBConn = connecttoDB("orderinfo");
        if(DBConn != null)
        {
            //System.out.println("Connected to DB!");
            // Database parameters
            ResultSet res = null;               // SQL query result set pointer
            Statement s = null;                 // SQL statement pointer
            try
            {
                s = DBConn.createStatement();
                res = s.executeQuery("Select * FROM users WHERE username='"+user+"' AND password='"+password+"';");
                res.next();
                //System.out.println(res.getString("username"));
                if(user.equals(res.getString("username")) && password.equals(res.getString("password")))
                {
                    System.out.println("User authenticated");
                    return true;
                } else {
                    System.out.println("User or Password incorrect");
                    return false;
                }            
            } catch (Exception e) {
                System.out.println("Problem in authentication:: " + e);
            }
        }
        return false;
	}
        
    public Connection connecttoDB(String dbname) throws RemoteException
    {
        Connection DBConn = null;           // MySQL connection handle
        // Connect to the orderinfo database
        try
        {
            System.out.println(">> Establishing Driver...");
            //load JDBC driver class for MySQL
            Class.forName( "com.mysql.jdbc.Driver" );
            //System.out.println(">> Setting up URL...");
            //define the data source
            String SQLServerIP = "localhost";
            String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/"+dbname;
            //System.out.println(">> Establishing connection with: " + sourceURL + "...");
            //System.out.println(msgString);
            //create a connection to the db - note the default account is "remote"
            //and the password is "remote_pass"
            DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
            return DBConn;
        } catch (Exception e) {
            System.out.println("Problem connecting to database:: " + e);
        }
        return null;
    }
    
    public String ListInventoryTrees()throws RemoteException{
        ResultSet res = null;
        Statement s = null;
        String msgString = null;
        Connection DBConn = null;           // MySQL connection handle
        DBConn =  connecttoDB("inventory");
        
        if (DBConn!=null)
        {
            
            try
            {
                s = DBConn.createStatement();
                res = s.executeQuery( "Select * from trees" );

                String enviar = "";
                
                while (res.next())
                {
                    msgString = res.getString(2) + " : " + res.getString(3) +
                            " : $"+ res.getString(5) + " : " + res.getString(4)
                            + " units in stock";
                    enviar = enviar +msgString+"\n";
                    

                }

               return enviar;
                
            }catch (Exception e) {
                    e.printStackTrace();


            } // end try-catch
        } // if connect check
        return null;
    }
}
