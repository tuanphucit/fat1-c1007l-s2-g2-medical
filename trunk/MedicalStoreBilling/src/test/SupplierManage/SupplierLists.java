/*
 * SupplierLists.java
 *
 * Created on April 11, 2012, 9:30 PM
 */

package SupplierManage;
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
public class SupplierLists extends javax.swing.JPanel {
    private DefaultTableModel modelSupplier = new DefaultTableModel();
    
    String nameTable = "Supplier";
    
    Vector vLoad = new Vector();
    /** Creates new form SupplierLists */
    public SupplierLists() {
        initComponents();
         try {
            txtCode.setEnabled(false);
            vLoad = Suppliers.getAllSupplier();
            this.loadTableSupplier(nameTable,vLoad);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     public void loadTableSupplier(String nameTable,Vector vLoad) throws SQLException{
        modelSupplier.setNumRows(0);
        Vector columnSupplier = new Vector();
        
        columnSupplier.addElement("Code");
        columnSupplier.addElement("SupplierName");
        columnSupplier.addElement("FullName");
        columnSupplier.addElement("Address");
        columnSupplier.addElement("Phone");
        columnSupplier.addElement("Fax");
        columnSupplier.addElement("Email");
        columnSupplier.addElement("Website");        
        modelSupplier.setColumnIdentifiers(columnSupplier);        
        Suppliers objSuppier = new Suppliers();
        for(int i=0; i< vLoad.size();i++){
            objSuppier = (Suppliers) vLoad.get(i);
            Vector rowSupplier = new Vector();
            rowSupplier.addElement(objSuppier.getsupplierCode());
            rowSupplier.addElement(objSuppier.getsupplierName());
            rowSupplier.addElement(objSuppier.getsupplierFullname());
            rowSupplier.addElement(objSuppier.getsupplierAddress());
            rowSupplier.addElement(objSuppier.getsupplierPhone());
            rowSupplier.addElement(objSuppier.getsupplierFax());
            rowSupplier.addElement(objSuppier.getsupplierEmail());
            rowSupplier.addElement(objSuppier.getsupplierWebsite());
            modelSupplier.addRow(rowSupplier);
        }
        tableSupplier.setModel(modelSupplier);
    }
    public void checkSupplierName(){       
        String name = txtName.getText().trim();       
        boolean test = false;
        try {
            ResultSet rs = DBHelper.executeQuery("spGetSupplierName");
            while(rs.next()){
                if(name.equals(rs.getString("supplierName"))){
                    txtName.setText("");
                    txtName.requestFocus();
                    test = true;                      
                }
            }
            if(test == true){
                JOptionPane.showMessageDialog(this,"Supplier Name existed !","Add new Supplier",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public boolean checkSupplier(){        
        Check c= new Check();
        if(!c.checkSpace(txtName.getText()) || !c.check(txtName.getText())){
            JOptionPane.showMessageDialog(this,"Suppier Name Invalid","Add new supplier",JOptionPane.ERROR_MESSAGE);
            txtName.setText("");
            txtName.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtFullName.getText()) || !c.check(txtFullName.getText())){
            JOptionPane.showMessageDialog(this,"Suppier FullName Invalid","Add new supplier",JOptionPane.ERROR_MESSAGE);
            txtFullName.setText("");
            txtFullName.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtAddress.getText()) || !c.check(txtAddress.getText())){
            JOptionPane.showMessageDialog(this,"Suppier Address Invalid","Add new supplier",JOptionPane.ERROR_MESSAGE);
            txtAddress.setText("");
            txtAddress.requestFocus();
            return false;
        }
        else if(!c.checkPhone(txtPhone.getText())){
            JOptionPane.showMessageDialog(this,"Suppier Phone Invalid","Add new supplier",JOptionPane.ERROR_MESSAGE);
            txtPhone.setText("");
            txtPhone.requestFocus();
            return false;
        }
        else if(!c.checkPhone(txtFax.getText())){
            JOptionPane.showMessageDialog(this,"Suppier Fax Invalid","Add new supplier",JOptionPane.ERROR_MESSAGE);
            txtFax.setText("");
            txtFax.requestFocus();
            return false;
        }
        else if(!c.checkEmail(txtEmail.getText())){
            JOptionPane.showMessageDialog(this,"Suppier Email Invalid","Add new supplier",JOptionPane.ERROR_MESSAGE);
            txtEmail.setText("");
            txtEmail.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtWebsite.getText()) || !c.check(txtWebsite.getText()) || !c.checkWebsite(txtWebsite.getText())){
            JOptionPane.showMessageDialog(this,"Suppier Website Invalid","Add new supplier",JOptionPane.ERROR_MESSAGE);
            txtWebsite.setText("");
            txtWebsite.requestFocus();
            return false;
        }
        return true;
    }
    
    public void addSupplier(){
        int test = 0;
        if(!checkSupplier()){
            return;
        }         
        Suppliers objSuppier = new Suppliers();
        objSuppier.setsupplierName(txtName.getText().trim());
        objSuppier.setsupplierFullname(txtFullName.getText().trim());
        objSuppier.setsupplierAddress(txtAddress.getText().trim());
        objSuppier.setsupplierPhone(txtPhone.getText().trim());
        objSuppier.setsupplierFax(txtFax.getText().trim());
        objSuppier.setsupplierEmail(txtEmail.getText().trim());
        objSuppier.setsupplierWebsite(txtWebsite.getText().trim());
        try {
            test = Suppliers.insertSupplier(objSuppier);
            if(test ==1){
                JOptionPane.showMessageDialog(this,"Add new Supplier successfully !","Add new Supplier",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Suppliers.getAllSupplier();
                this.loadTableSupplier(nameTable,vLoad);
            }else
                JOptionPane.showMessageDialog(this,"Add new Supplier error","Add new Supplier",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void updateSupplier(){
        int test = 0;
        if(!this.checkSupplier()){
            return;
        }
        Suppliers objSupplier = new Suppliers();
        int supplierCode = Integer.parseInt(txtCode.getText().trim());
        objSupplier.setsupplierCode(supplierCode);
        objSupplier.setsupplierName(txtName.getText().trim());
        objSupplier.setsupplierFullname(txtFullName.getText().trim());
        objSupplier.setsupplierAddress(txtAddress.getText().trim());
        objSupplier.setsupplierPhone(txtPhone.getText().trim());
        objSupplier.setsupplierFax(txtFax.getText().trim());
        objSupplier.setsupplierEmail(txtEmail.getText().trim());
        objSupplier.setsupplierWebsite(txtWebsite.getText().trim());
        
        try {            
            test = Suppliers.updateSupplier(objSupplier);
            if(test == 1){
                JOptionPane.showMessageDialog(this,"Update Supplier successfully !","Update Supplier",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Suppliers.getAllSupplier();
                this.loadTableSupplier(nameTable,vLoad);
            }else
                JOptionPane.showMessageDialog(this,"Update Supplier error","Update Supplier",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteSupplier(){
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Supplier ?","Remove Supplier",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableSupplier.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    test = Suppliers.deleteSupplier(tableSupplier.getValueAt(rArr[i],1).toString());
                    if(test == 1)
                        count++;
                }
                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Supplier","Remove User",JOptionPane.INFORMATION_MESSAGE);
                    vLoad = Suppliers.getAllSupplier();
                    this.loadTableSupplier(nameTable,vLoad);
                }else
                    JOptionPane.showMessageDialog(this,"Delete Supplier error !","Remove Supplier",JOptionPane.ERROR_MESSAGE);
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void resetForm(){
        txtName.setText("");
        txtFullName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtFax.setText("");
        txtEmail.setText("");
        txtWebsite.setText("");       
        
        txtName.requestFocus();
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
        tableSupplier = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtFullName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtFax = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtWebsite = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier List"));
        tableSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tableSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSupplierMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(tableSupplier);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier Information Details"));
        jLabel1.setText("Code");

        jLabel2.setText("Name");

        jLabel3.setText("FullName");

        jLabel4.setText("Phone");

        jLabel5.setText("Fax");

        jLabel6.setText("Address");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane2.setViewportView(txtAddress);

        jLabel7.setText("Email");

        jLabel8.setText("WebSite");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFax)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFullName)
                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(txtPhone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtWebsite)
                    .addComponent(txtEmail)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtWebsite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/reset.png")));
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png")));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Update.png")));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add.png")));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnAdd)
                .addGap(32, 32, 32)
                .addComponent(btnUpdate)
                .addGap(32, 32, 32)
                .addComponent(btnDelete)
                .addGap(29, 29, 29)
                .addComponent(btnReset)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnAdd)
                .addComponent(btnDelete)
                .addComponent(btnReset))
        );

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/supplier-128.png")));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
// TODO add your handling code here:
        this.resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
// TODO add your handling code here:
        this.deleteSupplier();
        this.resetForm();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// TODO add your handling code here:
        this.updateSupplier();
        this.resetForm();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
        this.checkSupplierName();
        this.addSupplier();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tableSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSupplierMouseClicked
// TODO add your handling code here:
        int r = tableSupplier.getSelectedRow();
        if(r < 0){
            return;
        }
        txtCode.setText(""+tableSupplier.getValueAt(r,0));
        txtName.setText(""+tableSupplier.getValueAt(r,1));
        txtFullName.setText(""+tableSupplier.getValueAt(r,2));
        txtAddress.setText(""+tableSupplier.getValueAt(r,3));
        txtPhone.setText(""+tableSupplier.getValueAt(r,4));
        txtFax.setText(""+tableSupplier.getValueAt(r,5));
        txtEmail.setText(""+tableSupplier.getValueAt(r,6));
        txtWebsite.setText(""+tableSupplier.getValueAt(r,7));
    }//GEN-LAST:event_tableSupplierMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableSupplier;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtWebsite;
    // End of variables declaration//GEN-END:variables
    
}
