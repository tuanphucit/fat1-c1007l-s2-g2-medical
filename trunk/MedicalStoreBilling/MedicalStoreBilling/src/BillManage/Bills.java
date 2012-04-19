/*
 * Bills.java
 *
 * Created on  April 9, 2012, 8:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package BillManage;
import ConnectDatabase.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author 
 */
public class Bills {
    
    /** Creates a new instance of Bills */
    
    private int billCode;
    private String billType;
    private int customerCode;
    private String addressToDeliver;
    private String dateStart;
    private String expiredTime;
    private float tax;
    private float price;
    private String status;
    private int userCode;
    
    
    private int medicineCode;
    private int measureCode;
    private int quantity;
    
    private float payedMoney;
    private String datePay;
    
    private String customerName;
    private String userName;
    private String relationship;
    
    public Bills() {
    }
    
    public int getmedicineCode(){
        return medicineCode;
    }
    public void setmedicineCode(int medicineCode){
        this.medicineCode = medicineCode;
    }
    
    public int getmeasureCode(){
        return measureCode;
    }
    public void setmeasureCode(int measureCode){
        this.measureCode = measureCode;
    }
    
    public int getquantity(){
        return quantity;
    }
    public void setquantity(int quantity){
        this.quantity = quantity;
    }
    
    
    
    //-------------------------------
    public int getbillCode(){
        return billCode;
    }
    public void setbillCode(int billCode){
        this.billCode = billCode;
    }
    //-------------------------------
    public int getcustomerCode(){
        return customerCode;
    }
    public void setcustomerCode(int customerCode){
        this.customerCode = customerCode;
    }
    //-------------------------------
    public String getaddressToDeliver(){
        return addressToDeliver;
    }
    public void setaddressToDeliver(String addressToDeliver){
        this.addressToDeliver = addressToDeliver;
    }
    //-------------------------------
    public String getdateStart(){
        return dateStart;
    }
    public void setdateStart(String dateStart){
        this.dateStart = dateStart;
    }
    //-------------------------------
    public String getexpiredTime(){
        return expiredTime;
    }
    public void setexpiredTime(String expiredTime){
        this.expiredTime = expiredTime;
    }
    //-------------------------------
    public float gettax(){
        return tax;
    }
    public void settax(float tax){
        this.tax = tax;
    }
    //-------------------------------
    public float getprice(){
        return price;
    }
    public void setprice(float price){
        this.price = price;
    }
    //-------------------------------
    public String getstatus(){
        return status;
    }
    public void setstatus(String status){
        this.status = status;
    }
    //-------------------------------
    public int getuserCode(){
        return userCode;
    }
    public void setuserCode(int userCode){
        this.userCode = userCode;
    }
    //------------
    //-------------------------------
    public String getbillType(){
        return billType;
    }
    public void setbillType(String billType){
        this.billType = billType;
    }
    
    public float getpayedMoney(){
        return payedMoney;
    }
    public void setpayedMoney(float payedMoney){
        this.payedMoney = payedMoney;
    }
    
    public String getdatePay(){
        return datePay;
    }
    public void setdatePay(String datePay){
        this.datePay = datePay;
    }
    
    public String getcustomerName(){
        return customerName;
    }
    public void setcustomerName(String customerName){
        this.customerName = customerName;
    }
    
    public String getrelationship(){
        return relationship;
    }
    public void setrelationship(String relationship){
        this.relationship = relationship;
    }
    
    public String getuserName(){
        return userName;
    }
    public void setuserName(String userName){
        this.userName = userName;
    }
    
    public static int insertBill(Bills objBill) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector();
            paramList.add(objBill.billType);
            paramList.add(objBill.customerCode);
            paramList.add(objBill.addressToDeliver);
            paramList.add(objBill.dateStart);
            paramList.add(objBill.expiredTime);
            paramList.add(objBill.tax);
            paramList.add(objBill.status);
            paramList.add(objBill.userCode);
            i = DBHelper.executeUpdate("spInsertNewBill",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
    
    public static int insertBillDetails(Bills objBill) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector();
            paramList.add(objBill.billCode);
            paramList.add(objBill.medicineCode);
            paramList.add(objBill.measureCode);
            paramList.add(objBill.quantity);
            i = DBHelper.executeUpdate("spInsertBillDetails",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
    
    public static Vector getAllBills() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetAllBill");
            while(rs.next()){
                Bills objBill = new Bills();
                objBill.billCode = rs.getInt(1);
                objBill.billType = rs.getString(2);
                objBill.customerCode = rs.getInt(3);
                objBill.addressToDeliver = rs.getString(4);
                objBill.dateStart = rs.getString(5);
                objBill.expiredTime = rs.getString(6);
                objBill.tax = rs.getFloat(7);
                objBill.price = rs.getFloat(8);
                objBill.status = rs.getString(9);
                objBill.userCode = rs.getInt(10);
                v.add(objBill);
            }
        } catch (SQLException ex) {
            
        }
        return v;
    }
    
    public static Vector getAllBillsWaitting(Vector vlist) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetAllBillWaitting",vlist);
            while(rs.next()){
                Bills objBill = new Bills();
                objBill.billCode = rs.getInt(1);
                objBill.customerCode = rs.getInt(2);
                objBill.billType = rs.getString(3);
                objBill.price = rs.getFloat(4);
                objBill.status = rs.getString(5);
                v.add(objBill);
            }
        } catch (SQLException ex) {
            
        }
        return v;
    }
    
    public static Vector getPayedMoney(Vector vlist) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetPayedMoney",vlist);
            while(rs.next()){
                Bills objBill = new Bills();
                objBill.payedMoney = rs.getFloat(1);
                v.add(objBill);
            }
        } catch (SQLException ex) {
            
        }
        return v;
    }
    
    public static Vector getAllBillNotDetails() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("vspGetAllBillNotDetails");
            while(rs.next()){
                Bills objBill = new Bills();
                objBill.billCode = rs.getInt(1);
                objBill.billType = rs.getString(2);
                objBill.customerCode = rs.getInt(3);
                objBill.dateStart = rs.getString(4);
                objBill.expiredTime = rs.getString(5);
                objBill.tax = rs.getFloat(6);
                objBill.addressToDeliver = rs.getString(7);
                v.add(objBill);
            }
        } catch (SQLException ex) {
            
        }
        return v;
    }
    
    public static int updatePriceForBill(Bills objBill) throws SQLException {
        int i=0;
        try {
            Vector paramList = new Vector();
            paramList.add(objBill.billCode);
            paramList.add(objBill.price);
            i = DBHelper.executeUpdate("spUpdatePriceForBill",paramList);
        } catch(SQLException sqlex) {
        }
        return i;
    }
    
    public static int insertPay(Bills objBill) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector();
            paramList.add(objBill.billCode);
            paramList.add(objBill.datePay);
            paramList.add(objBill.payedMoney);
            i = DBHelper.executeUpdate("spInsertPaypedReport",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
    
    public static int updateStatusForBill(Bills objBill) throws SQLException {
        int i=0;
        try {
            Vector paramList = new Vector();
            paramList.add(objBill.billCode);
            paramList.add(objBill.status);
            i = DBHelper.executeUpdate("spUpdateStatusForBill",paramList);
        } catch(SQLException sqlex) {
        }
        return i;
    }
    
    public static Vector getBillsWaittingForReport() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("vspGetAllBillWaitting");
            while(rs.next()){
                Bills objBill = new Bills();
                objBill.billType = rs.getString(1);
                objBill.customerName = rs.getString(2);
                objBill.relationship = rs.getString(3);
                objBill.price = rs.getFloat(5);
                objBill.status = rs.getString(6);
                objBill.userName = rs.getString(7);
                v.add(objBill);
            }
        } catch (SQLException ex) {
            
        }
        return v;
    }
    
    public static Vector getBillsOverdueForReport() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("vspGetAllBillOverdue");
            while(rs.next()){
                Bills objBill = new Bills();
                objBill.billType = rs.getString(1);
                objBill.customerName = rs.getString(2);
                objBill.relationship = rs.getString(3);
                objBill.expiredTime=rs.getString(4);
                objBill.price = rs.getFloat(5);
                objBill.status = rs.getString(6);
                objBill.userName = rs.getString(7);
                v.add(objBill);
            }
        } catch (SQLException ex) {
            
        }
        return v;
    }
    
    public static Vector getBillsCompleteForReport() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("vspGetAllBillComplete");
            while(rs.next()){
                Bills objBill = new Bills();
                objBill.billCode = rs.getInt(1);
                objBill.billType = rs.getString(2);
                objBill.customerName = rs.getString(3);
                objBill.relationship = rs.getString(4);
                objBill.price = rs.getFloat(6);
                objBill.status = rs.getString(7);
                objBill.userName = rs.getString(8);
                v.add(objBill);
            }
        } catch (SQLException ex) {
            
        }
        return v;
    }
    
       public static int deleteBill(int billCode) throws SQLException {
        int i=0;
        try {
            Vector paramList=new Vector();
            paramList.add(billCode);
            i=DBHelper.executeUpdate("spDeleteBill",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
    
}
