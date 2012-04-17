/*
 * Payment.java
 *
 * Created on  April 11, 2012, 5:38 PM
 */

package BillManage;

import Check.Check;
import CustomerManage.Customer;
import java.sql.SQLException;
import java.text.ParseException;
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
public class Payment extends javax.swing.JPanel {
    private float payedMoney  = 0.0f;
    
    private DefaultComboBoxModel cbmdCustomerName = new DefaultComboBoxModel();
    Customer objCustomer = new Customer();
    private DefaultTableModel modelTableBill = new DefaultTableModel();
    Vector vLoadBill = new Vector();
    
    /** Creates new form Payment */
    public Payment() {
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
                        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public boolean checkPay(){
        Check c = new Check();        
        if(!c.checkNumber(txtPayment.getText())){
            JOptionPane.showMessageDialog(this,"Payment invalid !","Payment",JOptionPane.ERROR_MESSAGE);
            txtPayment.setText("");
            txtPayment.requestFocus();
            return false;
        }
        if(!c.checkDateIn(txfDatePay.getText())){
            JOptionPane.showMessageDialog(this,"Date pay invalid !","Payment",JOptionPane.ERROR_MESSAGE);
            txfDatePay.setText("");
            txfDatePay.requestFocus();
            return false;
        }
        return true;
    }
    
     public void loadCustomer() throws SQLException{ 
        Vector vlistBill = new Vector();                
        Vector vCustomerName=new Vector();
        vCustomerName=Customer.getAllCustomer();
        
        for(int j=0;j<vCustomerName.size();j++) {
            objCustomer=(Customer)vCustomerName.get(j);
            if(cboCustomerName.getSelectedItem().equals(objCustomer.getcustomerName())){
                vlistBill.addElement(objCustomer.getcustomerCode());
            }
        }
        vLoadBill = Bills.getAllBillsWaitting(vlistBill);
        this.tableBillsWaitting("vBillsWaitting",vLoadBill);
    }
     
     public void resetForm(){
        txtBillCode.setText("");
        txtPayment.setText("");        
    }
     
      public void updateStatus() throws SQLException{
        int test = 0;
        Bills objBill = new Bills();
        objBill.setbillCode(Integer.parseInt(txtBillCode.getText().trim()));
        if(Float.parseFloat(txtPayed.getText().trim()) >= Float.parseFloat(txtTotalPrice.getText().trim())){

            JOptionPane.showMessageDialog(this," !","Pay",JOptionPane.INFORMATION_MESSAGE);
           
        }
    }
      
      public void tableBillsWaitting(String nameTable,Vector vLoad){
        modelTableBill.setNumRows(0);
        Vector columnBill = new Vector();
        columnBill.addElement("Code");
        columnBill.addElement("CustomerCode");
        columnBill.addElement("billType");
        columnBill.addElement("price");
        columnBill.addElement("status");        
        modelTableBill.setColumnIdentifiers(columnBill);
        Bills objBill = new Bills();
        for(int i=0; i< vLoad.size();i++){
            objBill = (Bills) vLoad.get(i);
            Vector rowBill = new Vector();
            rowBill.addElement(objBill.getbillCode());
            rowBill.addElement(objBill.getcustomerCode());
            rowBill.addElement(objBill.getbillType());
            rowBill.addElement(objBill.getprice());
            rowBill.addElement(objBill.getstatus());
            modelTableBill.addRow(rowBill);
        }
        tableBillsWaitting.setModel(modelTableBill);
    }
      
      public void saveNewPay() throws SQLException{
        
        if(!this.checkPay()){
            return;
        }
        
        
        int test = 0;
        Bills objBill = new Bills();
        objBill.setbillCode(Integer.parseInt(txtBillCode.getText().trim()));
        objBill.setdatePay(txfDatePay.getText());
        objBill.setpayedMoney(Float.parseFloat(txtPayment.getText().trim()));
        test = Bills.insertPay(objBill);
        if(test == 1){
            JOptionPane.showMessageDialog(this,"Pay successfully !","Pay",JOptionPane.INFORMATION_MESSAGE);
            this.loadCustomer();
        }else{
            JOptionPane.showMessageDialog(this,"Pay Error !","Pay",JOptionPane.ERROR_MESSAGE);
            this.loadCustomer();
        }
        
        int testUpdate = 0;
        Bills objBillUpdate = new Bills();
        objBillUpdate.setbillCode(Integer.parseInt(txtBillCode.getText().trim()));
        if((Float.parseFloat(txtPayed.getText().trim()) + Float.parseFloat(txtPayment.getText().trim())) >= Float.parseFloat(txtTotalPrice.getText().trim())){
            objBillUpdate.setstatus("complete");
            testUpdate = Bills.updateStatusForBill(objBillUpdate);
            if(testUpdate == 1){
                JOptionPane.showMessageDialog(this,"Has complete payment !","Complete payment",JOptionPane.INFORMATION_MESSAGE);
                this.loadCustomer();
            }else
            {
                JOptionPane.showMessageDialog(this,"Pay Error","Pay",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            objBillUpdate.setstatus("waitting");
            testUpdate = Bills.updateStatusForBill(objBillUpdate);
            if(testUpdate == 1){
                JOptionPane.showMessageDialog(this,"Payment not completed !","Pay",JOptionPane.INFORMATION_MESSAGE);
                this.loadCustomer();
            }
        }
        this.resetForm();
        
    }
      
      public void loadMoney(){
        int r = tableBillsWaitting.getSelectedRow();
        if(r<0){
            return;
        }
        txtBillCode.setText(tableBillsWaitting.getValueAt(r,0).toString());
        txtTotalPrice.setText(tableBillsWaitting.getValueAt(r,3).toString());
        int billCode = Integer.parseInt(tableBillsWaitting.getValueAt(r,0).toString());
        Vector vlistPay = new Vector();
        vlistPay.addElement(billCode);
        Vector vloadPay = new Vector();
        try {
            vloadPay = Bills.getPayedMoney(vlistPay);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        Bills objBill = new Bills();
        for(int i = 0; i< vloadPay.size();i++){
            objBill= (Bills) vloadPay.get(i);
            payedMoney = objBill.getpayedMoney();
        }
        Float money = new Float(payedMoney);
        String moneypay = money.toString();
        txtPayed.setText(moneypay);
    }
      
      
      
    



 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboCustomerName = new javax.swing.JComboBox();
        txtTotalPrice = new javax.swing.JTextField();
        txfDatePay = new javax.swing.JFormattedTextField();
        txtPayment = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtBillCode = new javax.swing.JTextField();
        txtPayed = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBillsWaitting = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pay information"));
        jLabel1.setText("Customer Name");

        jLabel2.setText("Total Price In Bill");

        jLabel3.setText("Payment");

        jLabel4.setText("Date");

        jLabel5.setText("[yyyy/mm/dd]");

        txtTotalPrice.setEnabled(false);

        try {
            txfDatePay = new JFormattedTextField(new MaskFormatter("####-##-##"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Bill Code");

        jLabel7.setText("Payed");

        txtBillCode.setDragEnabled(true);
        txtBillCode.setEnabled(false);

        txtPayed.setEnabled(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/login.png")));
        jButton1.setText("Pay");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txfDatePay, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtPayment, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTotalPrice, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cboCustomerName, javax.swing.GroupLayout.Alignment.LEADING, 0, 160, Short.MAX_VALUE))
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBillCode)
                            .addComponent(txtPayed, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                        .addContainerGap(232, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(64, 64, 64))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtBillCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtPayed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txfDatePay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Please choice the Bill the payment"));
        tableBillsWaitting.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableBillsWaitting);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        try{
        this.saveNewPay();
        }
        catch(SQLException ex){
        ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
     private void cboCustomerNameMouseClicked(java.awt.event.MouseEvent evt) {                                             
// TODO add your handling code here:
        cbmdCustomerName.removeElement("---- Choice Customer ----");
    }
     
      private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {                                       
        try {
// TODO add your handling code here:
            this.saveNewPay();
           // this.updateStatus();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }      
      
       private void tableBillsWaittingMouseClicked(java.awt.event.MouseEvent evt) {                                                
// TODO add your handling code here:
        this.loadMoney();
    }                                               

        private void cboCustomerNameItemStateChanged(java.awt.event.ItemEvent evt) {                                                 
        try {
// TODO add your handling code here:
            this.loadCustomer();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }     
        
         public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
                new Payment();
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboCustomerName;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableBillsWaitting;
    private javax.swing.JFormattedTextField txfDatePay;
    private javax.swing.JTextField txtBillCode;
    private javax.swing.JTextField txtPayed;
    private javax.swing.JTextField txtPayment;
    private javax.swing.JTextField txtTotalPrice;
    // End of variables declaration//GEN-END:variables
    
}
