/*
 * MeasureList.java
 *
 * Created on September 11, 2009, 9:27 PM
 */

package MeasureManage;
import ConnectDatabase.DBHelper;
import Check.Check;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author  TranVanHien
 */
public class MeasureList extends javax.swing.JPanel {
     private DefaultTableModel modelMeasure = new DefaultTableModel();
    
    String nameTable = "Measure";
    
    Vector vLoad = new Vector();
    /** Creates new form MeasureList */
    public MeasureList() {
        initComponents();
        txtCode.setEnabled(false);
        try {
            vLoad = Measures.getAllMeasure();
            this.loadTableMeasure(nameTable,vLoad);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     public void loadTableMeasure(String nameTable,Vector vLoad) throws SQLException{
        modelMeasure.setNumRows(0);
        Vector columnMeasure = new Vector();
        columnMeasure.addElement("Code");
        columnMeasure.addElement("MedicineTypeName");
        modelMeasure.setColumnIdentifiers(columnMeasure);
        Measures objMeasure = new Measures();
        for(int i=0; i< vLoad.size();i++){
            objMeasure = (Measures) vLoad.get(i);
            Vector rowMeasure = new Vector();
            rowMeasure.addElement(objMeasure.getmeasureCode());
            rowMeasure.addElement(objMeasure.getmeasureName());
            modelMeasure.addRow(rowMeasure);
        }
        tableMeasure.setModel(modelMeasure);
    }
    
     public void checkMeasureName(){
        String name = txtMeasure.getText().trim();       
        boolean test = false;
        try {
            ResultSet rs = DBHelper.executeQuery("spGetMeasureName");
            while(rs.next()){
                if(name.equals(rs.getString("measureName"))){
                    txtMeasure.setText("");
                    txtMeasure.requestFocus();
                    test = true;                      
                }
            }
            if(test == true){
                JOptionPane.showMessageDialog(this,"Measure name existed !","Add new Measure",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean checkMeasure(){
        int test = 0;
        Check c = new Check();
        if(!c.checkSpace(txtMeasure.getText()) || !c.check(txtMeasure.getText()) || !c.checkNhay(txtMeasure.getText())){
            JOptionPane.showMessageDialog(this,"Measure Name Invalid","Add new Measure",JOptionPane.ERROR_MESSAGE);
            txtMeasure.setText("");
            txtMeasure.requestFocus();
            return false;
        }
        return true;
    }
    
    public void addMeasure(){
        this.checkMeasureName();
        int test = 0;
        if(!checkMeasure()){
        return;
        }
        Measures objMeasure = new Measures();
        objMeasure.setmeasureName(txtMeasure.getText().trim());
        try {
            test = Measures.insertMeasure(objMeasure);
            if(test == 1){
                JOptionPane.showMessageDialog(this,"Add new Measure Successfully !","Add new Measure",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Measures.getAllMeasure();
                this.loadTableMeasure(nameTable,vLoad);
            }else{
                JOptionPane.showMessageDialog(this,"Add new Measure Error","Add new Measure",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateMeasure(){
        int test = 0;
        if(!checkMeasure()){
            return;
        }
        Measures objMeasure = new Measures();
        int measureCode = Integer.parseInt(txtCode.getText().trim());
        objMeasure.setmeasureCode(measureCode);
        objMeasure.setmeasureName(txtMeasure.getText().trim());
        try {
            test = Measures.updateMeasure(objMeasure);
            if(test == 1){
                JOptionPane.showMessageDialog(this,"Update Measure Successfully !","Update Measure",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Measures.getAllMeasure();
                this.loadTableMeasure(nameTable,vLoad);
            }else
                JOptionPane.showMessageDialog(this,"Update Measure Error !","Update Measure",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteMeasure(){
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Measure ?","Remove Measure",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableMeasure.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    test = Measures.deleteMeasure(tableMeasure.getValueAt(rArr[i],1).toString());
                    if(test == 1)
                        count++;
                }
                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Measure","Remove Measure",JOptionPane.INFORMATION_MESSAGE);
                    vLoad = Measures.getAllMeasure();
                    this.loadTableMeasure(nameTable,vLoad);
                }else
                    JOptionPane.showMessageDialog(this,"Delete Measure error !","Remove Measure",JOptionPane.ERROR_MESSAGE);
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void resetForm(){
        txtCode.setText("");
        txtMeasure.setText("");
        txtCode.requestFocus();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMeasure = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMeasure = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Measure List"));
        tableMeasure.setModel(new javax.swing.table.DefaultTableModel(
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
        tableMeasure.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMeasureMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(tableMeasure);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel1.setText("Code");

        jLabel2.setText("Measure Name");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add.png")));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Update.png")));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png")));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/reset.png")));
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd)
                .addGap(24, 24, 24)
                .addComponent(btnUpdate)
                .addGap(31, 31, 31)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btnReset)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAdd)
                .addComponent(btnReset)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/MeasureList.png")));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel2)
                        .addGap(17, 17, 17)
                        .addComponent(txtMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(33, 33, 33))
            .addGroup(layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(txtMeasure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel3)))
                .addGap(29, 29, 29)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// TODO add your handling code here:
        this.updateMeasure();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
// TODO add your handling code here:
        this.deleteMeasure();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
        this.addMeasure();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
// TODO add your handling code here:
        this.resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
// TODO add your handling code here:
    }//GEN-LAST:event_formAncestorAdded

    private void tableMeasureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMeasureMouseClicked
// TODO add your handling code here:
          int r = tableMeasure.getSelectedRow();
        if(r<0){
            return;
        }
        txtCode.setText(""+tableMeasure.getValueAt(r,0));
        txtMeasure.setText(""+tableMeasure.getValueAt(r,1));
    }//GEN-LAST:event_tableMeasureMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableMeasure;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtMeasure;
    // End of variables declaration//GEN-END:variables
    
}
