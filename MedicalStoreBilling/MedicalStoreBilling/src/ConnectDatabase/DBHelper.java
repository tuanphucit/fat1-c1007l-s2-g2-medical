/*
 * DBHelper.java
 *
 * Created on April 5, 2012, 9:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ConnectDatabase;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
/**
 *
 * @author NguyenHuuManh
 */
public class DBHelper {
     private static Connection conn = null;
     public static Connection getConnection(){
        return conn;
     }
    /** Creates a new instance of DBHelper */
    public DBHelper() {
         if(conn == null){
            try{
                String url = this.getSQLServerUrlConString();                
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(url);
               
            }catch (ClassNotFoundException e){
                System.out.println(e);
            }catch (SQLException sql){
                System.out.println(sql);
            }            
        }
    }
    public static String getSQLServerUrlConString(){
        Properties p = new Properties();
        try {
            FileInputStream fin = new FileInputStream("connection.cfg");
            p.load(fin);
        } 
        catch(Exception e) {
           return "";
        }
        String server = p.getProperty("ServerID");
        String instance = p.getProperty("Instance");
        String port = p.getProperty("Port");
        String databaseName = p.getProperty("Database");
        String username = p.getProperty("Username");
        String password = p.getProperty("Password");
        
        return "jdbc:sqlserver://" + server + ":" + port +
                ";databaseName=" + databaseName +
                ";User=" + username +
                ";Password=" + password;
    }
    /**
     * Method executes a non-parameter selecting querry
     * @return ResultSet cs.executeQuery()
     * @param spName 
     * @throws java.sql.SQLException 
     */
    public static ResultSet executeQuery(String spName) throws SQLException{
        if(conn != null)
        {
            CallableStatement cs = conn.prepareCall("{call " + spName + "}");            
            return cs.executeQuery();
        }
        return null;
    } 
    public static ResultSet executeQuery(String spName, Vector paramList) throws SQLException{
        if(conn != null){
            String strQ = "{call " + spName + "(";
            int t =0;
            for(Object obj : paramList){
                if(t != 0)
                    strQ += ",";
                if(obj instanceof Integer){
                    Integer i = (Integer)obj;
                    strQ += i.toString();
                }else if(obj instanceof String){
                    String s = (String)obj;
                    strQ += "'" + s + "'";
                }
                t++;
            }
            strQ += ")}";
            
            CallableStatement cst = conn.prepareCall(strQ);
            return cst.executeQuery();
        }
        return null;
    }
    public static int executeUpdate(String spName, Vector paramList) throws SQLException{
        if(conn != null)
        {
            String strQ = "{call " + spName + "(";
            
            int t = 0;
            for(Object obj : paramList)
            {
                if(t != 0)
                    strQ += ",";
                if(obj instanceof Integer)
                {
                    Integer i = (Integer)obj;
                    strQ += i.toString();
                }
                else if(obj instanceof Float)
                {
                    Float f = (Float)obj;
                    strQ += f.toString();
                }
                else if(obj instanceof String)
                {
                    String s = (String)obj;
                    strQ += "'" + s + "'";
                }
                t++;
            }
            strQ += ")}";
            
            CallableStatement cst = conn.prepareCall(strQ);
            return cst.executeUpdate();
        }
        return -1;
    }  
}
