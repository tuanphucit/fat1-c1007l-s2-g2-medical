/*
 * OrderManages.java
 *
 * Created on April 10, 2012, 6:34 PM
 */

package OrderManage;
import ConnectDatabase.DBHelper;
import CustomerManage.Customer;
import MeasureManage.Measures;
import MedicineManage.Medicines;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import GUI.Main;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author  
 */
public class OrderManages extends javax.swing.JPanel {
    private DefaultTableModel modelTableOrders = new DefaultTableModel();
    Vector vLoad = new Vector();
    String nameTable = "Orders";
    
    private DefaultTableModel modelTableMedicine = new DefaultTableModel();
    private DefaultTableModel modelTableMedicineChoice = new DefaultTableModel();
    //Vector vLoad = new Vector();
    Vector vLoadChoice = new Vector();
    String nameTableMedicine = "vMedicines";
    
    Vector vload1 = new Vector();
    Vector vload2 = new Vector();
    
    Orders objOrder = new Orders();
    
    JTextField txtQuantity = new JTextField();
    
    //-----------------Order Manege--------------------------------------
    private DefaultComboBoxModel cbmdCustomerName = new DefaultComboBoxModel();
    Customer objCustomer = new Customer();
    Main main = new Main();
    
    private DefaultTableModel modelTableOrdersManage = new DefaultTableModel();
    private DefaultTableModel modelTableOrderDetails = new DefaultTableModel();
    
    Vector vLoadOrder = new Vector();
    /** Creates new form OrderManages */
    
    
    public OrderManages() {
        initComponents();
         btnSave.setEnabled(false);
        try {
            vLoad = Orders.getAllOrdersNotDetails();
            this.loadTableOrders(nameTable,vLoad);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            vload2 = Medicines.getAllViewMedicinesForOrder();
            this.loadTableMedicineList(nameTableMedicine,vload2);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        modelTableMedicineChoice.setNumRows(0);
        Vector columnMedicineChoice = new Vector();
        columnMedicineChoice.addElement("Code");
        columnMedicineChoice.addElement("Name");
        columnMedicineChoice.addElement("Type");
        columnMedicineChoice.addElement("Measure");
        columnMedicineChoice.addElement("PricePerUnit");
        columnMedicineChoice.addElement("Quantity");
        tableMedicineChoice.setModel(modelTableMedicineChoice);
        modelTableMedicineChoice.setColumnIdentifiers(columnMedicineChoice);
        
        
        btnCalculator.setEnabled(false);
        btnRemove.setEnabled(false);
        txtPrice.setEditable(false);
        txtOrderCode.setEditable(false);
        ////------------------Order Manage-------------------------------------------
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
        txaAddress.setEditable(false);
        tableOrderDetails.setEnabled(false);
        radFamiliar.setEnabled(false);
        radUnfamiliar.setEnabled(false);
        txtCustomerPhoneOrder.setEditable(false);
        
    }
    public void loadTableOrders(String nameTable,Vector vLoad){
        modelTableOrders.setNumRows(0);
        Vector columnOrders = new Vector();
        columnOrders.addElement("Code");
        columnOrders.addElement("CustomerCode");
        columnOrders.addElement("Date");
        columnOrders.addElement("AddresstoDeliver");
        modelTableOrders.setColumnIdentifiers(columnOrders);
        Orders objOrder = new Orders();
        for(int i=0; i< vLoad.size();i++){
            objOrder = (Orders) vLoad.get(i);
            Vector rowOrder = new Vector();
            rowOrder.addElement(objOrder.getOderCode());
            rowOrder.addElement(objOrder.getcustomerCode());
            rowOrder.addElement(objOrder.getdateOrder());
            rowOrder.addElement(objOrder.getaddressToDeliver());
            modelTableOrders.addRow(rowOrder);
        }
        tableOrders.setModel(modelTableOrders);
    }
    
    
    public void loadTableMedicineList(String nameTableMedicine,Vector vLoad) throws SQLException{
        modelTableMedicine.setNumRows(0);
        Vector columnMedicine = new Vector();
        columnMedicine.addElement("Code");
        columnMedicine.addElement("Name");
        columnMedicine.addElement("Type");
        columnMedicine.addElement("Measure");
        columnMedicine.addElement("PricePerUnit");
        columnMedicine.addElement("avaiableAmount");
        columnMedicine.addElement("Origin");
        columnMedicine.addElement("Supplier");
        modelTableMedicine.setColumnIdentifiers(columnMedicine);
        Medicines objMedicine = new Medicines();
        for(int i=0; i<vLoad.size();i++){
            objMedicine = (Medicines) vLoad.get(i);
            Vector rowMedicine = new Vector();
            rowMedicine.addElement(objMedicine.getMedicineCode());
            rowMedicine.addElement(objMedicine.getMedicineName());
            rowMedicine.addElement(objMedicine.getmedicineTypeName());
            rowMedicine.addElement(objMedicine.getmeasureName());
            rowMedicine.addElement(objMedicine.getPricePerUnit());
            rowMedicine.addElement(objMedicine.getAvaiableAmount());
            rowMedicine.addElement(objMedicine.getOrigin());
            rowMedicine.addElement(objMedicine.getsupplierName());
            modelTableMedicine.addRow(rowMedicine);
        }
        tableMedicineList.setModel(modelTableMedicine);
    }
    
    public boolean test(){
        int r = tableMedicineList.getSelectedRow();
        
        int code = Integer.parseInt(tableMedicineList.getValueAt(r,0).toString());
        for(int i =0; i<tableMedicineChoice.getRowCount();i++){
            if(tableMedicineChoice.getValueAt(i,0).equals(code)){
                //JOptionPane.showConfirmDialog(this,"Khong them duoc !","Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
    
    public void AddMedicine(String nameTableMedicine,Vector vLoad) throws SQLException{
        int r = tableMedicineList.getSelectedRow();
        
        if(r<0){
            return;
        }
        int code = Integer.parseInt(tableMedicineList.getValueAt(r,0).toString());
        Vector v = new Vector();
        v.add(code);
        
        boolean test = test();
        Medicines objMedicine = new Medicines();
        //txtQuantity.setText("tao ghet may");
        ResultSet rs = DBHelper.executeQuery("vspGetMedicineByCodeForOrder",v);
        while(rs.next()){
            Vector rowMedicineChoice = new Vector();
            //if(tableMedicineChoice.getRowCount()==0 && test==true)
            if(test==false){
                JOptionPane.showMessageDialog(this,"Medicine Existed !","Error",JOptionPane.ERROR_MESSAGE);
            }else{
                rowMedicineChoice.addElement(rs.getInt(1));  //0
                rowMedicineChoice.addElement(rs.getString(2));//1
                rowMedicineChoice.addElement(rs.getString(3));//2
                rowMedicineChoice.addElement(rs.getString(4));//2
                rowMedicineChoice.addElement(rs.getFloat(5));//3
                //rowMedicineChoice.addElement(txtQuantity);//3
                TableColumn columnQuantity = tableMedicineChoice.getColumnModel().getColumn(5);
                columnQuantity.setCellEditor(new DefaultCellEditor(txtQuantity));
                modelTableMedicineChoice.addRow(rowMedicineChoice);
            }
        }
    }
    
    public void deleteOrder(){
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Order ?","Remove Order",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableOrders.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    int orderCode = Integer.parseInt(tableOrders.getValueAt(rArr[i],0).toString());
                    test = Orders.deleteOrder(orderCode);
                    if(test == 1)
                        count++;
                }
                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Order","Remove Order",JOptionPane.INFORMATION_MESSAGE);
                    vLoad = Orders.getAllOrders();
                    this.loadTableOrders(nameTable,vLoad);
                }else
                    JOptionPane.showMessageDialog(this,"Delete Order not exist order details !","Remove Order",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Orders.getAllOrders();
                this.loadTableOrders(nameTable,vLoad);
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void resetForm(){
        txtOrderCode.setText("");
        txtCustomerName.setText("");
        txaCustomerAddress.setText("");
        txtPrice.setText("");
        txtCustomerPhone.setText("");
        btnSave.setEnabled(false);
    }
    
    public void AddNewOrderDetails() throws SQLException{
        int test = 0;
        int testUpdate = 0;
        int testUpdateAvaiableAmount = 0;
        boolean flag = false;
        Vector vloadMeasure = new Vector();
        Vector vlistMeasure = new Vector();
        Orders objOrders= new Orders();
        Medicines objMedicine = new Medicines();
        int rTableOrder = tableOrders.getRowCount();
        int rTableChoice = tableMedicineChoice.getRowCount();
        // can xem xet lai cho nay tai sao lai bao loi sai ?
        objOrders.setOrderCode(Integer.parseInt(txtOrderCode.getText().trim()));
        
        for(int i = 0; i < rTableChoice; i++){
            
            objOrders.setmedicineCode(Integer.parseInt(tableMedicineChoice.getValueAt(i,0).toString()));
            objMedicine.setMedicineCode(Integer.parseInt(tableMedicineChoice.getValueAt(i,0).toString()));
            // lay ra ma cua don vi tinh theo ten
            vlistMeasure.addElement(tableMedicineChoice.getValueAt(i,3).toString());
            vloadMeasure = Measures.getMeasureCodeByName(vlistMeasure);
            Measures objMeasure = new Measures();
            for(int j=0; j< vloadMeasure.size();j++){
                objMeasure = (Measures) vloadMeasure.get(j);
                objOrders.setmeasureCode(objMeasure.getmeasureCode());
            }
            
            objOrders.setquantity(Integer.parseInt(tableMedicineChoice.getValueAt(i,5).toString()));
            objMedicine.setAvaiableAmount(Integer.parseInt(tableMedicineChoice.getValueAt(i,5).toString()));
            test = Orders.insertOrdersDetails(objOrders);
            testUpdateAvaiableAmount = Medicines.updateAvaiableAmountMedicineDetails(objMedicine);
            if(test == 1 && testUpdateAvaiableAmount==1){
                flag = true;
            }else{
                flag = false;
            }
        }
        Orders objOrdersUpdate = new Orders();
        int orderCode = Integer.parseInt(txtOrderCode.getText().trim());
        objOrdersUpdate.setOrderCode(orderCode);
        float totalPrice = Float.parseFloat(txtPrice.getText().trim().toString());
        objOrdersUpdate.setpriceOrder(totalPrice);
        testUpdate = Orders.updatePriceForOrders(objOrdersUpdate);
        
        if(flag == true && testUpdate ==1){
            JOptionPane.showMessageDialog(this,"Add new Order Details Successfully !","Add new Order Details",JOptionPane.INFORMATION_MESSAGE);
            vLoad = Orders.getAllOrdersNotDetails();
            this.loadTableOrders(nameTable,vLoad);
            vload2 = Medicines.getAllViewMedicinesForOrder();
            this.loadTableMedicineList(nameTableMedicine,vload2);
        }else
            JOptionPane.showMessageDialog(this,"Add new Order Details Error !","Add new Order Details",JOptionPane.ERROR_MESSAGE);
    }
    
    
    public boolean checkQuantity(){
        int r = tableMedicineChoice.getRowCount();
        for(int i = 0;i < r;i++){
            try{
                String quantity = tableMedicineChoice.getValueAt(i,5).toString();
                int c = Integer.parseInt(quantity);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"Quantity Not Format Number !","Transaction Details",JOptionPane.ERROR_MESSAGE);
                return false;
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Quantity Not Format Number !","Transaction Details",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
    //-----------------------0RDER MANAGE-----------------------------------------------------------------------------------
    public void loadTableOrder(String nameTable,Vector vload){
        modelTableOrders.setNumRows(0);
        Vector columnOrders = new Vector();
        columnOrders.addElement("Code");
        columnOrders.addElement("CustomerCode");
        columnOrders.addElement("DateOrder");
        columnOrders.addElement("UserCode");
        columnOrders.addElement("AddressToDeliver");
        columnOrders.addElement("Price");
        modelTableOrders.setColumnIdentifiers(columnOrders);
        Orders objOrder = new Orders();
        for(int i=0; i< vload.size();i++){
            objOrder = (Orders) vload.get(i);
            Vector rowOrder = new Vector();
            rowOrder.addElement(objOrder.getOderCode());
            rowOrder.addElement(objOrder.getcustomerCode());
            rowOrder.addElement(objOrder.getdateOrder());
            rowOrder.addElement(objOrder.getuserCode());
            rowOrder.addElement(objOrder.getaddressToDeliver());
            rowOrder.addElement(objOrder.getpriceOrder());
            modelTableOrders.addRow(rowOrder);
        }
        tableOrderManage.setModel(modelTableOrders);
    }
    
    public void loadTableOrderDetails(String nameTable, Vector vload){
        modelTableOrderDetails.setNumRows(0);
        Vector columnOrderDetails = new Vector();
        columnOrderDetails.addElement("OrderCode");
        columnOrderDetails.addElement("MedicineCode");
        columnOrderDetails.addElement("MedicineName");
        columnOrderDetails.addElement("MedicineType");
        columnOrderDetails.addElement("MeasureName");
        columnOrderDetails.addElement("PricePerUnit");
        columnOrderDetails.addElement("Quantity");
        modelTableOrderDetails.setColumnIdentifiers(columnOrderDetails);
        Orders objOrderDetails = new Orders();
        for(int i=0; i< vload.size();i++){
            objOrderDetails = (Orders) vload.get(i);
            Vector rowOrderDetails = new Vector();
            rowOrderDetails.addElement(objOrderDetails.getOderCode());
            rowOrderDetails.addElement(objOrderDetails.getmedicineCode());
            rowOrderDetails.addElement(objOrderDetails.getmedicineName());
            rowOrderDetails.addElement(objOrderDetails.getmedicineTypeName());
            rowOrderDetails.addElement(objOrderDetails.getmeasureName());
            rowOrderDetails.addElement(objOrderDetails.getpricePerUnit());
            rowOrderDetails.addElement(objOrderDetails.getquantity());
            modelTableOrderDetails.addRow(rowOrderDetails);
        }
        tableOrderDetails.setModel(modelTableOrderDetails);
    }
    
    public void loadCustomer() throws SQLException{
        
        Orders objOrder = new Orders();
        Vector vlistOrder = new Vector();
        Vector vLoadOrder = new Vector();
        Vector vCustomerName=new Vector();
        vCustomerName=Customer.getAllCustomer();
        
        for(int j=0;j<vCustomerName.size();j++) {
            objCustomer=(Customer)vCustomerName.get(j);
            if(cboCustomerName.getSelectedItem().equals(objCustomer.getcustomerName())){
                vlistOrder.addElement(objCustomer.getcustomerCode());
                txaAddress.setText(objCustomer.getcustomerAddress());
                if(objCustomer.getcustomerRelationship().equals("familiar")){
                    radFamiliar.setSelected(true);
                }else
                    radUnfamiliar.setSelected(true);
                txtCustomerPhoneOrder.setText(objCustomer.getcustomerPhone());
            }
        }
        vLoadOrder = Orders.getOrderByCustomerCode(vlistOrder);
        this.loadTableOrder("Orders",vLoadOrder);
    }
    public void deleteOrderManage() throws SQLException{
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Order ?","Remove Order",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableOrderManage.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    test = Orders.deleteOrder(Integer.parseInt(tableOrderManage.getValueAt(rArr[i],0).toString()));
                    if(test == 1)
                        count++;
                }
                if(count >= 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Order","Remove Order",JOptionPane.INFORMATION_MESSAGE);
                    this.loadCustomer();
                }else
                    JOptionPane.showMessageDialog(this,"Delete Order error !","Remove Order",JOptionPane.ERROR_MESSAGE);
                    this.loadCustomer();
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        txtPhone = new javax.swing.JTabbedPane();
        jpanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableOrders = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtOrderCode = new javax.swing.JTextField();
        btnRemove = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableMedicineChoice = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableMedicineList = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        radFamiliardetail = new javax.swing.JRadioButton();
        radUnfamiliarDetail = new javax.swing.JRadioButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        txaCustomerAddress = new javax.swing.JTextArea();
        txtCustomerPhone = new javax.swing.JTextField();
        btnCalculator = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnRemoveMedicine = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboCustomerName = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaAddress = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        radFamiliar = new javax.swing.JRadioButton();
        radUnfamiliar = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCustomerPhoneOrder = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScolpanel = new javax.swing.JScrollPane();
        tableOrderManage = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableOrderDetails = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Order List"));

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
        tableOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOrdersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableOrders);

        jLabel5.setText("Order Code");

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addComponent(txtOrderCode, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addComponent(btnRemove)
                .addGap(50, 50, 50))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btnRemove)
                    .addComponent(txtOrderCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine"));

        tableMedicineChoice.setModel(new javax.swing.table.DefaultTableModel(
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
        tableMedicineChoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMedicineChoiceMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableMedicineChoice);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine List"));

        tableMedicineList.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tableMedicineList);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer"));

        jLabel6.setText("Customer Name");

        txtCustomerName.setEnabled(false);

        jLabel7.setText("Address");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel8.setText("Relationship");

        jLabel9.setText("Phone");

        buttonGroup2.add(radFamiliardetail);
        radFamiliardetail.setText("Familiar");
        radFamiliardetail.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radFamiliardetail.setEnabled(false);
        radFamiliardetail.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup2.add(radUnfamiliarDetail);
        radUnfamiliarDetail.setText("UnFamiliar");
        radUnfamiliarDetail.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radUnfamiliarDetail.setEnabled(false);
        radUnfamiliarDetail.setMargin(new java.awt.Insets(0, 0, 0, 0));

        txaCustomerAddress.setColumns(20);
        txaCustomerAddress.setRows(5);
        jScrollPane6.setViewportView(txaCustomerAddress);

        txtCustomerPhone.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(34, 34, 34)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel11, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(237, 237, 237))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtCustomerPhone, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCustomerName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(radFamiliardetail)
                            .addGap(22, 22, 22)
                            .addComponent(radUnfamiliarDetail))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(radUnfamiliarDetail)
                                    .addComponent(jLabel8)
                                    .addComponent(radFamiliardetail))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCustomerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCalculator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/calculate.png"))); // NOI18N
        btnCalculator.setText("Calculator");
        btnCalculator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculatorActionPerformed(evt);
            }
        });

        jLabel10.setText("TotalPrice");

        txtPrice.setEnabled(false);

        jLabel11.setText("VND");

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemoveMedicine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png"))); // NOI18N
        btnRemoveMedicine.setText("Remove");
        btnRemoveMedicine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMedicineActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanel2Layout = new javax.swing.GroupLayout(jpanel2);
        jpanel2.setLayout(jpanel2Layout);
        jpanel2Layout.setHorizontalGroup(
            jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel2Layout.createSequentialGroup()
                .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(jpanel2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnCalculator)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveMedicine)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpanel2Layout.setVerticalGroup(
            jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, 0, 144, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCalculator)
                        .addComponent(jLabel10)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd)
                        .addComponent(btnRemoveMedicine)
                        .addComponent(btnSave)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtPhone.addTab("Order Detail", jpanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Information"));

        jLabel1.setText("Customer Name");

        cboCustomerName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboCustomerNameMouseClicked(evt);
            }
        });
        cboCustomerName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboCustomerNameItemStateChanged(evt);
            }
        });

        jLabel2.setText("Address");

        txaAddress.setColumns(20);
        txaAddress.setRows(5);
        jScrollPane1.setViewportView(txaAddress);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel3.setText("Relationship");

        buttonGroup1.add(radFamiliar);
        radFamiliar.setText("Familiar");
        radFamiliar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radFamiliar.setEnabled(false);
        radFamiliar.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(radUnfamiliar);
        radUnfamiliar.setText("UnFamiliar");
        radUnfamiliar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radUnfamiliar.setEnabled(false);
        radUnfamiliar.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel4.setText("Phone");

        txtCustomerPhoneOrder.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radFamiliar)
                    .addComponent(cboCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCustomerPhoneOrder, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(radUnfamiliar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cboCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(radUnfamiliar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(radFamiliar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustomerPhoneOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Order List"));

        tableOrderManage.setModel(new javax.swing.table.DefaultTableModel(
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
        tableOrderManage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOrderManageMouseClicked(evt);
            }
        });
        jScolpanel.setViewportView(tableOrderManage);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScolpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScolpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Order Detail"));

        tableOrderDetails.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableOrderDetails);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png"))); // NOI18N
        btnDelete.setText("Dalete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(675, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addGap(42, 42, 42))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btnDelete)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtPhone.addTab("Order Manage", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );

        txtPhone.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void tableOrderManageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOrderManageMouseClicked
// TODO add your handling code here:
          int r = tableOrderManage.getSelectedRow();
        if(r<0){
            return;
        }
        Orders objOrderList = new Orders();
        Vector vLoadOrderList = new Vector();
        Vector vlistOrderList = new Vector();
        vlistOrderList.addElement(Integer.parseInt(tableOrderManage.getValueAt(r,0).toString()));
        try {
            vLoadOrderList = Orders.getOrderDetailsByOrderCode(vlistOrderList);
            this.loadTableOrderDetails("orderDetails",vLoadOrderList);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_tableOrderManageMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
// TODO add your handling code here:
            deleteOrderManage();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cboCustomerNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboCustomerNameMouseClicked
// TODO add your handling code here:
         cbmdCustomerName.removeElement("---- Choice Customer ----");
    }//GEN-LAST:event_cboCustomerNameMouseClicked

    private void cboCustomerNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboCustomerNameItemStateChanged
  try {
// TODO add your handling code here:
            this.loadCustomer();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cboCustomerNameItemStateChanged

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
// TODO add your handling code here:
         try {
            this.AddNewOrderDetails();
            this.resetForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRemoveMedicineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMedicineActionPerformed
// TODO add your handling code here:
         for(int i =0; i<tableMedicineChoice.getRowCount();i++){
            if(tableMedicineChoice.isRowSelected(i)==true){
                modelTableMedicineChoice.removeRow(tableMedicineChoice.getSelectedRow());
            }
        }
    }//GEN-LAST:event_btnRemoveMedicineActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
         btnCalculator.setEnabled(true);
        if(txtOrderCode.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please choise the order","Please choice",JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            try {
                this.AddMedicine(nameTable,vLoad);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCalculatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculatorActionPerformed
// TODO add your handling code here:
        btnSave.setEnabled(true);
        if(!this.checkQuantity()){
            return;
        }
        int r = tableMedicineChoice.getRowCount();
        float total = 0.0f;
        
        for(int i =0;i<r;i++){
            if(radFamiliar.isSelected()==true){
                total += Float.parseFloat(tableMedicineChoice.getValueAt(i,4).toString())*Integer.parseInt(tableMedicineChoice.getValueAt(i,5).toString()) - Float.parseFloat(tableMedicineChoice.getValueAt(i,4).toString())*Integer.parseInt(tableMedicineChoice.getValueAt(i,5).toString())*0.05f;
            }else{
                total += Float.parseFloat(tableMedicineChoice.getValueAt(i,4).toString())*Integer.parseInt(tableMedicineChoice.getValueAt(i,5).toString());
            }
            
        }
        Float totalPrice = new Float(total);
        txtPrice.setText(totalPrice.toString());
    }//GEN-LAST:event_btnCalculatorActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
// TODO add your handling code here:
        this.deleteOrder();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void tableMedicineChoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMedicineChoiceMouseClicked
// TODO add your handling code here:
        btnRemove.setEnabled(true);
    }//GEN-LAST:event_tableMedicineChoiceMouseClicked

    private void tableOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOrdersMouseClicked
// TODO add your handling code here:
        // hien thi thong tin khach hang
        int r = tableOrders.getSelectedRow();
        if(r<0){
            return;
        }
        Customer objCustomer = new Customer();
        try {
            Vector vCustomer = new Vector();
            vCustomer = Customer.getAllCustomer();
            for(int j=0;j<vCustomer.size();j++) {
                objCustomer=(Customer)vCustomer.get(j);
                if(tableOrders.getValueAt(r,1).equals(objCustomer.getcustomerCode())){
                    txtCustomerName.setText(objCustomer.getcustomerName());
                    txaCustomerAddress.setText(objCustomer.getcustomerAddress());
                    if(objCustomer.getcustomerRelationship().equals("familiar")){
                        radFamiliar.setSelected(true);
                    }else
                        radUnfamiliar.setSelected(true);
                    txtCustomerPhone.setText(objCustomer.getcustomerPhone());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        txtOrderCode.setText(tableOrders.getValueAt(r,0).toString());
    }//GEN-LAST:event_tableOrdersMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCalculator;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemoveMedicine;
    private javax.swing.JButton btnSave;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScolpanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel jpanel2;
    private javax.swing.JRadioButton radFamiliar;
    private javax.swing.JRadioButton radFamiliardetail;
    private javax.swing.JRadioButton radUnfamiliar;
    private javax.swing.JRadioButton radUnfamiliarDetail;
    private javax.swing.JTable tableMedicineChoice;
    private javax.swing.JTable tableMedicineList;
    private javax.swing.JTable tableOrderDetails;
    private javax.swing.JTable tableOrderManage;
    private javax.swing.JTable tableOrders;
    private javax.swing.JTextArea txaAddress;
    private javax.swing.JTextArea txaCustomerAddress;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerPhone;
    private javax.swing.JTextField txtCustomerPhoneOrder;
    private javax.swing.JTextField txtOrderCode;
    private javax.swing.JTabbedPane txtPhone;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
    
}
