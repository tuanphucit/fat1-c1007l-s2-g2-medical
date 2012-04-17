/*
 * Search.java
 *
 * Created on April 11, 2012, 8:47 AM
 */

package Search;

import Check.Check;
import CustomerManage.Customer;
import MedicineManage.Medicines;
import MedicineTypeManage.MedicineType;
import SupplierManage.Suppliers;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  
 */
public class Search extends javax.swing.JPanel {
    private DefaultTableModel modelTableSupplier  = new DefaultTableModel();
    private DefaultTableModel modelTableCustomer  = new DefaultTableModel();
    private DefaultTableModel modelTableMedicine  = new DefaultTableModel();
    private DefaultComboBoxModel cbmdCustomerType = new DefaultComboBoxModel();
    
    private DefaultComboBoxModel cbmdMedicineType = new DefaultComboBoxModel();
    private DefaultComboBoxModel cbmdSupplier = new DefaultComboBoxModel();
    
    Vector vLoad = new Vector();
    Suppliers objSupplier = new Suppliers();
    MedicineType objMedicineType = new MedicineType();
    
    /** Creates new form Search */
    public Search() {
        initComponents();
        try {
            vLoad = Suppliers.getAllSupplier();
            this.loadTableSupplier("Supplier",vLoad);
            vLoad = Customer.getAllCustomer();
            this.loadTableCustomer("Customer",vLoad);
            vLoad = Medicines.getAllViewMedicines();
            this.loadTableMedicine("vMedicines",vLoad);
            
            cbmdCustomerType.removeAllElements();
            cbmdCustomerType.addElement("--- Customer Type---");
            cbmdCustomerType.addElement("Dealers");
            cbmdCustomerType.addElement("Retailers");
            cbmdCustomerType.addElement("Distributors");
            cboCustomerType.setModel(cbmdCustomerType);
            
            cbmdSupplier .removeAllElements();
            cbmdSupplier .addElement("--- Supplier Type---");
            cbmdSupplier .addElement("TRAPHACO");
            cbmdSupplier .addElement("VINPHACO");
            cbmdSupplier .addElement("BAOLONG");
            cbmdSupplier.addElement("AlCON");
            cbmdSupplier.addElement("AUSTRAPHARM");
            cboSupplier.setModel(cbmdSupplier );
            
            cbmdMedicineType.removeAllElements();
            cbmdMedicineType.addElement("--- Medicine Type---");
            cbmdMedicineType.addElement("Cardiovascular");
            cbmdMedicineType.addElement("Nervous");
            cbmdMedicineType.addElement("Digestive");
            cbmdMedicineType.addElement("Hocmon");
            cbmdMedicineType.addElement("Cancer");
            cbmdMedicineType.addElement("Touches the skin");
            cbmdMedicineType.addElement("Vitamin and Minerals");
            cbmdMedicineType.addElement("Germicide");
            cbmdMedicineType.addElement("respiratory");
            cboMedicineType.setModel(cbmdMedicineType);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
     public boolean checkSearchMedicine(){
        Check c = new Check();
        if(txtMedicineName.getText().contains("'")){
            JOptionPane.showMessageDialog(this,"Medicine Name Invalid","Search Medicine",JOptionPane.ERROR_MESSAGE);
            txtMedicineName.setText("");
            txtMedicineName.requestFocus();
            return false;
        }
        return true;
    }
    
     public boolean checkSearchSupplier(){
        Check c= new Check();
        if(txtSupplierName.getText().contains("'")){
            JOptionPane.showMessageDialog(this,"Supplier Name Invalid !","Search Supplier",JOptionPane.ERROR_MESSAGE);
            txtSupplierName.setText("");
            txtSupplierName.requestFocus();
            return false;
        }else  if(txtSupplierAddress.getText().contains("'")){
            JOptionPane.showMessageDialog(this,"Supplier Address Invalid !","Search Supplier",JOptionPane.ERROR_MESSAGE);
            txtSupplierAddress.setText("");
            txtSupplierAddress.requestFocus();
            return false;
        }
        return true;
    }
    
     public boolean checkSearchCustomer(){
        Check c = new Check();
        if(txtCustomerName.getText().contains("'")){
            JOptionPane.showMessageDialog(this,"Customer Name Invalid !","Search Customer",JOptionPane.ERROR_MESSAGE);
            txtCustomerName.setText("");
            txtCustomerName.requestFocus();
            return false;
        } else if(txtCustomerAddress.getText().contains("'")){
            JOptionPane.showMessageDialog(this,"Customer Name Invalid !","Search Customer",JOptionPane.ERROR_MESSAGE);
            txtCustomerAddress.setText("");
            txtCustomerAddress.requestFocus();
            return false;
        }
        return true;
    }
    
     public void loadTableMedicine(String nameTable,Vector vLoad) throws SQLException{
        modelTableMedicine.setNumRows(0);
        Vector columnMedicine = new Vector();
        columnMedicine.addElement("Code");
        columnMedicine.addElement("Name");
        columnMedicine.addElement("Type");
        columnMedicine.addElement("Measure");
        columnMedicine.addElement("PricePerUnit");
        columnMedicine.addElement("Amount");
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
        tableMedicineDetails.setModel(modelTableMedicine);
    }
     
      public void loadTableCustomer(String nameTable,Vector vLoad) throws SQLException{
        modelTableCustomer.setNumRows(0);
        Vector columnCustomer = new Vector();
        columnCustomer.addElement("Code");
        columnCustomer.addElement("Name");
        columnCustomer.addElement("Type");
        columnCustomer.addElement("Phone");
        columnCustomer.addElement("Address");
        columnCustomer.addElement("Relation");
        
        modelTableCustomer.setColumnIdentifiers(columnCustomer);
        Customer objCustomer = new Customer();
        for(int i=0; i<vLoad.size();i++){
            objCustomer = (Customer) vLoad.get(i);
            Vector rowCustomer = new Vector();
            rowCustomer.addElement(objCustomer.getcustomerCode());
            rowCustomer.addElement(objCustomer.getcustomerName());
            rowCustomer.addElement(objCustomer.getcustomerType());
            rowCustomer.addElement(objCustomer.getcustomerPhone());
            rowCustomer.addElement(objCustomer.getcustomerAddress());
            rowCustomer.addElement(objCustomer.getcustomerRelationship());
            modelTableCustomer.addRow(rowCustomer);
        }
        tableCustomerDetails.setModel(modelTableCustomer);
    }
      
     public void loadTableSupplier(String nameTable,Vector vLoad) throws SQLException{
        modelTableSupplier.setNumRows(0);
        Vector columnSupplier = new Vector();
        columnSupplier.addElement("Code");
        columnSupplier.addElement("Name");
        columnSupplier.addElement("Address");
        columnSupplier.addElement("Phone");
        columnSupplier.addElement("Email");
        modelTableSupplier.setColumnIdentifiers(columnSupplier);
        Suppliers objSupplier = new Suppliers();
        for(int i=0; i<vLoad.size();i++){
            objSupplier = (Suppliers) vLoad.get(i);
            Vector rowSupplier = new Vector();
            rowSupplier.addElement(objSupplier.getsupplierCode());
            rowSupplier.addElement(objSupplier.getsupplierName());
            rowSupplier.addElement(objSupplier.getsupplierAddress());
            rowSupplier.addElement(objSupplier.getsupplierPhone());
            rowSupplier.addElement(objSupplier.getsupplierEmail());
            modelTableSupplier.addRow(rowSupplier);
        }
        tableSupplierDetails.setModel(modelTableSupplier);
    }
     //------------------------ ham serach Medicines----------------
     //-------------------------------------------------------------
      public void searchMedicine() throws SQLException{
        if(!this.checkSearchMedicine()){
            return;
        }
        Vector vList = new Vector();
        if(radDomestic.isSelected()==true){
            //-----------------------------------------
            if(txtMedicineName.getText()!="" && cboMedicineType.getSelectedIndex()==0){
                vList.addElement("Domestic");
                vList.addElement(txtMedicineName.getText().trim());
                vLoad = Medicines.searchMedicineByName(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText()!="" && cboMedicineType.getSelectedIndex()!=0
                    && cboSupplier.getSelectedIndex()==0){
                vList.addElement("Domestic");
                vList.addElement(txtMedicineName.getText().trim());
                vList.addElement(cboMedicineType.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineByNameAndType(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText()!="" && cboMedicineType.getSelectedIndex()==0
                    && cboSupplier.getSelectedIndex()!=0){
                vList.addElement("Domestic");
                vList.addElement(txtMedicineName.getText().trim());
                vList.addElement(cboSupplier.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineByNameAndSupplier(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText().equals("") && cboMedicineType.getSelectedIndex()==0
                    && cboSupplier.getSelectedIndex()!=0){
                vList.addElement("Domestic");
                vList.addElement(cboSupplier.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineBySupplier(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText().equals("") && cboMedicineType.getSelectedIndex()!=0
                    && cboSupplier.getSelectedIndex()!=0){
                vList.addElement("Domestic");
                vList.addElement(cboMedicineType.getSelectedItem().toString());
                vList.addElement(cboSupplier.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineByTypeAndSupplier(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText().equals("") && cboMedicineType.getSelectedIndex()!=0
                    && cboSupplier.getSelectedIndex()==0){
                vList.addElement("Domestic");
                vList.addElement(cboMedicineType.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineByType(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            }else{
                vList.addElement("Domestic");
                vLoad = Medicines.searchMedicineByOrigin(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            }
            
        }else{
            if(txtMedicineName.getText()!="" && cboMedicineType.getSelectedIndex()==0
                    && cboSupplier.getSelectedIndex()==0){
                vList.addElement("Foreign");
                vList.addElement(txtMedicineName.getText().trim());
                vLoad = Medicines.searchMedicineByName(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText()!="" && cboMedicineType.getSelectedIndex()!=0
                    && cboSupplier.getSelectedIndex()==0){
                vList.addElement("Foreign");
                vList.addElement(txtMedicineName.getText().trim());
                vList.addElement(cboMedicineType.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineByNameAndType(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText()!="" && cboMedicineType.getSelectedIndex()==0
                    && cboSupplier.getSelectedIndex()!=0){
                vList.addElement("Foreign");
                vList.addElement(txtMedicineName.getText().trim());
                vList.addElement(cboSupplier.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineByNameAndSupplier(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText().equals("") && cboMedicineType.getSelectedIndex()==0
                    && cboSupplier.getSelectedIndex()!=0){
                vList.addElement("Foreign");
                vList.addElement(cboSupplier.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineBySupplier(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText().equals("") && cboMedicineType.getSelectedIndex()!=0
                    && cboSupplier.getSelectedIndex()!=0){
                vList.addElement("Foreign");
                vList.addElement(cboMedicineType.getSelectedItem().toString());
                vList.addElement(cboSupplier.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineByTypeAndSupplier(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else if(txtMedicineName.getText().equals("") && cboMedicineType.getSelectedIndex()!=0
                    && cboSupplier.getSelectedIndex()==0){
                vList.addElement("Foreign");
                vList.addElement(cboMedicineType.getSelectedItem().toString());
                vLoad = Medicines.searchMedicineByType(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            } else{
                vList.addElement("Foreign");
                vLoad = Medicines.searchMedicineByOrigin(vList);
                this.loadTableMedicine("vMedicines",vLoad);
            }
        }
    }
      //------------------------ ham search customer--------------------------
     //-----------------------------------------------------------------------
      public void searchCustomer() throws SQLException{
        if(!this.checkSearchCustomer()){
            return;
        }
        Vector vlist = new Vector();
        if(radFamiliar.isSelected()==true){
            //---------------------------------------
            if(txtCustomerName.getText()!="" && cboCustomerType.getSelectedIndex()==0
                    && txtCustomerAddress.getText().equals("")){
                vlist.addElement("familiar");
                vlist.addElement(txtCustomerName.getText().trim());
                vLoad = Customer.searchCustomerByName(vlist);
                this.loadTableCustomer("Customer",vLoad);
                
            }else if(txtCustomerName.getText()!="" && cboCustomerType.getSelectedIndex()!=0
                    && txtCustomerAddress.getText().equals("")){
                vlist.addElement("familiar");
                vlist.addElement(txtCustomerName.getText().trim());
                vlist.addElement(cboCustomerType.getSelectedItem().toString());
                vLoad = Customer.searchCustomerByNameAndType(vlist);
                this.loadTableCustomer("Customer",vLoad);
                
            }else if(txtCustomerName.getText()!="" && cboCustomerType.getSelectedIndex()==0
                    && txtCustomerAddress.getText()!=""){
                vlist.addElement("familiar");
                vlist.addElement(txtCustomerName.getText().trim());
                vlist.addElement(txtCustomerAddress.getText().trim());
                vLoad = Customer.searchCustomerByNameAndAddress(vlist);
                this.loadTableCustomer("Customer",vLoad);
            }
            //-----------------------------------------------------
            else if(txtCustomerName.getText().equals("") && cboCustomerType.getSelectedIndex()==0
                    && txtCustomerAddress.getText()!=""){
                vlist.addElement("familiar");
                vlist.addElement(txtCustomerAddress.getText().trim());
                vLoad = Customer.searchCustomerByAddress(vlist);
                this.loadTableCustomer("Customer",vLoad);
            } else if(txtCustomerName.getText().equals("") && cboCustomerType.getSelectedIndex()!=0
                    && txtCustomerAddress.getText()!=""){
                vlist.addElement("familiar");
                vlist.addElement(cboCustomerType.getSelectedItem().toString());
                vlist.addElement(txtCustomerAddress.getText().trim());
                vLoad = Customer.searchCustomerByTypeAndAddress(vlist);
                this.loadTableCustomer("Customer",vLoad);
            } else if(txtCustomerName.getText().equals("") && cboCustomerType.getSelectedIndex()!=0
                    && txtCustomerAddress.getText().equals("")){
                vlist.addElement("familiar");
                vlist.addElement(cboCustomerType.getSelectedItem().toString());
                vLoad = Customer.searchCustomerByTypeAndAddress(vlist);
                this.loadTableCustomer("Customer",vLoad);
            } else if(txtCustomerName.getText()!="" && cboCustomerType.getSelectedIndex()!=0
                    && txtCustomerAddress.getText()!=""){
                vlist.addElement("familiar");
                vlist.addElement(txtCustomerName.getText().trim());
                vlist.addElement(cboCustomerType.getSelectedItem().toString());
                vlist.addElement(txtCustomerAddress.getText().trim());
                vLoad = Customer.searchCustomerAdvanced(vlist);
                this.loadTableCustomer("Customer",vLoad);
            } else{
                vlist.addElement("familiar");
                vLoad = Customer.searchCustomerByRelation(vlist);
                this.loadTableCustomer("Customer",vLoad);
            }
            
        }else {
            if(txtCustomerName.getText()!="" && cboCustomerType.getSelectedIndex()==0
                    && txtCustomerAddress.getText().equals("")){
                vlist.addElement("unfamiliar");
                vlist.addElement(txtCustomerName.getText().trim());
                vLoad = Customer.searchCustomerByName(vlist);
                this.loadTableCustomer("Customer",vLoad);
                
            }else if(txtCustomerName.getText()!="" && cboCustomerType.getSelectedIndex()!=0
                    && txtCustomerAddress.getText().equals("")){
                vlist.addElement("unfamiliar");
                vlist.addElement(txtCustomerName.getText().trim());
                vlist.addElement(cboCustomerType.getSelectedItem().toString());
                vLoad = Customer.searchCustomerByNameAndType(vlist);
                this.loadTableCustomer("Customer",vLoad);
                
            }else if(txtCustomerName.getText()!="" && cboCustomerType.getSelectedIndex()==0
                    && txtCustomerAddress.getText()!=""){
                vlist.addElement("unfamiliar");
                vlist.addElement(txtCustomerName.getText().trim());
                vlist.addElement(txtCustomerAddress.getText().trim());
                vLoad = Customer.searchCustomerByNameAndAddress(vlist);
                this.loadTableCustomer("Customer",vLoad);
            }
            //-----------------------------------------------------
            else if(txtCustomerName.getText().equals("") && cboCustomerType.getSelectedIndex()==0
                    && txtCustomerAddress.getText()!=""){
                vlist.addElement("unfamiliar");
                vlist.addElement(txtCustomerAddress.getText().trim());
                vLoad = Customer.searchCustomerByAddress(vlist);
                this.loadTableCustomer("Customer",vLoad);
            } else if(txtCustomerName.getText().equals("") && cboCustomerType.getSelectedIndex()!=0
                    && txtCustomerAddress.getText()!=""){
                vlist.addElement("unfamiliar");
                vlist.addElement(cboCustomerType.getSelectedItem().toString());
                vlist.addElement(txtCustomerAddress.getText().trim());
                vLoad = Customer.searchCustomerByTypeAndAddress(vlist);
                this.loadTableCustomer("Customer",vLoad);
            } else if(txtCustomerName.getText().equals("") && cboCustomerType.getSelectedIndex()!=0
                    && txtCustomerAddress.getText().equals("")){
                vlist.addElement("unfamiliar");
                vlist.addElement(cboCustomerType.getSelectedItem().toString());
                vLoad = Customer.searchCustomerByTypeAndAddress(vlist);
                this.loadTableCustomer("Customer",vLoad);
            }else if(txtCustomerName.getText()!="" && cboCustomerType.getSelectedIndex()!=0
                    && txtCustomerAddress.getText()!=""){
                vlist.addElement("unfamiliar");
                vlist.addElement(txtCustomerName.getText().trim());
                vlist.addElement(cboCustomerType.getSelectedItem().toString());
                vlist.addElement(txtCustomerAddress.getText().trim());
                vLoad = Customer.searchCustomerAdvanced(vlist);
                this.loadTableCustomer("Customer",vLoad);
            } else{
                vlist.addElement("unfamiliar");
                vLoad = Customer.searchCustomerByRelation(vlist);
                this.loadTableCustomer("Customer",vLoad);
            }
        }
    }
      
      //--------------------- ham search supplier-----------------------
      //*----------------------------------------------------------------
       public void searchSupplier() throws SQLException{
        if(!this.checkSearchSupplier()){
            return;
        }
        Vector vlist = new Vector();
        if(txtSupplierName.getText()!="" && txtSupplierAddress.getText().equals("")){
            vlist.addElement(txtSupplierName.getText().trim());
            vLoad = Suppliers.searchSupplierByName(vlist);
            this.loadTableSupplier("Supplier",vLoad);
        }else if(txtSupplierName.getText().equals("") && txtSupplierAddress.getText()!=""){
            vlist.addElement(txtSupplierAddress.getText().trim());
            vLoad = Suppliers.searchSupplierByAddress(vlist);
            this.loadTableSupplier("Supplier",vLoad);
        }else{
            vlist.addElement(txtSupplierName.getText().trim());
            vlist.addElement(txtSupplierAddress.getText().trim());
            vLoad = Suppliers.searchSupplierAdvanced(vlist);
            this.loadTableSupplier("Supplier",vLoad);
        }
    }
       //------------------------------------------------------------------------
       //------------------------------------------------------------------------
       public void resetMedicine(){
        txtMedicineName.setText("");
        cboMedicineType.setSelectedIndex(0);
        cboSupplier.setSelectedIndex(0);
        txtMedicineName.requestFocus();
    }
    
    public void resetSupplier(){
        txtSupplierName.setText("");
        txtSupplierAddress.setText("");
        cboCustomerType.setSelectedIndex(0);
        txtSupplierName.requestFocus();
        
    }
    
    public void resetCustomer(){
        txtCustomerName.setText("");
        cboCustomerType.setSelectedIndex(0);
        txtCustomerAddress.setText("");
        radUnfarmiliar.setSelected(true);
        txtCustomerName.requestFocus();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        GrouptMedicine = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtSupplierName = new javax.swing.JTextField();
        txtSupplierAddress = new javax.swing.JTextField();
        btnResetSupplier = new javax.swing.JButton();
        btnSearchSupplier = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSupplierDetails = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMedicineName = new javax.swing.JTextField();
        cboMedicineType = new javax.swing.JComboBox();
        radDomestic = new javax.swing.JRadioButton();
        radForeign = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        cboSupplier = new javax.swing.JComboBox();
        SearchMedicine = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMedicineDetails = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        cboCustomerType = new javax.swing.JComboBox();
        radFamiliar = new javax.swing.JRadioButton();
        radUnfarmiliar = new javax.swing.JRadioButton();
        txtCustomerAddress = new javax.swing.JTextField();
        btnResetCustomer = new javax.swing.JButton();
        btnSearchCustomer = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCustomerDetails = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Supplier information"));
        jLabel11.setText("Supplier Name");

        jLabel12.setText("Address");

        btnResetSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Refresh.ico-32x32.png")));
        btnResetSupplier.setText("Reset");
        btnResetSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetSupplierActionPerformed(evt);
            }
        });

        btnSearchSupplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/search2.png")));
        btnSearchSupplier.setText("Search");
        btnSearchSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchSupplierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSupplierName, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(txtSupplierAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnResetSupplier)
                    .addComponent(btnSearchSupplier))
                .addGap(25, 25, 25))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtSupplierAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchSupplier))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/gaimphone2456.png")));

        tableSupplierDetails.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableSupplierDetails);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 804, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(22, 22, 22)))
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(355, 355, 355))
        );
        jTabbedPane1.addTab("Suppliers", jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicines information"));
        jLabel1.setText("Medicine Name");

        jLabel2.setText("Medicine Type");

        jLabel3.setText("Origin");

        jLabel4.setText("Supplier");

        GrouptMedicine.add(radDomestic);
        radDomestic.setSelected(true);
        radDomestic.setText(" Domestic");
        radDomestic.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radDomestic.setMargin(new java.awt.Insets(0, 0, 0, 0));

        GrouptMedicine.add(radForeign);
        radForeign.setText(" Foreign");
        radForeign.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radForeign.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Refresh.ico-32x32.png")));
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        SearchMedicine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/search2.png")));
        SearchMedicine.setText("Search");
        SearchMedicine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchMedicineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboMedicineType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(radDomestic)
                        .addGap(23, 23, 23)
                        .addComponent(radForeign))
                    .addComponent(txtMedicineName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(SearchMedicine))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMedicineName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cboMedicineType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(radDomestic)
                                .addComponent(radForeign)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cboSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SearchMedicine)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine Details"));
        tableMedicineDetails.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableMedicineDetails);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/MedicineTypeList.png")));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)
                        .addComponent(jLabel10))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("Medicines", jPanel3);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel5.setText("Customer Name");

        jLabel6.setText("Customer Type");

        jLabel7.setText("Relationship");

        jLabel8.setText("Address");

        buttonGroup1.add(radFamiliar);
        radFamiliar.setText("Familiar");
        radFamiliar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radFamiliar.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(radUnfarmiliar);
        radUnfarmiliar.setSelected(true);
        radUnfarmiliar.setText("Unfamiliar");
        radUnfarmiliar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radUnfarmiliar.setMargin(new java.awt.Insets(0, 0, 0, 0));

        btnResetCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Refresh.ico-32x32.png")));
        btnResetCustomer.setText("Reset");
        btnResetCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetCustomerActionPerformed(evt);
            }
        });

        btnSearchCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/search2.png")));
        btnSearchCustomer.setText("Search");
        btnSearchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCustomerAddress)
                    .addComponent(txtCustomerName)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(radFamiliar)
                        .addGap(30, 30, 30)
                        .addComponent(radUnfarmiliar))
                    .addComponent(cboCustomerType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnResetCustomer)
                    .addComponent(btnSearchCustomer))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cboCustomerType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(radFamiliar)
                            .addComponent(radUnfarmiliar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtCustomerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearchCustomer))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer Details"));
        tableCustomerDetails.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableCustomerDetails);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Customer_Icon.jpg")));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(317, 317, 317))
        );
        jTabbedPane1.addTab("Customers", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetSupplierActionPerformed
// TODO add your handling code here:
        this.resetSupplier();
    }//GEN-LAST:event_btnResetSupplierActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        this.resetMedicine();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnResetCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetCustomerActionPerformed
// TODO add your handling code here:
        this.resetCustomer();
    }//GEN-LAST:event_btnResetCustomerActionPerformed

    private void btnSearchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCustomerActionPerformed
        try {
// TODO add your handling code here:
            this.searchCustomer();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSearchCustomerActionPerformed

    private void SearchMedicineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchMedicineActionPerformed
        try {
// TODO add your handling code here:
            this.searchMedicine();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_SearchMedicineActionPerformed

    private void btnSearchSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchSupplierActionPerformed
        try {
// TODO add your handling code here:
            this.searchSupplier();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSearchSupplierActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrouptMedicine;
    private javax.swing.JButton SearchMedicine;
    private javax.swing.JButton btnResetCustomer;
    private javax.swing.JButton btnResetSupplier;
    private javax.swing.JButton btnSearchCustomer;
    private javax.swing.JButton btnSearchSupplier;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboCustomerType;
    private javax.swing.JComboBox cboMedicineType;
    private javax.swing.JComboBox cboSupplier;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton radDomestic;
    private javax.swing.JRadioButton radFamiliar;
    private javax.swing.JRadioButton radForeign;
    private javax.swing.JRadioButton radUnfarmiliar;
    private javax.swing.JTable tableCustomerDetails;
    private javax.swing.JTable tableMedicineDetails;
    private javax.swing.JTable tableSupplierDetails;
    private javax.swing.JTextField txtCustomerAddress;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtMedicineName;
    private javax.swing.JTextField txtSupplierAddress;
    private javax.swing.JTextField txtSupplierName;
    // End of variables declaration//GEN-END:variables
    
}
