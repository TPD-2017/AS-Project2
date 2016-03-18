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
    public Logger log;
    public Connection db;
    public String userSave;
    
	public InterfaceServer() throws RemoteException
	{
            log = new Logger("loginlog.txt", "orderlog.txt");
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
                    userSave = user;
                    log.logLogin(user);
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
            //System.out.println(">> Establishing Driver...");
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
    
    public String ListInventorySeeds()throws RemoteException{
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
                res = s.executeQuery( "Select * from seeds" );
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
    
    public String ListInventoryShrubs()throws RemoteException{
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
                res = s.executeQuery( "Select * from shrubs" );
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
    
    public Boolean ConnectToOrder()throws RemoteException{
        try{
                //load JDBC driver class for MySQL
        Class.forName( "com.mysql.jdbc.Driver" );

        

                //define the data source
              String SQLServerIP = "localhost";
                String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/orderinfo";


                //create a connection to the db - note the default account is "remote"
                //and the password is "remote_pass" - you will have to set this
                //account up in your database

                db = DriverManager.getConnection(sourceURL,"remote","remote_pass");
                return false;
        } catch (Exception e) {

                //errString =  "\nError connecting to orderinfo database\n" + e;
                //jTextArea3.append(errString);
                //connectError = true;
                return true;
            }
    }
    
    public Boolean InsertOrder(String dateTimeStamp, String firstName, String lastName, 
            String customerAddress, String phoneNumber, Float fCost, String orderTableName) throws RemoteException{
        String SQLstatement = null;
        Statement s = null;
        int executeUpdateVal;
        
        try
            {
                s = db.createStatement();

                SQLstatement = ( "CREATE TABLE " + orderTableName +
                            "(item_id int unsigned not null auto_increment primary key, " +
                            "product_id varchar(20), description varchar(80), " +
                            "item_price float(7,2) );");

                executeUpdateVal = s.executeUpdate(SQLstatement);

            } catch (Exception e) {

                return true;

            } // try
        
        try
                {
                    SQLstatement = ( "INSERT INTO orders (order_date, " + "first_name, " +
                        "last_name, address, phone, total_cost, shipped, " +
                        "ordertable) VALUES ( '" + dateTimeStamp + "', " +
                        "'" + firstName + "', " + "'" + lastName + "', " +
                        "'" + customerAddress + "', " + "'" + phoneNumber + "', " +
                        fCost + ", " + false + ", '" + orderTableName +"' );");

                    executeUpdateVal = s.executeUpdate(SQLstatement);
                    
                } catch (Exception e1) {

                    

                    try
                    {
                        SQLstatement = ( "DROP TABLE " + orderTableName + ";" );
                        executeUpdateVal = s.executeUpdate(SQLstatement);
                        return true;
                    } catch (Exception e2) {

                        return true;

                    } // try

                } // try
        return false;
    }
    
    public Boolean FinishOrder(String orderTableName, String productID, String description
                            ,Float perUnitCost, String dateTimeStamp) throws RemoteException{
        String SQLstatement = null;
        Statement s = null;
        int executeUpdateVal;
        SQLstatement = ( "INSERT INTO " + orderTableName +
                        " (product_id, description, item_price) " +
                        "VALUES ( '" + productID + "', " + "'" +
                        description + "', " + perUnitCost + " );");
                    try
                    {
                        s = db.createStatement();
                        executeUpdateVal = s.executeUpdate(SQLstatement);

                        // Clean up the display

                        /*jTextArea1.setText("");
                        jTextArea2.setText("");
                        jTextArea4.setText("");
                        jTextField3.setText("");
                        jTextField4.setText("");
                        jTextField5.setText("");
                        jTextField6.setText("$0");*/
                            
                    } catch (Exception e) {

                        //errString =  "\nProblem with inserting into table " + orderTableName +
                        //    ":: " + e;
                        //jTextArea3.append(errString);
                        return true;

                    } // try
           log.logOrder(userSave, productID, dateTimeStamp);
            return false;
    }
    public void LogOut() throws RemoteException{
        log.logLogout(userSave);
    }
}
