/*
 * Customer.java
 *
 * Created on September 10, 2009, 3:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CustomerManage;
import ConnectDatabase.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author TranVanHien
 */
public class Customer {
    
    /** Creates a new instance of Customer */
    private int customerCode;
    private String customerName;
    private String customerType;
    private String customerPhone;
    private String customerFax;
    private String customerEmail;
    private String customerAddress;
    private String customerRelationship;
    
    public Customer() {
    }   
    
    /**
     *Method getcustomerCode(), get value of customerCode field.
     *@return customerCode
     */
    public int getcustomerCode() {
        return customerCode;
    }
    /**
     * Method setcustomerCode(), set value for customerCode field.
     * 
     * @param customerCode
     */
    public void setcustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }
    
    /**
     *Method getcustomerName(), get value of customerName field.
     *@return customerName
     */
    public String getcustomerName() {
        return customerName;
    }
    /**
     * Method setcustomerName(), set value for customerName field.
     * 
     * @param customerName
     */
    public void setcustomerName(String customerName) {
        this.customerName = customerName;
    }    
    
    /**
     *Method getcustomerType(), get value of customerType field.
     *@return customerType
     */
    public String getcustomerType() {
        return customerType;
    }
    /**
     * Method setcustomerType(), set value for customerType field.
     * 
     * @param customerType
     */
    public void setcustomerType(String customerType) {
        this.customerType = customerType;
    }    
    
    /**
     *Method getcustomerPhone(), get value of customerPhone field.
     *@return customerPhone
     */
    public String getcustomerPhone() {
        return customerPhone;
    }
    /**
     * Method setcustomerPhone(), set value for customerPhone field.
     * 
     * @param customerPhone
     */
    public void setcustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }    
    
    /**
     *Method getcustomerFax(), get value of customerFax field.
     *@return customerFax
     */
    public String getcustomerFax() {
        return customerFax;
    }
    /**
     * Method setcustomerFax(), set value for customerFax field.
     * 
     * @param customerFax
     */
    public void setcustomerFax(String customerFax) {
        this.customerFax = customerFax;
    }    
    
    /**
     *Method getcustomerEmail(), get value of customerEmail field.
     *@return customerEmail
     */
    public String getcustomerEmail() {
        return customerEmail;
    }
    /**
     * Method setcustomerEmail(), set value for customerEmail field.
     * 
     * @param customerEmail
     */
    public void setcustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }    
    
    /**
     *Method getcustomerAddress(), get value of customerAddress field.
     *@return customerAddress
     */
    public String getcustomerAddress() {
        return customerAddress;
    }
    /**
     * Method setcustomerAddress(), set value for customerAddress field.
     * 
     * @param customerAddress
     */
    public void setcustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }   
    /**
     *Method getcustomerRelationship(), get value of customerRelationship field.
     *@return customerRelationship
     */
    public String getcustomerRelationship() {
        return customerRelationship;
    }
    /**
     * Method setcustomerRelationship(), set value for customerRelationship field.
     * 
     * @param customerRelationship
     */
    public void setcustomerRelationship(String customerRelationship) {
        this.customerRelationship = customerRelationship;
    }    
    
    /**
     * Method getAllCustomer(), get all Customer.
     * @return Vector v
     * @throws java.sql.SQLException 
     */
    public static Vector getAllCustomer() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetAllCustomer");
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerFax = rs.getString(5);
                objCustomer.customerEmail = rs.getString(6);
                objCustomer.customerAddress = rs.getString(7);
                objCustomer.customerRelationship = rs.getString(8);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    
    public static Vector searchCustomerByName(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchCustomerByName",vList);
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerAddress = rs.getString(5);
                objCustomer.customerRelationship = rs.getString(6);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    
    public static Vector searchCustomerByType(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchCustomerByType",vList);
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerAddress = rs.getString(5);
                objCustomer.customerRelationship = rs.getString(6);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    
      public static Vector searchCustomerByRelation(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchCustomerByRelation",vList);
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerAddress = rs.getString(5);
                objCustomer.customerRelationship = rs.getString(6);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
      
      public static Vector searchCustomerByAddress(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchCustomerByAddress",vList);
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerAddress = rs.getString(5);
                objCustomer.customerRelationship = rs.getString(6);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    
    public static Vector searchCustomerByNameAndType(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchCustomerByNameAndType",vList);
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerAddress = rs.getString(5);
                objCustomer.customerRelationship = rs.getString(6);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
      
    public static Vector searchCustomerByNameAndAddress(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchCustomerByTypeAndAddress",vList);
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerAddress = rs.getString(5);
                objCustomer.customerRelationship = rs.getString(6);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
      
     public static Vector searchCustomerByTypeAndAddress(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchCustomerByTypeAndAddress",vList);
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerAddress = rs.getString(5);
                objCustomer.customerRelationship = rs.getString(6);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
     
    public static Vector searchCustomerAdvanced(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchAdvancedCustomer",vList);
            while(rs.next()){
                Customer objCustomer = new Customer();
                objCustomer.customerCode = rs.getInt(1);
                objCustomer.customerName = rs.getString(2);
                objCustomer.customerType = rs.getString(3);                
                objCustomer.customerPhone = rs.getString(4);
                objCustomer.customerAddress = rs.getString(5);
                objCustomer.customerRelationship = rs.getString(6);
                v.add(objCustomer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    /**
     * Method to insert into table Customer
     * @return int i
     * @param objCustomer
     * @throws java.sql.SQLException
     */
    public static int insertCustomer(Customer objCustomer) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector();            
            paramList.add(objCustomer.customerName);
            paramList.add(objCustomer.customerType);
            paramList.add(objCustomer.customerPhone);
            paramList.add(objCustomer.customerFax);
            paramList.add(objCustomer.customerEmail);
            paramList.add(objCustomer.customerAddress);
            paramList.add(objCustomer.customerRelationship);            
            i = DBHelper.executeUpdate("spInsertCustomer",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
    /**
     * Method to update table Customer
     * @return int i
     * @param objCustomer
     * @throws java.sql.SQLException
     */
    public static int updateCustomer(Customer objCustomer) throws SQLException {
        int i=0;
        try {            
            Vector paramList = new Vector();
            paramList.add(objCustomer.customerCode);
            paramList.add(objCustomer.customerName);
            paramList.add(objCustomer.customerType);
            paramList.add(objCustomer.customerPhone);
            paramList.add(objCustomer.customerFax);
            paramList.add(objCustomer.customerEmail);
            paramList.add(objCustomer.customerAddress);
            paramList.add(objCustomer.customerRelationship);     
            i = DBHelper.executeUpdate("spUpdateCustomer",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
    /**
     * Method to delete table Customer
     * @return int i
     * @param customerName
     * @throws java.sql.SQLException
     */
     public static int deleteCustomer(String customerName) throws SQLException {
        int i=0;
        try {
            Vector paramList=new Vector();
            paramList.add(customerName);
            i = DBHelper.executeUpdate("spDeleteCustomer",paramList);
        } catch(SQLException sqlex) {            
        }
        return i;
    }
    
}
