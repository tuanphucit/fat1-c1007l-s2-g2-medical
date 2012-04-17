/*
 * Users.java
 *
 * Created on April 8, 2012, 10:03 AM
 *
 * 
 */

package UserManage;
import ConnectDatabase.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author 
 */
public class Users {
    private int userCode;
    private String nameLogin;
    private String password; 
    private String fullName;
    private String userTypeCode;
    private String userAddress;
    private String userPhone;
    private String userEmail;
    private int userActive;
    /** Creates a new instance of Users */
    public Users() {
    }
     public int getuserCode() {
        return userCode;
    }
    /**
     * Method setuserCode(), set value for userCode field.
     * 
     * @param userCode
     */
    public void setuserCode(int userCode) {
        this.userCode = userCode;
    }
    /**
     *Method getnameLogin(), get value of nameLogin field.
     *@return nameLogin
     */
    public String getnameLogin() {
        return nameLogin;
    }
    /**
     * Method setnameLogin(), set value for nameLogin field.
     * 
     * @param nameLogin 
     */
    public void setnameLogin(String nameLogin) {
        this.nameLogin = nameLogin;
    }
    /**
     *Method getPassword(), get value of password field.
     *@return password
     */
    public String getPassword() {
        return password;
    }
     /**
     * Method setPassword(), set value for password field.
     * 
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     *Method getfullName(), get value of fullName field.
     *@return fullName
     */
    public String getfullName() {
        return fullName;
    }
    /**
     * Method setfullName(), set value for fullName field.
     * 
     * @param fullName 
     */
    public void setfullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     *Method getuserTypeCode(), get value of userTypeCode field.
     *@return userTypeCode
     */
    public String getuserTypeCode() {
        return userTypeCode;
    }
    /**
     * Method setuserTypeCode(), set value for userTypeCode field.
     * 
     * @param userTypeCode 
     */
    public void setuserTypeCode(String userTypeCode) {
        this.userTypeCode = userTypeCode;
    }
    /**
     * Method setuserAddress(), set value for userAddress field.
     * 
     * @param userAddress 
     */
    public void setuserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    /**
     *Method getuserAddress(), get value of userAddress field.
     *@return userAddress
     */
    public String getuserAddress() {
        return userAddress;
    }
    /**
     * Method setuserPhone(), set value for userPhone field.
     * 
     * @param userPhone 
     */
    public void setuserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
     /**
     *Method getuserPhone(), get value of userPhone field.
     *@return userPhone
     */
    public String getuserPhone() {
        return userPhone;
    }
    /**
     *Method getuserEmail(), get value of userEmail field.
     *@return userEmail
     */
    public String getuserEmail() {
        return userEmail;
    }
    /**
     * Method setuserEmail(), set value for userEmail field.
     * 
     * @param userEmail 
     */
    public void setuserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
     /**
     *Method getuserActive(), get value of userActive field.
     *@return userActive
     */
    public int getuserActive() {
        return userActive;
    }
    /**
     * Method setuserActive(), set value for userActive field.
     * 
     * @param userActive 
     */
    public void setuserActive(int userActive) {
        this.userActive = userActive;
    }
    /**
     * Method getAllUser(), get all users.
     * 
     * @return Vector v
     * @throws java.sql.SQLException 
     */
    public static Vector getAllUser() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetAllUser");
            while(rs.next()){
                Users objUser = new Users();
                objUser.userCode = rs.getInt(1);
                objUser.nameLogin = rs.getString(2);
                objUser.password = rs.getString(3);
                objUser.fullName = rs.getString(4);
                objUser.userTypeCode = rs.getString(5);
                objUser.userAddress = rs.getString(6);
                objUser.userPhone = rs.getString(7);
                objUser.userEmail = rs.getString(8);
                objUser.userActive = rs.getInt(9);
                v.add(objUser);
            }
        } catch (SQLException ex) {
            
        }
        return v;        
    }   
    /**
     *
     * @return int i
     * Method to change password of User
     * @param pass
     * @throws java.sql.SQLException
     */
    public static int changePassword(String pass) throws SQLException{
        int i = 0;
        try {
            Vector paramList = new Vector();
            paramList.add(pass);
            i = DBHelper.executeUpdate("spChangePassword",paramList);
        } catch (SQLException ex) {
            
        }
        return i;
    }
    
    /**
     * Method to insert into table Users
     * @return int i
     * @param objUser
     * @throws java.sql.SQLException
     */
    public static int insertUser(Users objUser) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector();
            paramList.add(objUser.nameLogin);
            paramList.add(objUser.password);
            paramList.add(objUser.fullName);
            paramList.add(objUser.userTypeCode);
            paramList.add(objUser.userAddress);
            paramList.add(objUser.userPhone);
            paramList.add(objUser.userEmail);
            paramList.add(objUser.userActive);
            i = DBHelper.executeUpdate("spInsertUser",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
     /**
     * Method to update table Users
     * @return int i
     * @param objUser
     * @throws java.sql.SQLException
     */
    public static int updateUser(Users objUser) throws SQLException {
        int i=0;
        try {            
            Vector paramList = new Vector();
            paramList.add(objUser.userCode);
            paramList.add(objUser.nameLogin);
            paramList.add(objUser.password);
            paramList.add(objUser.fullName);
            paramList.add(objUser.userTypeCode);
            paramList.add(objUser.userAddress);
            paramList.add(objUser.userPhone);
            paramList.add(objUser.userEmail);
            paramList.add(objUser.userActive);
            i = DBHelper.executeUpdate("spUpdateUser",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
    /**
     * Method to delete table Users
     * @return int i
     * @param userName
     * @throws java.sql.SQLException
     */
    public static int deleteUser(String userName) throws SQLException {
        int i=0;
        try {
            Vector paramList=new Vector();
            paramList.add(userName);
            i=DBHelper.executeUpdate("spDeleteUser",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
    
    public static Vector getUserCodeForOrders(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetCodeByName",vList);
            while(rs.next()){
                Users objUser = new Users();
                objUser.userCode = rs.getInt(1);
                v.add(objUser);
            }
        } catch (SQLException ex) {
            
        }
        return v;        
    }    
}
