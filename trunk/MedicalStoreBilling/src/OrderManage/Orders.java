/*
 * Orders.java
 *
 * Created on April 10, 2012, 7:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package OrderManage;
import ConnectDatabase.DBHelper;
import GUI.Main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
/**
 *
 * @author 
 */
public class Orders {
    private int measureCode;
    private int orderCode;
    private int customerCode;
    private String dateOrder;
    private int userCode;
    private String addressToDeliver;
    private float priceOrder;
    
    private int medicineCode;
    private String medicineName;
    private String medicineTypeName;
    private String measureName;
    private float pricePerUnit;
    private int quantity;
    /** Creates a new instance of Orders */
    public Orders() {
    }
    public int getquantity(){
        return quantity;
    }
    public void setquantity(int quantity){
        this.quantity = quantity;
    }
    
    public float getpricePerUnit(){
        return pricePerUnit;
    }
    public void setpricePerUnit(float pricePerUnit){
        this.pricePerUnit = pricePerUnit;
    }
    
    public int getmedicineCode(){
        return medicineCode;
    }
    public void setmedicineCode(int medicineCode){
        this.medicineCode = medicineCode;
    }    
    
    public String getmedicineName(){
        return medicineName;
    }
    public void setmedicineName(String medicineName){
        this.medicineName = medicineName;
    }
    
    public String getmedicineTypeName(){
        return medicineTypeName;
    }
    public void setmedicineTypeName(String medicineTypeName){
        this.medicineTypeName = medicineTypeName;
    }
    
    public int getmeasureCode(){
        return measureCode;
    }
    public void setmeasureCode(int measureCode){
        this.measureCode = measureCode;
    }
    
    public String getmeasureName(){
        return measureName;
    }
    public void setmeasureName(String measureName){
        this.measureName = measureName;
    }
    
    //-------------------------------------------------
    
    public int getOderCode(){
        return orderCode;
    }
    public void setOrderCode(int orderCode){
        this.orderCode = orderCode;
    }
    
    public int getcustomerCode(){
        return customerCode;
    }
    public void setcustomerCode(int customerCode){
        this.customerCode = customerCode;
    }   
    
    //----------------------
    public int getuserCode(){
        return userCode;
    }
    public void setuserCode(int userCode){
        this.userCode = userCode;
    }
    
    public String getdateOrder(){
        return dateOrder;
    }
    public void setdateOrder(String dateOrder){
        this.dateOrder = dateOrder;
    }
    
    public String getaddressToDeliver(){
        return addressToDeliver;
    }
    public void setaddressToDeliver(String addressToDeliver){
        this.addressToDeliver = addressToDeliver;
    }
    
    public float getpriceOrder(){
        return priceOrder;
    }
    public void setpriceOrder(float priceOrder){
        this.priceOrder = priceOrder;
    }
        
     public static int insertOrders(Orders objOrders) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector(); 
            paramList.addElement(objOrders.customerCode);
            paramList.addElement(objOrders.dateOrder);
            paramList.addElement(objOrders.userCode);
            paramList.addElement(objOrders.addressToDeliver);            
            i = DBHelper.executeUpdate("spInsertOrders",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    } 
    
    public static Vector getAllOrders() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetAllOrders");
            while(rs.next()){
                Orders objOrder = new Orders();
                objOrder.orderCode = rs.getInt(1);
                objOrder.customerCode = rs.getInt(2);
                objOrder.dateOrder = rs.getString(3);
                objOrder.userCode = rs.getInt(4);
                objOrder.addressToDeliver = rs.getString(5);
                objOrder.priceOrder = rs.getFloat(6);
                v.add(objOrder);
            }
        } catch (SQLException ex) {
            
        }
        return v;        
    }    
    
    
    public static Vector getAllOrdersNotDetails() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("vspGetOrderNotDetails");
            while(rs.next()){
                Orders objOrder = new Orders();
                objOrder.orderCode = rs.getInt(1);
                objOrder.customerCode = rs.getInt(2);
                objOrder.dateOrder = rs.getString(3);
                objOrder.addressToDeliver = rs.getString(4);
                v.add(objOrder);
            }
        } catch (SQLException ex) {
            
        }
        return v;        
    }    
    
    
    public static Vector getOrderByCustomerCode(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetOrderByCustomerCode",vList);
            while(rs.next()){
                Orders objOrder = new Orders();
                objOrder.orderCode = rs.getInt(1);
                objOrder.customerCode = rs.getInt(2);
                objOrder.dateOrder = rs.getString(3);
                objOrder.userCode = rs.getInt(4);
                objOrder.addressToDeliver = rs.getString(5);
                objOrder.priceOrder = rs.getFloat(6);
                v.add(objOrder);
            }
        } catch (SQLException ex) {
            
        }
        return v;        
    }    
    
    public static Vector getOrderDetailsByOrderCode(Vector vList) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("vspOrderDetailsByOrderCode",vList);
            while(rs.next()){
                Orders objOrder = new Orders();
                objOrder.orderCode = rs.getInt(1);
                objOrder.medicineCode = rs.getInt(2);
                objOrder.medicineName = rs.getString(3);
                objOrder.medicineTypeName = rs.getString(4);
                objOrder.measureName = rs.getString(5);
                objOrder.pricePerUnit = rs.getFloat(6);
                objOrder.quantity = rs.getInt(7);
                v.add(objOrder);
            }
        } catch (SQLException ex) {
            
        }
        return v;        
    }    
    
    
    public static int deleteOrder(int orderCode) throws SQLException {
        int i=0;
        try {
            Vector paramList=new Vector();
            paramList.add(orderCode);
            i=DBHelper.executeUpdate("spDeleteOrder",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
    
    public static int insertOrdersDetails(Orders objOrders) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector(); 
            paramList.addElement(objOrders.orderCode);
            paramList.addElement(objOrders.medicineCode);
            paramList.addElement(objOrders.measureCode);
            paramList.addElement(objOrders.quantity);            
            i = DBHelper.executeUpdate("spInsertOrderDetails",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    } 
    
     public static int updatePriceForOrders(Orders objOrders) throws SQLException {
        int i=0;
        try {            
            Vector paramList = new Vector();
            paramList.add(objOrders.orderCode);
            paramList.add(objOrders.priceOrder);
            i = DBHelper.executeUpdate("spUpdatePriceForOrder",paramList);
        } catch(SQLException sqlex) {            
        }
        return i;
    }
}
