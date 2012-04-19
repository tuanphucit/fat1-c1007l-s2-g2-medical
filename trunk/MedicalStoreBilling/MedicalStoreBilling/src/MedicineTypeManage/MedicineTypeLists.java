/*
 * MedicineTypeLists.java
 *
 * Created on  April 14, 2012, 5:16 AM
 */

package MedicineTypeManage;
import ConnectDatabase.DBHelper;
import Check.Check;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author  
 */
public class MedicineTypeLists extends javax.swing.JPanel {
     private DefaultTableModel modelMedicineType = new DefaultTableModel();
    
    String nameTable = "MedicineType";
    
    Vector vLoad = new Vector();
    /** Creates new form MedicineTypeLists */
    public MedicineTypeLists() {
        initComponents();
        txtCode.setEnabled(false);
        try {
            vLoad = MedicineType.getAllMedicineType();
            this.loadTableMedicineType(nameTable,vLoad);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void loadTableMedicineType(String nameTable,Vector vLoad) throws SQLException{
        modelMedicineType.setNumRows(0);
        Vector columnMedicineType = new Vector();
        columnMedicineType.addElement("Code");
        columnMedicineType.addElement("MedicineTypeName");
        modelMedicineType.setColumnIdentifiers(columnMedicineType);
        MedicineType objMedicineType = new MedicineType();
        for(int i=0; i< vLoad.size();i++){
            objMedicineType = (MedicineType) vLoad.get(i);
            Vector rowMedicineType = new Vector();
            rowMedicineType.addElement(objMedicineType.getMedicineTypeCode());
            rowMedicineType.addElement(objMedicineType.getMedicineTypeName());
            modelMedicineType.addRow(rowMedicineType);
        }
        tableMedicineTypeCode.setModel(modelMedicineType);
    }
    
    public void checkMedicineTypeName(){
        String name = txtTypeName.getText().trim();       
        boolean test = false;
        try {
            ResultSet rs = DBHelper.executeQuery("spGetMedicineTypeName");
            while(rs.next()){
                if(name.equals(rs.getString("medicineTypeName"))){
                    txtTypeName.setText("");
                    txtTypeName.requestFocus();
                    test = true;                      
                }
            }
            if(test == true){
                JOptionPane.showMessageDialog(this,"Medicine type name existed !","Add new Medicine Type",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean checkMedicineType(){
        int test = 0;
        Check c = new Check();
        if(!c.checkSpace(txtTypeName.getText()) || !c.check(txtTypeName.getText())){
            JOptionPane.showMessageDialog(this,"Medicine Type Name Invalid","Add new Medicine Type",JOptionPane.ERROR_MESSAGE);
            txtTypeName.setText("");
            txtTypeName.requestFocus();
            return false;
        }
        return true;
    }
    
    public void addMedicineType(){ 
        this.checkMedicineTypeName();
        int test = 0;
        if(!checkMedicineType()){
            return;
        }
        MedicineType objMedicineType = new MedicineType();
        objMedicineType.setmedicineTypeName(txtTypeName.getText().trim());
        try {
            test = MedicineType.insertMedicineType(objMedicineType);
            if(test == 1){
                JOptionPane.showMessageDialog(this,"Add new Medicine Type Successfully !","Add new Medicine Type",JOptionPane.INFORMATION_MESSAGE);
                vLoad = MedicineType.getAllMedicineType();
                this.loadTableMedicineType(nameTable,vLoad);
            }else{
                JOptionPane.showMessageDialog(this,"Add new Medicine Type Error","Add new Medicine Type",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateMedicineType(){
        int test = 0;
        if(!checkMedicineType()){
            return;
        }
        MedicineType objMedicineType = new MedicineType();
        int medicineTypeCode = Integer.parseInt(txtCode.getText().trim());
        objMedicineType.setmedicineTypeCode(medicineTypeCode);
        objMedicineType.setmedicineTypeName(txtTypeName.getText().trim());
        try {
            test = MedicineType.updateMedicineTyp(objMedicineType);
            if(test == 1){
                JOptionPane.showMessageDialog(this,"Update Medicine Type Successfully !","Update Medicine Type",JOptionPane.INFORMATION_MESSAGE);
                vLoad = MedicineType.getAllMedicineType();
                this.loadTableMedicineType(nameTable,vLoad);
            }else
                JOptionPane.showMessageDialog(this,"Update Medicine Type Error !","Update Medicine Type",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteMedicineType(){
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Medicine Type ?","Remove Medicine Type",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableMedicineTypeCode.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    test = MedicineType.deleteMedicineType(tableMedicineTypeCode.getValueAt(rArr[i],1).toString());
                    if(test == 1)
                        count++;
                }
                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Medicine Type","Remove Medicine Type",JOptionPane.INFORMATION_MESSAGE);
                    vLoad = MedicineType.getAllMedicineType();
                    this.loadTableMedicineType(nameTable,vLoad);
                }else
                    JOptionPane.showMessageDialog(this,"Delete Medicine Type error !","Remove Medicine Type",JOptionPane.ERROR_MESSAGE);
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void resetForm(){
        txtCode.setText("");
        txtTypeName.setText("");
        txtTypeName.requestFocus();
    }   
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMedicineTypeCode = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTypeName = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine Type List"));

        tableMedicineTypeCode.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableMedicineTypeCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMedicineTypeCodeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableMedicineTypeCode);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
        );

        jLabel1.setText("Code");

        jLabel2.setText("Type Name");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/reset.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Update.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnAdd)
                .addGap(31, 31, 31)
                .addComponent(btnUpdate)
                .addGap(30, 30, 30)
                .addComponent(btnDelete)
                .addGap(30, 30, 30)
                .addComponent(btnReset)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(btnReset)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel1)
                        .addGap(21, 21, 21)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2)
                        .addGap(17, 17, 17)
                        .addComponent(txtTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTypeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
// TODO add your handling code here:
        this.resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
// TODO add your handling code here:
        this.deleteMedicineType();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// TODO add your handling code here:
        this.updateMedicineType();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
// TODO add your handling code here
    }//GEN-LAST:event_formAncestorAdded

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
        this.checkMedicineTypeName();
        this.addMedicineType();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tableMedicineTypeCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMedicineTypeCodeMouseClicked
// TODO add your handling code here:
         int r = tableMedicineTypeCode.getSelectedRow();
        if(r<0){
            return;
        }
        txtCode.setText(""+tableMedicineTypeCode.getValueAt(r,0));
        txtTypeName.setText(""+tableMedicineTypeCode.getValueAt(r,1));
    }//GEN-LAST:event_tableMedicineTypeCodeMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableMedicineTypeCode;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtTypeName;
    // End of variables declaration//GEN-END:variables
    
}
