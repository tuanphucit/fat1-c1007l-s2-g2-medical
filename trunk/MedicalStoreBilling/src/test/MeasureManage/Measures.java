/*
 * Measures.java
 *
 * Created on September 11, 2009, 1:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package MeasureManage;
import ConnectDatabase.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author TranVanHien
 */
public class Measures {
    
    /** Creates a new instance of Measures */
    private int measureCode;
    private String measureName;
    
    public Measures() {
    }
    
    /**
     *Method getmeasureCode(), get value of measureCode field.
     *@return measureCode
     */
    public int getmeasureCode() {
        return measureCode;
    }
    /**
     * Method setmeasureCode(), set value for measureCode field.
     * 
     * @param measureCode 
     */
    public void setmeasureCode(int measureCode) {
        this.measureCode = measureCode;
    }
    
    
    /**
     *Method getmeasureName(), get value of measureName field.
     *@return measureName
     */
    public String getmeasureName() {
        return measureName;
    }
    /**
     * Method setmeasureName(), set value for measureName field.
     * 
     * @param measureName 
     */
    public void setmeasureName(String measureName) {
        this.measureName = measureName;
    }
    
    /**
     * Method getAllMeasure(), get all Measure type.
     * 
     * @return Vector v
     * @throws java.sql.SQLException 
     */
    public static Vector getAllMeasure() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetAllMeasure");
            while(rs.next()){
                Measures objMeasure = new Measures();
                objMeasure.measureCode= rs.getInt(1);
                objMeasure.measureName = rs.getString(2);
                v.add(objMeasure);
            }
        } catch (SQLException ex) {            
        }
        return v;        
    }
    
     public static Vector getMeasureCodeByName(Vector vlist) throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetMeasureCodeByName",vlist);
            while(rs.next()){
                Measures objMeasure = new Measures();
                objMeasure.measureCode= rs.getInt(1);
                v.add(objMeasure);
            }
        } catch (SQLException ex) {            
        }
        return v;        
    }
    
     /**
     * Method to insert into table Measure
     * @return int i
     * @param objMeasure
     * @throws java.sql.SQLException
     */
    public static int insertMeasure(Measures objMeasure) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector();
            paramList.add(objMeasure.measureName);           
            i = DBHelper.executeUpdate("spInsertMeasure",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
    
     /**
     * Method to update table Measure
     * @return int i
     * @param objMeasure
     * @throws java.sql.SQLException
     */
    public static int updateMeasure(Measures objMeasure) throws SQLException {
        int i=0;
        try {            
            Vector paramList = new Vector();
            paramList.add(objMeasure.measureCode);
            paramList.add(objMeasure.measureName);            
            i = DBHelper.executeUpdate("spUpdateMeasure",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
     /**
     * Method to delete table Measure
     * @return int i
     * @param measureName
     * @throws java.sql.SQLException
     */
    public static int deleteMeasure(String measureName) throws SQLException {
        int i=0;
        try {
            Vector paramList=new Vector();            
            paramList.add(measureName);
            i=DBHelper.executeUpdate("spDeleteMeasure",paramList);
        } catch(SQLException sqlex) {            
        }
        return i;
    }
}
