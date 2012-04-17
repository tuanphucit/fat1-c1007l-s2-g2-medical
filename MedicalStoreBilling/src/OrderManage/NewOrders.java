/*
 * NewOrders.java
 *
 * Created on April 10, 2012, 6:11 PM
 */

package OrderManage;
import ConnectDatabase.DBHelper;
import Check.Check;
import CustomerManage.Customer;
import GUI.Main;
import MedicineManage.Medicines;
import UserManage.Users;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author  
 */
public class NewOrders extends javax.swing.JPanel {
    private DefaultTableModel modelTableOrders = new DefaultTableModel();
    Vector vLoad = new Vector();
    
    String nameTable = "Orders";
    
    String nameTableMedicine = "vMedicines";
    
    private DefaultComboBoxModel cbmdCustomerType = new DefaultComboBoxModel();
    
    Vector vload1 = new Vector();
    Vector vlist = new Vector();
    
    Customer objCustomer = new Customer();
    /** Creates new form NewOrders */
    public NewOrders() {
        initComponents();
        try {
            
            vLoad = Orders.getAllOrders();
            this.loadTableOrders(nameTable,vLoad);
                                    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }       
        
        Vector vCustomer=new Vector();
        try {
            vCustomer=Customer.getAllCustomer();
            cbmdCustomerType.removeAllElements();
            cbmdCustomerType.addElement("---- Choice Customer ----");

            for(int j=0;j<vCustomer.size();j++) {
                objCustomer=(Customer)vCustomer.get(j);
                cbmdCustomerType.addElement(objCustomer.getcustomerName());
            }
            cboCustomer.setModel(cbmdCustomerType);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     public boolean checkOrders(){
        Check c = new Check();
        if(cboCustomer.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(this,"Customer not selected !","Create new order",JOptionPane.ERROR_MESSAGE);           
            return false;
        }
        if(!c.checkDateIn(txfDateOrder.getText())){
            JOptionPane.showMessageDialog(this,"Date order invalid !","Create new order",JOptionPane.ERROR_MESSAGE);
            txfDateOrder.setText("");
            txfDateOrder.requestFocus();
            return false;            
        }
        if(!c.checkSpace(txaAddressToDeliver.getText()) || !c.check(txaAddressToDeliver.getText()) || txaAddressToDeliver.getText().contains("'")){
            JOptionPane.showMessageDialog(this,"Address To Deliver order invalid !","Create new order",JOptionPane.ERROR_MESSAGE);
            txaAddressToDeliver.setText("");
            txaAddressToDeliver.requestFocus();            
            return false;
        }
        return true;
    }
    
    public void loadTableOrders(String nameTable,Vector vLoad){
        modelTableOrders.setNumRows(0);
        Vector columnOrders = new Vector();
        columnOrders.addElement("Code");
        columnOrders.addElement("CustomerCode");
        columnOrders.addElement("Date");
        columnOrders.addElement("userCode");
        columnOrders.addElement("AddresstoDeliver");
        columnOrders.addElement("Price");
        modelTableOrders.setColumnIdentifiers(columnOrders);
        Orders objOrder = new Orders();
        for(int i=0; i< vLoad.size();i++){
            objOrder = (Orders) vLoad.get(i);
            Vector rowOrder = new Vector();
            rowOrder.addElement(objOrder.getOderCode());
            rowOrder.addElement(objOrder.getcustomerCode());
            rowOrder.addElement(objOrder.getdateOrder());
            rowOrder.addElement(objOrder.getuserCode());
            rowOrder.addElement(objOrder.getaddressToDeliver());
            rowOrder.addElement(objOrder.getpriceOrder());
            modelTableOrders.addRow(rowOrder);
        }
        tableOrders.setModel(modelTableOrders);
    }    
    
    public void AddNewOrders() throws SQLException{
        int test = 0;
        if(!checkOrders()){
            return;
        }
        Orders objOrder = new Orders();
        
        Vector vCustomerName=new Vector();
        vCustomerName=Customer.getAllCustomer();
        for(int j=0;j<vCustomerName.size();j++) {
           objCustomer=(Customer)vCustomerName.get(j);
           if(cboCustomer.getSelectedItem().equals(objCustomer.getcustomerName())){
                objOrder.setcustomerCode(objCustomer.getcustomerCode());                
             }           
        } 
        objOrder.setdateOrder(txfDateOrder.getText().trim());
        //objOrder.setdateOrder(objOrder.date);
        
        Vector vlist = new Vector();
        Vector vloadUser = new Vector();
        vlist.addElement(Main.nameLogin);
        vloadUser = Users.getUserCodeForOrders(vlist);
        Users objUser = new Users();
        for(int i=0; i< vloadUser.size();i++){
            objUser = (Users) vloadUser.get(i);
            objOrder.setuserCode(objUser.getuserCode());
        }       
        
        objOrder.setaddressToDeliver(txaAddressToDeliver.getText().trim());
        
        test = Orders.insertOrders(objOrder);
        
        if(test == 1){
            JOptionPane.showMessageDialog(this,"Insert Successfully !","Create Orders",JOptionPane.INFORMATION_MESSAGE);
            vLoad = Orders.getAllOrders();
            this.loadTableOrders(nameTable,vLoad);
        }else{
            JOptionPane.showMessageDialog(this,"Insert Error !","Create Orders",JOptionPane.ERROR_MESSAGE);
        }
        
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
        tableOrders = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txfDateOrder = new javax.swing.JTextField();
        try {
            txfDateOrder = new JFormattedTextField(new MaskFormatter("####-##-##"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cboCustomer = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaAddressToDeliver = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Orders List"));
        tableOrders.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableOrders);

        jLabel1.setText("Customer Name ");

        jLabel2.setText("Date");

        jLabel3.setText("[ yyyy / mm / dd ]");

        jLabel4.setText("Address To Deliver");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txaAddressToDeliver.setColumns(20);
        txaAddressToDeliver.setRows(5);
        jScrollPane2.setViewportView(txaAddressToDeliver);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2)))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txfDateOrder, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCustomer, javax.swing.GroupLayout.Alignment.LEADING, 0, 126, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4)))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txfDateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel1)
                                .addComponent(cboCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add.png")));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(69, 69, 69))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnAdd)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
// TODO add your handling code here:
            this.AddNewOrders();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAddActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JComboBox cboCustomer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableOrders;
    private javax.swing.JTextArea txaAddressToDeliver;
    private javax.swing.JTextField txfDateOrder;
    // End of variables declaration//GEN-END:variables
    
}
