/*
 * BillManages.java
 *
 * Created on  April 10, 2012, 12:38 PM
 */

package BillManage;
import ConnectDatabase.DBHelper;
import CustomerManage.Customer;
import MeasureManage.Measures;
import MedicineManage.Medicines;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


import Check.Check;
import java.text.ParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author  
 */
public class BillManages extends javax.swing.JPanel {
    private DefaultTableModel modelTableBills = new DefaultTableModel();
    Vector vLoad = new Vector();
     
    String nameTable = "Bill";
    
    String nameTableMedicine= "vMedicines";
    
    private DefaultTableModel modelTableMedicine = new DefaultTableModel();
    private DefaultTableModel modelTableMedicineChoice = new DefaultTableModel();
    
    JTextField txtQuantity = new JTextField();
    
    Vector vLoadBillNoDetails = new Vector();
    Vector vloadMedicineList = new Vector();
    
    //----------------------Pay Bill_---------------------------------
     private float payedMoney  = 0.0f;
    
    private DefaultComboBoxModel cbmdCustomerName = new DefaultComboBoxModel();
    Customer objCustomer = new Customer();
    private DefaultTableModel modelTableBill = new DefaultTableModel();
    Vector vLoadBill = new Vector();
    public BillManages() {
    /** Creates new form BillManages */
        initComponents();
         try {
            
            vLoadBillNoDetails = Bills.getAllBillNotDetails();
            this.loadTableBillNotDetails("vBillNotDetails",vLoadBillNoDetails);
            
            vloadMedicineList = Medicines.getAllViewMedicinesForOrder();
            this.loadTableMedicineList(nameTableMedicine,vloadMedicineList);
            
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
        
        btnSaveToBillDetails.setEnabled(false);
        btnCalculator.setEnabled(false);
        btnRemoveMedicineChoice.setEnabled(false);
        txtTotalPrice.setEditable(false);
        txtBillCode.setEditable(false);
        
        //---------------------Pay Bill---------------------
        
        Vector vCustomer=new Vector();
        try {
            vCustomer=Customer.getAllCustomer();
            cbmdCustomerName.removeAllElements();
            cbmdCustomerName.addElement("---- Choice Customer ----");

            for(int j=0;j<vCustomer.size();j++) {
                objCustomer=(Customer)vCustomer.get(j);
                cbmdCustomerName.addElement(objCustomer.getcustomerName());
            }
            cblCustmerName.setModel(cbmdCustomerName);
                        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void loadTableBillNotDetails(String nameTable,Vector vLoad){
        modelTableBills.setNumRows(0);
        Vector columnBill = new Vector();
        columnBill.addElement("Code");
        columnBill.addElement("Type");
        columnBill.addElement("CustomerCode");
        columnBill.addElement("DateStart");
        columnBill.addElement("ExpiredTime");
        columnBill.addElement("Tax");
        columnBill.addElement("AddressToDeliver");
        modelTableBills.setColumnIdentifiers(columnBill);
        Bills objBills = new Bills();
        for(int i=0; i< vLoad.size();i++){
            objBills = (Bills) vLoad.get(i);
            Vector rowBills = new Vector();
            rowBills.addElement(objBills.getbillCode());
            rowBills.addElement(objBills.getbillType());
            rowBills.addElement(objBills.getcustomerCode());
            rowBills.addElement(objBills.getdateStart());
            rowBills.addElement(objBills.getexpiredTime());
            rowBills.addElement(objBills.gettax());
            rowBills.addElement(objBills.getaddressToDeliver());
            modelTableBills.addRow(rowBills);
        }
        tableBills.setModel(modelTableBills);
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
    
     
    public void deleteBill(){
        Vector vLoadBill = new Vector();
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Bill ?","Remove Bill",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableBills.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    int billCode = Integer.parseInt(tableBills.getValueAt(rArr[i],0).toString());
                    test = Bills.deleteBill(billCode);
                    if(test == 1)
                        count++;
                }
                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Bill","Remove Bill",JOptionPane.INFORMATION_MESSAGE);
                    vLoadBill = Bills.getAllBillNotDetails();
                    this.loadTableBillNotDetails("vBillNotDetails",vLoadBill);
                }else
                    JOptionPane.showMessageDialog(this,"Delete Bill not exist bill details !","Remove Bill",JOptionPane.INFORMATION_MESSAGE);
                    vLoadBill = Bills.getAllBillNotDetails();
                    this.loadTableBillNotDetails("vBillNotDetails",vLoadBill);
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    
     public void resetForm(){
        txtBillCode.setText("");
        txtCustomerName.setText("");
        txaCustomerAddress.setText("");
        txtTotalPrice.setText("");
        txtCustomerPhone.setText("");
        btnSaveToBillDetails.setEnabled(false);
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
    
    
    public void AddNewBillDetails() throws SQLException{
        int test = 0;
        int testUpdate = 0;
        int testUpdateAvaiableAmount = 0;
        boolean flag = false;
        Vector vloadMeasure = new Vector();
        Vector vlistMeasure = new Vector();
        Vector vloadMedicine =new Vector();
        Bills objBill= new Bills();
        Medicines objMedicine = new Medicines();
        int rTableBills = tableBills.getRowCount();
        int rTableChoice = tableMedicineChoice.getRowCount();
        // can xem xet lai cho nay tai sao lai bao loi sai ?
        objBill.setbillCode(Integer.parseInt(txtBillCode.getText().trim()));
        
        for(int i = 0; i < rTableChoice; i++){
            
            objBill.setmedicineCode(Integer.parseInt(tableMedicineChoice.getValueAt(i,0).toString()));
            objMedicine.setMedicineCode(Integer.parseInt(tableMedicineChoice.getValueAt(i,0).toString()));
            // lay ra ma cua don vi tinh theo ten
            vlistMeasure.addElement(tableMedicineChoice.getValueAt(i,3).toString());
            vloadMeasure = Measures.getMeasureCodeByName(vlistMeasure);
            Measures objMeasure = new Measures();
            for(int j=0; j< vloadMeasure.size();j++){
                objMeasure = (Measures) vloadMeasure.get(j);
                objBill.setmeasureCode(objMeasure.getmeasureCode());
            }
            
            objBill.setquantity(Integer.parseInt(tableMedicineChoice.getValueAt(i,5).toString()));
            objMedicine.setAvaiableAmount(Integer.parseInt(tableMedicineChoice.getValueAt(i,5).toString()));
            test = Bills.insertBillDetails(objBill);
            testUpdateAvaiableAmount = Medicines.updateAvaiableAmountMedicineDetails(objMedicine);
            if(test == 1 && testUpdateAvaiableAmount ==1){
                flag = true;
            }else{
                flag = false;
            }
        }
        Bills objBillUpdate = new Bills();
        int billCode = Integer.parseInt(txtBillCode.getText().trim());
        objBillUpdate.setbillCode(billCode);
        float totalPrice = Float.parseFloat(txtTotalPrice.getText().trim().toString());
        objBillUpdate.setprice(totalPrice);
        testUpdate = Bills.updatePriceForBill(objBillUpdate);
        
        if(flag == true && testUpdate ==1){
            JOptionPane.showMessageDialog(this,"Add new Bill Details Successfully !","Add new Bill Details",JOptionPane.INFORMATION_MESSAGE);
            vLoad = Bills.getAllBillNotDetails();
            this.loadTableBillNotDetails(nameTable,vLoad);
            vloadMedicine = Medicines.getAllViewMedicinesForOrder();
            this.loadTableMedicineList(nameTableMedicine,vloadMedicine);
        }else
            JOptionPane.showMessageDialog(this,"Add new Bill Details Error !","Add new Bill Details",JOptionPane.ERROR_MESSAGE);
    }
    ///--------------------------------Pay Bill------------------------------------------------------------------------
     public boolean checkPay(){
        Check c = new Check();        
        if(!c.checkNumber(txtPayment.getText())){
            JOptionPane.showMessageDialog(this,"Payment invalid !","Payment",JOptionPane.ERROR_MESSAGE);
            txtPayment.setText("");
            txtPayment.requestFocus();
            return false;
        }
        if(!c.checkDateIn(txtDate.getText())){
            JOptionPane.showMessageDialog(this,"Date pay invalid !","Payment",JOptionPane.ERROR_MESSAGE);
            txtDate.setText("");
            txtDate.requestFocus();
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
            if(cblCustmerName.getSelectedItem().equals(objCustomer.getcustomerName())){
                vlistBill.addElement(objCustomer.getcustomerCode());
            }
        }
        vLoadBill = Bills.getAllBillsWaitting(vlistBill);
        this.loadTableBill("vBillsWaitting",vLoadBill);
    }
    
    public void resetFormPay(){
        txtBillCode.setText("");
        txtPayment.setText("");        
    }
   
    public void updateStatus() throws SQLException{
        int test = 0;
        Bills objBill = new Bills();
        objBill.setbillCode(Integer.parseInt(txtBillCode.getText().trim()));
        if(Float.parseFloat(txtPayed.getText().trim()) >= Float.parseFloat(txtTotalPrice.getText().trim())){
//            objBill.setstatus("complete");
//        }else
//            objBill.setstatus("waitting");
//        
//        test = Bills.updateStatusForBill(objBill);
//        if(test == 1){
            JOptionPane.showMessageDialog(this,"thanh toan song !","Pay",JOptionPane.INFORMATION_MESSAGE);
            //this.loadCustomer();
        }
    }
    
    public void loadTableBill(String nameTable,Vector vLoad){
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
        tablePay.setModel(modelTableBill);
    }
    
    public void saveNewPay() throws SQLException{
        
        if(!this.checkPay()){
            return;
        }
        
        
        int test = 0;
        Bills objBill = new Bills();
        objBill.setbillCode(Integer.parseInt(txtBillCode.getText().trim()));
        objBill.setdatePay(txtDate.getText());
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
        int r = tablePay.getSelectedRow();
        if(r<0){
            return;
        }
        txtBillCode.setText(tablePay.getValueAt(r,0).toString());
        txtTotalPrice.setText(tablePay.getValueAt(r,3).toString());
        int billCode = Integer.parseInt(tablePay.getValueAt(r,0).toString());
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBills = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        radExport = new javax.swing.JRadioButton();
        radImport = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        txtTax = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtBillCode = new javax.swing.JTextField();
        btnRemoveBill = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        radFamiliar = new javax.swing.JRadioButton();
        radUnfamiliar = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtCustomerPhone = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaCustomerAddress = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMedicineChoice = new javax.swing.JTable();
        btnCalculator = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtTotalPrice = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnAddDetail = new javax.swing.JButton();
        btnRemoveMedicineChoice = new javax.swing.JButton();
        btnSaveToBillDetails = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableMedicineList = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cblCustmerName = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        txtTotalPriceInBill = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPayment = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        try {
            txtDate = new JFormattedTextField(new MaskFormatter("####-##-##"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtBillCodePay = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtPayed = new javax.swing.JTextField();
        btnPay = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablePay = new javax.swing.JTable();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Bill Information"));

        tableBills.setModel(new javax.swing.table.DefaultTableModel(
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
        tableBills.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBillsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBills);

        jLabel1.setText("Bill Type");

        buttonGroup1.add(radExport);
        radExport.setText("Export");
        radExport.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radExport.setEnabled(false);
        radExport.setMargin(new java.awt.Insets(0, 0, 0, 0));
        radExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radExportActionPerformed(evt);
            }
        });

        buttonGroup1.add(radImport);
        radImport.setText("Import");
        radImport.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radImport.setEnabled(false);
        radImport.setMargin(new java.awt.Insets(0, 0, 0, 0));
        radImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radImportActionPerformed(evt);
            }
        });

        jLabel2.setText("Tax");

        txtTax.setEnabled(false);

        jLabel3.setText("Bill Code");

        txtBillCode.setEnabled(false);

        btnRemoveBill.setText("Remove");
        btnRemoveBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(radExport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radImport))
                    .addComponent(txtBillCode, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnRemoveBill))
                .addContainerGap(77, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(radExport)
                    .addComponent(radImport)
                    .addComponent(jLabel2)
                    .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBillCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnRemoveBill))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Information"));

        jLabel4.setText("Customer Name");

        txtCustomerName.setEnabled(false);

        jLabel5.setText("Address");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 7, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 58, Short.MAX_VALUE)
        );

        jLabel6.setText("Relationship");

        buttonGroup2.add(radFamiliar);
        radFamiliar.setText("Familiar");
        radFamiliar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radFamiliar.setEnabled(false);
        radFamiliar.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup2.add(radUnfamiliar);
        radUnfamiliar.setText("Unfamiliar");
        radUnfamiliar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radUnfamiliar.setEnabled(false);
        radUnfamiliar.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabel7.setText("Phone");

        txtCustomerPhone.setEnabled(false);

        txaCustomerAddress.setColumns(20);
        txaCustomerAddress.setRows(5);
        jScrollPane2.setViewportView(txaCustomerAddress);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txtCustomerPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(radFamiliar)
                        .addGap(20, 20, 20)
                        .addComponent(radUnfamiliar)
                        .addGap(21, 21, 21)))
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(46, 46, 46))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radFamiliar)
                            .addComponent(radUnfamiliar)
                            .addComponent(jLabel6))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCustomerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine Has Choice"));

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
        jScrollPane3.setViewportView(tableMedicineChoice);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnCalculator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/calculate.png"))); // NOI18N
        btnCalculator.setText("Calculator");
        btnCalculator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculatorActionPerformed(evt);
            }
        });

        jLabel8.setText("Total Price");

        txtTotalPrice.setEnabled(false);

        jLabel9.setText("VND");

        btnAddDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/up.png"))); // NOI18N
        btnAddDetail.setText("Add");
        btnAddDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDetailActionPerformed(evt);
            }
        });

        btnRemoveMedicineChoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/down.png"))); // NOI18N
        btnRemoveMedicineChoice.setText("Remove");
        btnRemoveMedicineChoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMedicineChoiceActionPerformed(evt);
            }
        });

        btnSaveToBillDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/save.png"))); // NOI18N
        btnSaveToBillDetails.setText("Save");
        btnSaveToBillDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveToBillDetailsActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Please choice medicine in list"));

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
        jScrollPane4.setViewportView(tableMedicineList);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnCalculator)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel9)
                        .addGap(187, 187, 187)
                        .addComponent(btnAddDetail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveMedicineChoice)
                        .addGap(19, 19, 19)
                        .addComponent(btnSaveToBillDetails))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCalculator)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(btnAddDetail)
                    .addComponent(btnRemoveMedicineChoice)
                    .addComponent(btnSaveToBillDetails)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jTabbedPane1.addTab("Bill Detail", new javax.swing.ImageIcon(getClass().getResource("/Icon/Info.png")), jPanel1); // NOI18N

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Pay Information"));

        jLabel10.setText("Cusotmer Name");

        cblCustmerName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cblCustmerNameMouseClicked(evt);
            }
        });
        cblCustmerName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cblCustmerNameItemStateChanged(evt);
            }
        });

        jLabel11.setText("Total Price In Bill");

        txtTotalPriceInBill.setEnabled(false);

        jLabel12.setText("Payment");

        jLabel13.setText("Date");

        jLabel14.setText("[yyyy/mm/dd]");

        jLabel15.setText("Bill Code");

        txtBillCodePay.setEnabled(false);

        jLabel16.setText("Payed");

        txtPayed.setEnabled(false);
        txtPayed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPayedActionPerformed(evt);
            }
        });

        btnPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/login.png"))); // NOI18N
        btnPay.setText("Pay");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDate)
                            .addComponent(txtPayment)
                            .addComponent(txtTotalPriceInBill)
                            .addComponent(cblCustmerName, 0, 143, Short.MAX_VALUE))
                        .addGap(206, 206, 206)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPayed)
                                    .addComponent(txtBillCodePay, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)))
                            .addComponent(btnPay))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cblCustmerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(txtBillCodePay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(txtTotalPriceInBill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(6, 6, 6)))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPay)))
                    .addComponent(txtPayed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Please choice the  Bill to payment"));

        tablePay.setModel(new javax.swing.table.DefaultTableModel(
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
        tablePay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePayMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablePay);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48))
        );

        jTabbedPane1.addTab("Pay Bill", new javax.swing.ImageIcon(getClass().getResource("/Icon/Info.png")), jPanel2); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tablePayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePayMouseClicked
    this.loadMoney();
    }//GEN-LAST:event_tablePayMouseClicked

    private void cblCustmerNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cblCustmerNameMouseClicked
    cbmdCustomerName.removeElement("---- Choice Customer ----");
    }//GEN-LAST:event_cblCustmerNameMouseClicked

    private void cblCustmerNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cblCustmerNameItemStateChanged
    try {
// TODO add your handling code here:
            this.loadCustomer();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cblCustmerNameItemStateChanged

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
try {
// TODO add your handling code here:
            this.saveNewPay();
           // this.updateStatus();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnSaveToBillDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveToBillDetailsActionPerformed
// TODO add your handling code here:
          try {
// TODO add your handling code here:
            this.AddNewBillDetails();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSaveToBillDetailsActionPerformed

    private void btnRemoveMedicineChoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMedicineChoiceActionPerformed
// TODO add your handling code here:
         for(int i =0; i<tableMedicineChoice.getRowCount();i++){
            if(tableMedicineChoice.isRowSelected(i)==true){
                modelTableMedicineChoice.removeRow(tableMedicineChoice.getSelectedRow());
            }
        }
    }//GEN-LAST:event_btnRemoveMedicineChoiceActionPerformed

    private void btnAddDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDetailActionPerformed
// TODO add your handling code here:
         btnCalculator.setEnabled(true);
        btnRemoveMedicineChoice.setEnabled(true);
        if(txtBillCode.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please choice the order","Please choice",JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            try {
                this.AddMedicine(nameTable,vLoad);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnAddDetailActionPerformed

    private void btnCalculatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculatorActionPerformed
// TODO add your handling code here:
        btnSaveToBillDetails.setEnabled(true);
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
        total = total + (total * Float.parseFloat(txtTax.getText().trim()));        
        Float totalPrice = new Float(total);
        txtTotalPrice.setText(totalPrice.toString());
    }//GEN-LAST:event_btnCalculatorActionPerformed

    private void btnRemoveBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveBillActionPerformed
// TODO add your handling code here:
         this.deleteBill();
    }//GEN-LAST:event_btnRemoveBillActionPerformed

    private void tableBillsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBillsMouseClicked
// TODO add your handling code here:
        int r = tableBills.getSelectedRow();
        if(r<0){
            return;
        }
        Customer objCustomer = new Customer();
        try {
            Vector vCustomer = new Vector();
            vCustomer = Customer.getAllCustomer();
            for(int j=0;j<vCustomer.size();j++) {
                objCustomer=(Customer)vCustomer.get(j);
                if(tableBills.getValueAt(r,2).equals(objCustomer.getcustomerCode())){
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
        if(tableBills.getValueAt(r,1).toString().equals("Export")){
            radExport.setSelected(true);
        }
        if(tableBills.getValueAt(r,1).toString().equals("Import")){
            radImport.setSelected(true);
        }
        txtBillCode.setText(tableBills.getValueAt(r,0).toString());
        txtTax.setText(tableBills.getValueAt(r,5).toString());
    }//GEN-LAST:event_tableBillsMouseClicked

    private void radExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radExportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radExportActionPerformed

    private void radImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radImportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radImportActionPerformed

    private void txtPayedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPayedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPayedActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDetail;
    private javax.swing.JButton btnCalculator;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnRemoveBill;
    private javax.swing.JButton btnRemoveMedicineChoice;
    private javax.swing.JButton btnSaveToBillDetails;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cblCustmerName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton radExport;
    private javax.swing.JRadioButton radFamiliar;
    private javax.swing.JRadioButton radImport;
    private javax.swing.JRadioButton radUnfamiliar;
    private javax.swing.JTable tableBills;
    private javax.swing.JTable tableMedicineChoice;
    private javax.swing.JTable tableMedicineList;
    private javax.swing.JTable tablePay;
    private javax.swing.JTextArea txaCustomerAddress;
    private javax.swing.JTextField txtBillCode;
    private javax.swing.JTextField txtBillCodePay;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerPhone;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtPayed;
    private javax.swing.JTextField txtPayment;
    private javax.swing.JTextField txtTax;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextField txtTotalPriceInBill;
    // End of variables declaration//GEN-END:variables
    
}
