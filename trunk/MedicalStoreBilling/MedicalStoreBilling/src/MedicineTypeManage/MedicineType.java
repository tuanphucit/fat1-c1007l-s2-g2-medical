/*
 * MedicineType.java
 *
 * Created on April 14, 2012, 1:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package MedicineTypeManage;
import ConnectDatabase.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author 
 */
public class MedicineType {
     private int medicineTypeCode;
    private String medicineTypeName;
    /** Creates a new instance of MedicineType */
    public MedicineType() {
    }
    
     /**
     *Method getMedicineTypeCode(), get value of medicineTypeCode field.
     *@return medicineTypeCode
     */
    public int getMedicineTypeCode() {
        return medicineTypeCode;
    }
    /**
     * Method setmedicineTypeCode(), set value for medicineTypeCode field.
     * 
     * @param medicineTypeCode 
     */
    public void setmedicineTypeCode(int medicineTypeCode) {
        this.medicineTypeCode = medicineTypeCode;
    }
    
    /**
     *Method getMedicineTypeName(), get value of medicineTypeName field.
     *@return medicineTypeName
     */
    public String getMedicineTypeName() {
        return medicineTypeName;
    }
    /**
     * Method setmedicineTypeName(), set value for medicineTypeName field.
     * 
     * @param medicineTypeName 
     */
    public void setmedicineTypeName(String medicineTypeName) {
        this.medicineTypeName = medicineTypeName;
    }
     /**
     * Method getAllMedicineType(), get all medicine type.
     * 
     * @return Vector v
     * @throws java.sql.SQLException 
     */
    public static Vector getAllMedicineType() throws SQLException{
        Vector v = new Vector();
        try {
            ResultSet rs = DBHelper.executeQuery("spGetAllMedicineType");
            while(rs.next()){
                MedicineType objMedicineType = new MedicineType();
                objMedicineType.medicineTypeCode = rs.getInt(1);
                objMedicineType.medicineTypeName = rs.getString(2);
                v.add(objMedicineType);
            }
        } catch (SQLException ex) {
            
        }
        return v;        
    }   
      
    /**
     * Method to insert into table MedicineType
     * @return int i
     * @param objMedicineType
     * @throws java.sql.SQLException
     */
    public static int insertMedicineType(MedicineType objMedicineType) throws SQLException {
        int i = 0;
        try {
            Vector paramList = new Vector();
            paramList.add(objMedicineType.medicineTypeName);           
            i = DBHelper.executeUpdate("spInsertMedicineType",paramList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
     /**
     * Method to update table MedicineType
     * @return int i
     * @param objMedicineType
     * @throws java.sql.SQLException
     */
    public static int updateMedicineTyp(MedicineType objMedicineType) throws SQLException {
        int i=0;
        try {            
            Vector paramList = new Vector();
            paramList.add(objMedicineType.medicineTypeCode);
            paramList.add(objMedicineType.medicineTypeName);            
            i = DBHelper.executeUpdate("spUpdateMedicineType",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
     /**
     * Method to delete table MedicineType
     * @return int i
     * @param medicineTypeName
     * @throws java.sql.SQLException
     */
    public static int deleteMedicineType(String medicineTypeName) throws SQLException {
        int i=0;
        try {
            Vector paramList=new Vector();            
            paramList.add(medicineTypeName);
            i=DBHelper.executeUpdate("spDeleteMedicineType",paramList);
        } catch(SQLException sqlex) {
            
        }
        return i;
    }
    
}
