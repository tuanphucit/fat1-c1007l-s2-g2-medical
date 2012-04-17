/*
 * Suppliers.java
 *
 * Created on April 9, 2012, 9:21 AM
 *
 * 
 */

package SupplierManage;
import ConnectDatabase.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author 
 */
public class Suppliers {
    
    /** Creates a new instance of Suppliers */
    private int supplierCode;
    private String supplierName;
    private String supplierfullName;
    private String supplierAddress;
    private String supplierPhone;
    private String supplierFax;
    private String supplierEmail;
    private String supplierWebsite;
    
    public Suppliers() {
    }
    /**
     *Method getsupplierCode(), get value of supplierCode field.
     *@return supplierCode
     */
    public int getsupplierCode() {
        return supplierCode;
    }
    /**
     * Method setsupplierCode(), set value for supplierCode field.
     * 
     * @param supplierCode
     */
    public void setsupplierCode(int supplierCode) {
        this.supplierCode = supplierCode;
    }
    
    /**
     *Method getsupplierName(), get value of supplierName field.
     *@return supplierName
     */
    public String getsupplierName() {
        return supplierName;
    }
    /**
     * Method setsupplierName(), set value for supplierName field.
     * 
     * @param supplierName
     */
    public void setsupplierName(String supplierName) {
        this.supplierName = supplierName;
    }    
    
    /**
     *Method getsupplierFullname(), get value of supplierfullName field.
     *@return supplierfullName
     */
    public String getsupplierFullname() {
        return supplierfullName;
    }
    /**
     * Method setsupplierFullname(), set value for supplierfullName field.
     * 
     * @param supplierfullName
     */
    public void setsupplierFullname(String supplierfullName) {
        this.supplierfullName = supplierfullName;
    }   
    
    /**
     *Method getsupplierAddress(), get value of supplierAddress field.
     *@return supplierAddress
     */
    public String getsupplierAddress() {
        return supplierAddress;
    }
    /**
     * Method setsupplierAddress(), set value for supplierAddress field.
     * 
     * @param supplierAddress
     */
    public void setsupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }    
    
    /**
     *Method getsupplierPhone(), get value of supplierPhone field.
     *@return supplierPhone
     */
    public String getsupplierPhone() {
        return supplierPhone;
    }
    /**
     * Method setsupplierPhone(), set value for supplierPhone field.
     * 
     * @param supplierPhone
     */
    public void setsupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    } 
    
    /**
     *Method getsupplierFax(), get value of supplierFax field.
     *@return supplierFax
     */
    public String getsupplierFax() {
        return supplierFax;
    }
    /**
     * Method setsupplierFax(), set value for supplierFax field.
     * 
     * @param supplierFax
     */
    public void setsupplierFax(String supplierFax) {
        this.supplierFax = supplierFax;
    } 
    
    /**
     *Method getsupplierEmail(), get value of supplierEmail field.
     *@return supplierEmail
     */
    public String getsupplierEmail() {
        return supplierEmail;
    }
    /**
     * Method setsupplierFax(), set value for supplierEmail field.
     * 
     * @param supplierEmail
     */
    public void setsupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    } 
    
    /**
     *Method getsupplierWebsite(), get value of supplierWebsite field.
     *@return supplierWebsite
     */
    public String getsupplierWebsite() {
        return supplierWebsite;
    }
    /**
     * Method setsupplierFax(), set value for supplierWebsite field.
     * 
     * @param supplierWebsite
     */
    public void setsupplierWebsite(String supplierWebsite) {
        this.supplierWebsite = supplierWebsite;
    } 
    /**
     * Method getAllSupplier(), get all Supplier
     * @return Vector v
     * @throws java.sql.SQLException 
     */
    public static Vector getAllSupplier() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetAllSupplier");
            while(rs.next()){
                Suppliers objSupplier = new Suppliers();
                objSupplier.supplierCode = rs.getInt(1);
                objSupplier.supplierName = rs.getString(2);
                objSupplier.supplierfullName = rs.getString(3);
                objSupplier.supplierAddress = rs.getString(4);
                objSupplier.supplierPhone = rs.getString(5);
                objSupplier.supplierFax = rs.getString(6);
                objSupplier.supplierEmail = rs.getString(7);
                objSupplier.supplierWebsite = rs.getString(8);
                v.add(objSupplier);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    
    public static Vector searchSupplierByName(Vector name) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchSupplierByName",name);
            while(rs.next()){
                Suppliers objSupplier = new Suppliers();
                objSupplier.supplierCode = rs.getInt(1);
                objSupplier.supplierName = rs.getString(2);
                objSupplier.supplierAddress = rs.getString(3);
                objSupplier.supplierPhone = rs.getString(4);
                objSupplier.supplierEmail = rs.getString(5);
                v.add(objSupplier);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    
    public static Vector searchSupplierByAddress(Vector address) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchSupplierByAddress",address);
            while(rs.next()){
                Suppliers objSupplier = new Suppliers();
                objSupplier.supplierCode = rs.getInt(1);
                objSupplier.supplierName = rs.getString(2);
                objSupplier.supplierAddress = rs.getString(3);
                objSupplier.supplierPhone = rs.getString(4);
                objSupplier.supplierEmail = rs.getString(5);
                v.add(objSupplier);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    
    public static Vector searchSupplierAdvanced(Vector vlist) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spSearchAdvancedSupplier",vlist);
            while(rs.next()){
                Suppliers objSupplier = new Suppliers();
                objSupplier.supplierCode = rs.getInt(1);
                objSupplier.supplierName = rs.getString(2);
                objSupplier.supplierAddress = rs.getString(3);
                objSupplier.supplierPhone = rs.getString(4);
                objSupplier.supplierEmail = rs.getString(5);
                v.add(objSupplier);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return v;
    }
    
     /**
     * Method to insert into table Supplier
     * @return int i
     * @param objSupplier
     * @throws java.sql.SQLException
     */
    public static int insertSupplier(Suppliers objSupplier) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector();
            paramList.add(objSupplier.supplierName);
            paramList.add(objSupplier.supplierfullName);
            paramList.add(objSupplier.supplierAddress);
            paramList.add(objSupplier.supplierPhone);
            paramList.add(objSupplier.supplierFax);
            paramList.add(objSupplier.supplierEmail);
            paramList.add(objSupplier.supplierWebsite);            
            i = DBHelper.executeUpdate("spInsertSupplier",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
    /**
     * Method to update table Supplier
     * @return int i
     * @param objSupplier
     * @throws java.sql.SQLException
     */
    public static int updateSupplier(Suppliers objSupplier) throws SQLException {
        int i=0;
        try {            
            Vector paramList = new Vector();
            paramList.add(objSupplier.supplierCode);
            paramList.add(objSupplier.supplierName);
            paramList.add(objSupplier.supplierfullName);
            paramList.add(objSupplier.supplierAddress);
            paramList.add(objSupplier.supplierPhone);
            paramList.add(objSupplier.supplierFax);
            paramList.add(objSupplier.supplierEmail);
            paramList.add(objSupplier.supplierWebsite); 
            i = DBHelper.executeUpdate("spUpdateSupplier",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
    /**
     * Method to delete table Supplier
     * @return int i
     * @param supplierName
     * @throws java.sql.SQLException
     */
     public static int deleteSupplier(String supplierName) throws SQLException {
        int i=0;
        try {
            Vector paramList=new Vector();
            paramList.add(supplierName);
            i=DBHelper.executeUpdate("spDeleteSupplier",paramList);
        } catch(SQLException sqlex) {            
        }
        return i;
    }
}
