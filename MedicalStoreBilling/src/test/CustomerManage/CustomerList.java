/*
 * CustomerList.java
 *
 * Created on September 11, 2009, 3:32 PM
 */

package CustomerManage;
import ConnectDatabase.DBHelper;
import Check.Check;
import GUI.Main;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author  TranVanHien
 */
public class CustomerList extends javax.swing.JPanel {
    private DefaultTableModel modelCustomer = new DefaultTableModel();
    
    String nameTable = "Supplier";
    
    Vector vLoad = new Vector();
    /** Creates new form CustomerList */
    public CustomerList() {
        initComponents();
        txtCodeCustomer.setEnabled(false);
        try {
            vLoad = Customer.getAllCustomer();
            this.loadTableCustomer(nameTable,vLoad);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void loadTableCustomer(String nameTable,Vector vLoad) throws SQLException{
    modelCustomer.setNumRows(0);
        Vector columnCustomer = new Vector();        
        columnCustomer.addElement("Code");
        columnCustomer.addElement("CustomerName");
        columnCustomer.addElement("CustomerType");
        columnCustomer.addElement("Phone");
        columnCustomer.addElement("Fax");
        columnCustomer.addElement("Email");
        columnCustomer.addElement("Address");
        columnCustomer.addElement("Relation");        
        modelCustomer.setColumnIdentifiers(columnCustomer);        
        Customer objCustomer = new Customer();
        
        for(int i=0; i< vLoad.size();i++){
            objCustomer = (Customer) vLoad.get(i);
            Vector rowCustomer = new Vector();
            rowCustomer.addElement(objCustomer.getcustomerCode());
            rowCustomer.addElement(objCustomer.getcustomerName());
            rowCustomer.addElement(objCustomer.getcustomerType());
            rowCustomer.addElement(objCustomer.getcustomerPhone());
            rowCustomer.addElement(objCustomer.getcustomerFax());
            rowCustomer.addElement(objCustomer.getcustomerEmail());
            rowCustomer.addElement(objCustomer.getcustomerAddress());
            rowCustomer.addElement(objCustomer.getcustomerRelationship());
            
            modelCustomer.addRow(rowCustomer);
        }
        tableCustomer.setModel(modelCustomer);
    }
    public void checkCustomerName(){       
        String name = txtNameCustomer.getText().trim();       
        boolean test = false;
        try {
            ResultSet rs = DBHelper.executeQuery("spCustomerName");
            while(rs.next()){
                if(name.equals(rs.getString("customerName"))){
                    txtNameCustomer.setText("");
                    txtNameCustomer.requestFocus();
                    test = true;                      
                }
            }
            if(test == true){
                JOptionPane.showMessageDialog(this,"Customer Name existed !","Add new Customer",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean checkCustomer(){        
        Check c= new Check();
        if(!c.checkSpace(txtNameCustomer.getText()) || !c.check(txtNameCustomer.getText())){
            JOptionPane.showMessageDialog(this,"Customer Name Invalid","Add new Customer",JOptionPane.ERROR_MESSAGE);
            txtNameCustomer.setText("");
            txtNameCustomer.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtAddressCustomer.getText())){
            JOptionPane.showMessageDialog(this,"Customer Address Invalid","Add new Customer",JOptionPane.ERROR_MESSAGE);
            txtAddressCustomer.setText("");
            txtAddressCustomer.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtEmailCustomer.getText()) || !c.checkEmail(txtEmailCustomer.getText())){
            JOptionPane.showMessageDialog(this,"Customer Email Invalid","Add new Customer",JOptionPane.ERROR_MESSAGE);
            txtEmailCustomer.setText("");
            txtEmailCustomer.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtFaxCustomer.getText()) || !c.checkPhone(txtFaxCustomer.getText())){
            JOptionPane.showMessageDialog(this,"Customer Fax Invalid","Add new Customer",JOptionPane.ERROR_MESSAGE);
            txtFaxCustomer.setText("");
            txtFaxCustomer.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtPhoneCustomer.getText()) || !c.checkPhone(txtPhoneCustomer.getText())){
            JOptionPane.showMessageDialog(this,"Customer Phone Invalid","Add new Customer",JOptionPane.ERROR_MESSAGE);
            txtPhoneCustomer.setText("");
            txtPhoneCustomer.requestFocus();
            return false;
        }
        return true;
    }
    
    public void AddCustomer(){
        int test = 0;
        if(!checkCustomer()){
            return;
        }
        Customer objCustomer = new Customer();
        objCustomer.setcustomerName(txtNameCustomer.getText().trim());
        
        if(cblCustomerType.getSelectedItem().toString().equals("Dealers")){
            objCustomer.setcustomerType("Dealers");
        }else if(cblCustomerType.getSelectedItem().toString().equals("Retailers")){
            objCustomer.setcustomerType("Retailers");
        }else
            objCustomer.setcustomerType("Distributors");
        
        objCustomer.setcustomerPhone(txtPhoneCustomer.getText().trim());
        objCustomer.setcustomerFax(txtPhoneCustomer.getText().trim());
        objCustomer.setcustomerEmail(txtEmailCustomer.getText().trim());
        objCustomer.setcustomerAddress(txtAddressCustomer.getText().trim());
        
        if(radiofamillar.isSelected()== true){
            objCustomer.setcustomerRelationship("familiar");
        }else
            objCustomer.setcustomerRelationship("unfamiliar");
        
        try {            
            test = Customer.insertCustomer(objCustomer);
            if(test == 1){
                JOptionPane.showMessageDialog(this,"Add new Customer successfully !","Add new Customer",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Customer.getAllCustomer();
                this.loadTableCustomer(nameTable,vLoad);
            }else
                JOptionPane.showMessageDialog(this,"Add new Customer error","Add new Customer",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateCustomer(){
        int test = 0;
        if(!this.checkCustomer()){
            return;
        }
        Customer objCustomer = new Customer();
        int customerCode = Integer.parseInt(txtCodeCustomer.getText().trim());
        objCustomer.setcustomerCode(customerCode);
        
        objCustomer.setcustomerName(txtNameCustomer.getText().trim());  
        
        if(cblCustomerType.getSelectedItem().toString().equals("Dealers")){
            objCustomer.setcustomerType("Dealers");
        }
        else if(cblCustomerType.getSelectedItem().toString().equals("Retailers")){
            objCustomer.setcustomerType("Retailers");
        }else
            objCustomer.setcustomerType("Distributors");
        
        objCustomer.setcustomerPhone(txtPhoneCustomer.getText().trim());
        objCustomer.setcustomerFax(txtFaxCustomer.getText().trim());
        objCustomer.setcustomerEmail(txtEmailCustomer.getText().trim());
        objCustomer.setcustomerAddress(txtAddressCustomer.getText().trim());
        if(radiofamillar.isSelected()== true){
            objCustomer.setcustomerRelationship("familiar");
        }else
            objCustomer.setcustomerRelationship("unfamiliar");
        
        try {            
            test = Customer.updateCustomer(objCustomer);
            if(test ==1){
                JOptionPane.showMessageDialog(this,"Update Customer successfully !","Update Customer",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Customer.getAllCustomer();
                this.loadTableCustomer(nameTable,vLoad);
            }else
                JOptionPane.showMessageDialog(this,"Update Customer error","Update Customer",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteCustomer(){
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Customer ?","Remove Customer",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableCustomer.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    test = Customer.deleteCustomer(tableCustomer.getValueAt(rArr[i],1).toString());
                    if(test == 1)
                        count++;
                }
                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Customer","Remove Customer",JOptionPane.INFORMATION_MESSAGE);
                    vLoad = Customer.getAllCustomer();
                    this.loadTableCustomer(nameTable,vLoad);
                }else
                    JOptionPane.showMessageDialog(this,"Delete Customer error !","Remove Customer",JOptionPane.ERROR_MESSAGE);
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void resetForm(){
        txtCodeCustomer.setText("");
        txtNameCustomer.setText("");
        txtAddressCustomer.setText("");
        txtPhoneCustomer.setText("");
        txtEmailCustomer.setText("");
        txtFaxCustomer.setText("");
        cblCustomerType.setSelectedIndex(0);
        txtCodeCustomer.requestFocus();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCustomer = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodeCustomer = new javax.swing.JTextField();
        txtNameCustomer = new javax.swing.JTextField();
        txtEmailCustomer = new javax.swing.JTextField();
        txtPhoneCustomer = new javax.swing.JTextField();
        txtFaxCustomer = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddressCustomer = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        radiofamillar = new javax.swing.JRadioButton();
        radioUnFamilar = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        cblCustomerType = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer List"));
        tableCustomer.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCustomerMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(tableCustomer);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Information Details"));
        jLabel1.setText("Code");

        jLabel2.setText("Name");

        jLabel3.setText("Email");

        jLabel4.setText("Phone");

        jLabel5.setText("Fax");

        jLabel6.setText("Adress");

        txtAddressCustomer.setColumns(20);
        txtAddressCustomer.setRows(5);
        jScrollPane2.setViewportView(txtAddressCustomer);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("RelationShip"));
        buttonGroup1.add(radiofamillar);
        radiofamillar.setSelected(true);
        radiofamillar.setText("familiar");
        radiofamillar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radiofamillar.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(radioUnFamilar);
        radioUnFamilar.setText("Unfamiliar");
        radioUnFamilar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioUnFamilar.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radiofamillar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radioUnFamilar)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(radiofamillar)
                .addComponent(radioUnFamilar))
        );

        jLabel7.setText("Type");

        cblCustomerType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "     Select Customer Type", "Dealers", "Retailers", "Distributors" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFaxCustomer)
                    .addComponent(txtPhoneCustomer)
                    .addComponent(txtEmailCustomer)
                    .addComponent(txtNameCustomer)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCodeCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                        .addComponent(cblCustomerType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cblCustomerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodeCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmailCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhoneCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFaxCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add.png")));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Remove.png")));
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

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/reset.png")));
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate)
                .addGap(14, 14, 14)
                .addComponent(btnDelete)
                .addGap(14, 14, 14)
                .addComponent(btnReset))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAdd)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDelete)
                .addComponent(btnReset))
        );

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/customerList.png")));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
// TODO add your handling code here:
        this.resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
// TODO add your handling code here:
        this.deleteCustomer();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// TODO add your handling code here:
        this.updateCustomer();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
        this.checkCustomerName();
        this.AddCustomer();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tableCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCustomerMouseClicked
// TODO add your handling code here:
        int r = tableCustomer.getSelectedRow();
        if(r < 0){
            return;
        }
        txtCodeCustomer.setText(""+tableCustomer.getValueAt(r,0));
        txtNameCustomer.setText(""+tableCustomer.getValueAt(r,1));
        
        if(tableCustomer.getValueAt(r,2).equals("Dealers")){
            cblCustomerType.setSelectedItem("Dealers");
        }else if(tableCustomer.getValueAt(r,2).equals("Retailers")){
            cblCustomerType.setSelectedItem("Retailers");
        }else
        cblCustomerType.setSelectedItem("Distributors");
        txtPhoneCustomer.setText(""+tableCustomer.getValueAt(r,3));
        txtFaxCustomer.setText(""+tableCustomer.getValueAt(r,4));
        txtEmailCustomer.setText(""+tableCustomer.getValueAt(r,5));
        txtAddressCustomer.setText(""+tableCustomer.getValueAt(r,6));
        
        if(tableCustomer.getValueAt(r,7).equals("familiar")){
            radiofamillar.setSelected(true);
        }else{
            radioUnFamilar.setSelected(true);
        }
    }//GEN-LAST:event_tableCustomerMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cblCustomerType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton radioUnFamilar;
    private javax.swing.JRadioButton radiofamillar;
    private javax.swing.JTable tableCustomer;
    private javax.swing.JTextArea txtAddressCustomer;
    private javax.swing.JTextField txtCodeCustomer;
    private javax.swing.JTextField txtEmailCustomer;
    private javax.swing.JTextField txtFaxCustomer;
    private javax.swing.JTextField txtNameCustomer;
    private javax.swing.JTextField txtPhoneCustomer;
    // End of variables declaration//GEN-END:variables
    
}
