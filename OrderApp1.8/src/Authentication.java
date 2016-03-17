
import java.sql.*;

/**
 *
 * @author dbast
 */
public class Authentication {
    public Connection connecttoDB(String dbname)
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
            //System.out.println("Problem connecting to database:: " + e);
        }
        return null;
    }
    
    public Boolean auth(String user, String pass)
    {
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
                res = s.executeQuery("Select * FROM users WHERE username='"+user+"' AND password='"+pass+"';");
                res.next();
                //System.out.println(res.getString("username"));
                if(user.equals(res.getString("username")) && pass.equals(res.getString("password")))
                {
                    //System.out.println("User authenticated");
                    return true;
                } else {
                    //System.out.println("User or Password incorrect");
                    return false;
                }            
            } catch (Exception e) {
                //System.out.println("Problem in authentication:: " + e);
            }
        }
        return false;
    }
}
