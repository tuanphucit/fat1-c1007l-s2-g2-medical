/*
 * NewBill.java
 *
 * Created on  April 12, 2012, 11:08 PM
 */

package BillManage;
import Check.Check;
import CustomerManage.Customer;
import GUI.Main;
import UserManage.Users;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author  
 */
public class NewBill extends javax.swing.JPanel {
     private DefaultComboBoxModel cbmdCustomerName = new DefaultComboBoxModel();
    Customer objCustomer = new Customer();
    private DefaultTableModel modelTableBill = new DefaultTableModel();
    Vector vLoadBillNoDetails = new Vector();
    
    String nameTable = "Bill";
    /** Creates new form NewBill */
    public NewBill() {
        super();
        initComponents();
        Vector vCustomer=new Vector();
        try {
            vCustomer=Customer.getAllCustomer();
            cbmdCustomerName.removeAllElements();
            cbmdCustomerName.addElement("---- Choice Customer ----");

            for(int j=0;j<vCustomer.size();j++) {
                objCustomer=(Customer)vCustomer.get(j);
                cbmdCustomerName.addElement(objCustomer.getcustomerName());
            }
            cboCustomerName.setModel(cbmdCustomerName);
            
            vLoadBillNoDetails = Bills.getAllBillNotDetails();
            this.loadTableBillNotDetails("vBillNotDetails",vLoadBillNoDetails);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public boolean checkNewBill(){
        Check c = new Check();
        if(cboCustomerName.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(this,"Customer not selected !","Create new order",JOptionPane.ERROR_MESSAGE);           
            return false;
        }
        else if(!c.checkDateIn(txfDateStart.getText())){
            JOptionPane.showMessageDialog(this,"Date start invalid !","Create new order",JOptionPane.ERROR_MESSAGE);           
            txfDateStart.setText("");
            txfDateStart.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtAddressToDeliver.getText()) || txtAddressToDeliver.getText().contains("'")){
            JOptionPane.showMessageDialog(this,"AddressToDeliver invalid !","Create new order",JOptionPane.ERROR_MESSAGE);           
            txtAddressToDeliver.setText("");
            txtAddressToDeliver.requestFocus();
            return false;
        }
        else if(!c.checkNumber(txtTaxs.getText()) || txtTaxs.getText().contains("'")){
            JOptionPane.showMessageDialog(this,"Tax invalid !","Create new order",JOptionPane.ERROR_MESSAGE);           
            txtTaxs.setText("");
            txtTaxs.requestFocus();
            return false;
        }
        else if(!c.checkDateIn(txfExpiredTime.getText())){
            JOptionPane.showMessageDialog(this,"Expired invalid !","Create new order",JOptionPane.ERROR_MESSAGE);           
            txfDateStart.setText("");
            txfDateStart.requestFocus();
            return false;
        }else if(java.sql.Date.valueOf(txfDateStart.getText().trim()).compareTo(java.sql.Date.valueOf(txfExpiredTime.getText().trim())) >0){
            JOptionPane.showMessageDialog(this," Date start cannot more than Expired !","Create new order",JOptionPane.ERROR_MESSAGE);           
            txfDateStart.setText("");
            txfDateStart.requestFocus();
            return false;
        }
        return true;
    }
    
     public void loadTableBillNotDetails(String nameTable,Vector vLoad){
        modelTableBill.setNumRows(0);
        Vector columnBill = new Vector();
        columnBill.addElement("Code");
        columnBill.addElement("billType");
        columnBill.addElement("customerCode");
        columnBill.addElement("addressToDeliver");
        columnBill.addElement("dateStart");
        columnBill.addElement("ExpiredTime");
        columnBill.addElement("Tax");
        //columnBill.addElement("price");
        //columnBill.addElement("status");
        //columnBill.addElement("UserCode");
        modelTableBill.setColumnIdentifiers(columnBill);
        Bills objBill = new Bills();
        for(int i=0; i< vLoad.size();i++){
            objBill = (Bills) vLoad.get(i);
            Vector rowBill = new Vector();
            rowBill.addElement(objBill.getbillCode());
            rowBill.addElement(objBill.getbillType());
            rowBill.addElement(objBill.getcustomerCode());
            rowBill.addElement(objBill.getaddressToDeliver());
            rowBill.addElement(objBill.getdateStart());
            rowBill.addElement(objBill.getexpiredTime());
            rowBill.addElement(objBill.gettax());
            //rowBill.addElement(objBill.getprice());
            //rowBill.addElement(objBill.getstatus());
            //rowBill.addElement(objBill.getuserCode());
            modelTableBill.addRow(rowBill);
        }
        tableNewBill.setModel(modelTableBill);
    }
    
      public void loadCustomer() throws SQLException{
        Vector vLoadOrder = new Vector();
        Vector vCustomerName=new Vector();
        vCustomerName=Customer.getAllCustomer();
        
        for(int j=0;j<vCustomerName.size();j++) {
            objCustomer=(Customer)vCustomerName.get(j);
            if(cboCustomerName.getSelectedItem().equals(objCustomer.getcustomerName())){
                txaCustomerAddress.setText(objCustomer.getcustomerAddress());
           }
        }
     }
    
     public void AddNewBill() throws SQLException{
        
        if(!this.checkNewBill()){
            return;
        }
        
        int test = 0;
        Bills objBill = new Bills();
        if(radImport.isSelected()==true){
            objBill.setbillType("Import");
        }else
            objBill.setbillType("Export");
        
        Vector vCustomerName=new Vector();
        vCustomerName=Customer.getAllCustomer();
        for(int j=0;j<vCustomerName.size();j++) {
           objCustomer=(Customer)vCustomerName.get(j);
           if(cboCustomerName.getSelectedItem().equals(objCustomer.getcustomerName())){
                objBill.setcustomerCode(objCustomer.getcustomerCode());                
             }           
        } 
        
        objBill.setaddressToDeliver(txaCustomerAddress.getText().trim());
        objBill.setdateStart(txfDateStart.getText().trim());
        objBill.setexpiredTime(txfExpiredTime.getText().trim());
        objBill.settax(Float.parseFloat(txtTaxs.getText().trim())/100);
        
        if(radWaiting.isSelected()==true){
            objBill.setstatus("waitting");
        }else{
            objBill.setstatus("complete");
        }
        
        Vector vlist = new Vector();
        Vector vloadUser = new Vector();
        vlist.addElement(Main.nameLogin);
        vloadUser = Users.getUserCodeForOrders(vlist);
        Users objUser = new Users();
        for(int i=0; i< vloadUser.size();i++){
            objUser = (Users) vloadUser.get(i);
            objBill.setuserCode(objUser.getuserCode());
        }   
        test = Bills.insertBill(objBill);
        if(test == 1){
            JOptionPane.showMessageDialog(this,"Insert Successfully !","Create Bill",JOptionPane.INFORMATION_MESSAGE);
            vLoadBillNoDetails = Bills.getAllBillNotDetails();
            this.loadTableBillNotDetails("vBillNotDetails",vLoadBillNoDetails);
        }else{
            JOptionPane.showMessageDialog(this,"Insert Error !","Create Bill",JOptionPane.ERROR_MESSAGE);
            vLoadBillNoDetails = Bills.getAllBillNotDetails();
            this.loadTableBillNotDetails("vBillNotDetails",vLoadBillNoDetails);
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jpanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboCustomerName = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaCustomerAddress = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        radExport = new javax.swing.JRadioButton();
        radImport = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txfDateStart = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddressToDeliver = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        radWaiting = new javax.swing.JRadioButton();
        radComplete = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtTaxs = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txfExpiredTime = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnCreate = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableNewBill = new javax.swing.JTable();

        jpanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Information"));
        jLabel1.setText("Customer Name");

        cboCustomerName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboCustomerNameItemStateChanged(evt);
            }
        });

        jLabel2.setText("Address");

        txaCustomerAddress.setColumns(20);
        txaCustomerAddress.setRows(5);
        jScrollPane1.setViewportView(txaCustomerAddress);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel3.setText("Bill Type");

        buttonGroup1.add(radExport);
        radExport.setSelected(true);
        radExport.setText("Export");
        radExport.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radExport.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(radImport);
        radImport.setText("Import");
        radImport.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radImport.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel4.setText("Date Start");

        try {
            txfDateStart = new JFormattedTextField(new MaskFormatter("####-##-##"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        jLabel5.setText("AddressToDeliver");

        txtAddressToDeliver.setColumns(20);
        txtAddressToDeliver.setRows(5);
        jScrollPane2.setViewportView(txtAddressToDeliver);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
        );

        jLabel6.setText("Status");

        buttonGroup2.add(radWaiting);
        radWaiting.setSelected(true);
        radWaiting.setText("Waiting");
        radWaiting.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radWaiting.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup2.add(radComplete);
        radComplete.setText("Complete");
        radComplete.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radComplete.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel7.setText("Tax");

        jLabel8.setText("[%]");

        jLabel9.setText("Expired Time");

        try {
            txfExpiredTime = new JFormattedTextField(new MaskFormatter("####-##-##"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        jLabel10.setText("[yyyy/mm/dd]");

        jLabel11.setText("[yyyy/mm/dd]");

        javax.swing.GroupLayout jpanel1Layout = new javax.swing.GroupLayout(jpanel1);
        jpanel1.setLayout(jpanel1Layout);
        jpanel1Layout.setHorizontalGroup(
            jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpanel1Layout.createSequentialGroup()
                            .addComponent(radExport)
                            .addGap(27, 27, 27)
                            .addComponent(radImport)
                            .addGap(128, 128, 128))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel1Layout.createSequentialGroup()
                            .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txfDateStart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboCustomerName, javax.swing.GroupLayout.Alignment.LEADING, 0, 174, Short.MAX_VALUE))
                            .addGap(79, 79, 79))))
                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txfExpiredTime, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpanel1Layout.createSequentialGroup()
                            .addComponent(radWaiting)
                            .addGap(32, 32, 32)
                            .addComponent(radComplete))
                        .addGroup(jpanel1Layout.createSequentialGroup()
                            .addComponent(txtTaxs, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(15, 15, 15)
                            .addComponent(jLabel8)))
                    .addComponent(jLabel11))
                .addGap(154, 154, 154))
        );
        jpanel1Layout.setVerticalGroup(
            jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel1Layout.createSequentialGroup()
                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel1Layout.createSequentialGroup()
                        .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cboCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpanel1Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel3))
                                    .addGroup(jpanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(radExport)
                                            .addComponent(radImport))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addComponent(jLabel10))
                            .addGroup(jpanel1Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(radWaiting, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(radComplete))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtTaxs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(32, 32, 32)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txfDateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txfExpiredTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add.png")));
        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Bill List"));
        tableNewBill.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableNewBill);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCreate)
                        .addGap(56, 56, 56))))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboCustomerNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboCustomerNameItemStateChanged
        try {
// TODO add your handling code here:
            this.loadCustomer();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cboCustomerNameItemStateChanged

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
 
        try{
        this.AddNewBill();
        }
        catch(SQLException ex){
        ex.printStackTrace();
        }
                /*try {
// TODO add your handling code here:
        this.AddNewBill();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
  */
    }//GEN-LAST:event_btnCreateActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cboCustomerName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jpanel1;
    private javax.swing.JRadioButton radComplete;
    private javax.swing.JRadioButton radExport;
    private javax.swing.JRadioButton radImport;
    private javax.swing.JRadioButton radWaiting;
    private javax.swing.JTable tableNewBill;
    private javax.swing.JTextArea txaCustomerAddress;
    private javax.swing.JTextField txfDateStart;
    private javax.swing.JTextField txfExpiredTime;
    private javax.swing.JTextArea txtAddressToDeliver;
    private javax.swing.JTextField txtTaxs;
    // End of variables declaration//GEN-END:variables
    
}
