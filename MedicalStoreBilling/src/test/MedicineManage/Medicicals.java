/*
 * Medicicals.java
 *
 * Created on September 11, 2009, 8:29 PM
 */

package MedicineManage;
import ConnectDatabase.DBHelper;
import Check.Check;
import MeasureManage.Measures;
import MedicineTypeManage.MedicineType;
import SupplierManage.Suppliers;
import java.sql.ResultSet;
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
 * @author  TranVanHien
 */
public class Medicicals extends javax.swing.JPanel {
    private DefaultComboBoxModel cbmdMedicineType = new DefaultComboBoxModel();
    private DefaultComboBoxModel cbmdSupplier = new DefaultComboBoxModel();
    private DefaultComboBoxModel cbmdMeasure = new DefaultComboBoxModel();
    private DefaultTableModel modelMedicine = new DefaultTableModel();
    String nameTable = "Medicine";
    Vector vLoad = new Vector();
    Measures objMeasure = new Measures();
    Suppliers objSupplier = new Suppliers();
    MedicineType objMedicineType = new MedicineType();
    /** Creates new form Medicicals */
    public Medicicals() {
        initComponents();
        txtCodeMedicineList.setEnabled(false);
        radioDomestic.setSelected(true);
        try {
            vLoad = Medicines.getAllMedicine();
            this.loadTableMedicine(nameTable,vLoad);            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     public boolean checkMedicine(){
        Check c = new Check();
        if(!c.checkSpace(txtNameMedicineList.getText()) || !c.check(txtNameMedicineList.getText())){
            JOptionPane.showMessageDialog(this,"Medicine Name Invalid !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            txtNameMedicineList.setText("");
            txtNameMedicineList.requestFocus();
            return false;
        }else if(cblTypeMedicine.getSelectedItem().equals("-- Choice Medicine Type --")){
            JOptionPane.showMessageDialog(this,"Medicine Type is not select !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            return false;
        }else if(cblSuplier.getSelectedItem().equals("---- Choice Supplier ----")){
            JOptionPane.showMessageDialog(this,"Supplier is not select !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public void checkMedicineName(){       
        String name = txtNameMedicineList.getText().trim();       
        boolean test = false;
        try {
            ResultSet rs = DBHelper.executeQuery("spGetMedicineName");
            while(rs.next()){
                if(name.equals(rs.getString("medicineName"))){
                    txtNameMedicineList.setText("");
                    txtNameMedicineList.requestFocus();
                    test = true;                      
                }
            }
            if(test == true){
                JOptionPane.showMessageDialog(this,"Medicine Name existed !","Add new Medicine",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean checkMedicineDetails(){
        Check c = new Check();
        if(cblMensure.getSelectedItem().equals("---- Choice Measure -----")){
            JOptionPane.showMessageDialog(this,"Measure is not select !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(!c.checkSpace(txtPriceUnit.getText())){
            JOptionPane.showMessageDialog(this,"PricePreUnit Invalid !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            txtPriceUnit.setText("");
            txtPriceUnit.requestFocus();
            return false;
        }        
        else if(!c.checkDateIn(txtTermsofUser.getText())){
            JOptionPane.showMessageDialog(this,"TermsOfUse Invalid !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            txtTermsofUser.setText("");
            txtTermsofUser.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtUsed.getText()) || !c.check(txtUsed.getText())){
            JOptionPane.showMessageDialog(this,"Use Invalid !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            txtUsed.setText("");
            txtUsed.requestFocus();
            return false;
        }        
        else if(!c.checkSpace(txtAvarible.getText())|| !c.checkNumber(txtAvarible.getText())){
            JOptionPane.showMessageDialog(this,"Avaiable Amount Invalid !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            txtAvarible.setText("");
            txtAvarible.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtRegister.getText()) || !c.check(txtRegister.getText())){
            JOptionPane.showMessageDialog(this,"Register Number Invalid !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            txtRegister.setText("");
            txtRegister.requestFocus();
            return false;
        }
        else if(!c.checkSpace(txtUserGuide.getText()) || !c.check(txtUserGuide.getText())){
            JOptionPane.showMessageDialog(this,"User Guide Invalid !","Update Medicine",JOptionPane.ERROR_MESSAGE);
            txtUserGuide.setText("");
            txtUserGuide.requestFocus();
            return false;
        }
        return true;
    }
    
    public void addMedicine() throws SQLException{
        int test = 0;
        if(!checkMedicine()){
            return;
        }
        Medicines objMedicine = new Medicines();
        objMedicine.setMedicineName(txtNameMedicineList.getText());
        
        Vector vMedicineType=new Vector();
        vMedicineType=MedicineType.getAllMedicineType();
        for(int j=0;j<vMedicineType.size();j++) {
           objMedicineType=(MedicineType)vMedicineType.get(j);
           if(cbmdMedicineType.getSelectedItem().equals(objMedicineType.getMedicineTypeName())){
                objMedicine.setMedicineTypeCode(objMedicineType.getMedicineTypeCode());                    
             }           
        } 
        
        Vector vSupplier=new Vector();
        vSupplier=Suppliers.getAllSupplier();
        for(int j=0;j<vSupplier.size();j++) {
            objSupplier=(Suppliers)vSupplier.get(j);
            if(cblSuplier.getSelectedItem().equals(objSupplier.getsupplierName())){
                objMedicine.setSupplierCode(objSupplier.getsupplierCode());                    
            }           
        }   
                
        test = Medicines.insertMedicine(objMedicine);
        if (test == 1) {
            JOptionPane.showMessageDialog(this, "Add New Medicine sucsessful !", "Add New Medicine", JOptionPane.INFORMATION_MESSAGE);
            vLoad = Medicines.getAllMedicine();
            this.loadTableMedicine(nameTable, vLoad);
        } 
        else{
            JOptionPane.showMessageDialog(this,"Add New Medicine Error!", "Add New Medicine", JOptionPane.ERROR_MESSAGE);
            }          
    }
    
   public void updateMedicine() throws SQLException{
        int test = 0;
        if(!checkMedicine()){
            return;
        }
        Medicines objMedicine = new Medicines();
        int medicineCode = Integer.parseInt(txtCodeMedicineList.getText().trim());
        objMedicine.setMedicineCode(medicineCode);
        objMedicine.setMedicineName(txtNameMedicineList.getText());
                           
        Vector vMedicineType=new Vector();
        vMedicineType=MedicineType.getAllMedicineType();
        for(int j=0;j<vMedicineType.size();j++) {
           objMedicineType=(MedicineType)vMedicineType.get(j);
           if(cblTypeMedicine.getSelectedItem().equals(objMedicineType.getMedicineTypeName())){
                objMedicine.setMedicineTypeCode(objMedicineType.getMedicineTypeCode());                    
             }           
        } 
          
        Vector vSupplier=new Vector();
        vSupplier=Suppliers.getAllSupplier();
        for(int j=0;j<vSupplier.size();j++) {
            objSupplier=(Suppliers)vSupplier.get(j);
            if(cblSuplier.getSelectedItem().equals(objSupplier.getsupplierName())){
                objMedicine.setSupplierCode(objSupplier.getsupplierCode());                    
            }           
        }            
        
        test = Medicines.updateMedicine(objMedicine);
        if (test == 1) {
            JOptionPane.showMessageDialog(this, "Update Medicine sucsessful !", "Update Medicine", JOptionPane.INFORMATION_MESSAGE);
            vLoad = Medicines.getAllMedicine();
            this.loadTableMedicine(nameTable, vLoad);
        } 
        else{
            JOptionPane.showMessageDialog(this,"Update Medicine Error!", "Update Medicine", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    public void addMedicineMedicineDetails() throws SQLException{
        int test = 0;
        if(!checkMedicineDetails()){
            return;
        }       
        Medicines objMedicineDetails = new Medicines();
        int medicineCode = Integer.parseInt(txtCodeMedicineList.getText().trim());
                
        Vector vMeasure=new Vector();
        vMeasure=Measures.getAllMeasure();
        for(int j=0;j<vMeasure.size();j++) {
            objMeasure=(Measures)vMeasure.get(j);
            if(cblMensure.getSelectedItem().equals(objMeasure.getmeasureName())){
                objMedicineDetails.setMeasureCode(objMeasure.getmeasureCode());                    
            }           
         }
               
        objMedicineDetails.setMedicineCode(medicineCode); 
        objMedicineDetails.setPricePerUnit(Float.parseFloat(txtPriceUnit.getText().trim()));
        objMedicineDetails.setAvaiableAmount(Integer.parseInt(txtAvarible.getText().trim()));
        objMedicineDetails.setRegisterNumber(txtRegister.getText().trim());
        if(radioDomestic.isSelected() == true){
            objMedicineDetails.setOrigin("Domestic");
        }else
            objMedicineDetails.setOrigin("Foregin");
        objMedicineDetails.setUsed(txtUsed.getText().trim());        
        objMedicineDetails.setTermsOfUse(txtTermsofUser.getText());
        objMedicineDetails.setUseGuide(txtUserGuide.getText());
        test = Medicines.insertMedicineDetails(objMedicineDetails);
        
        if (test == 1) {
            JOptionPane.showMessageDialog(this, "Add New Medicine Details sucsessful !", "Add New Medicine Details", JOptionPane.INFORMATION_MESSAGE);               
        } 
        else{
            JOptionPane.showMessageDialog(this,"Add New Medicine Details Error!", "Add New Medicine Details", JOptionPane.ERROR_MESSAGE);
            }
           
    }
    
    public void updateMedicineDetails() throws SQLException{
        int test = 0;
        if(!checkMedicineDetails()){
            return;
        }        
        Medicines objMedicineDetails = new Medicines();     
        int medicineCode = Integer.parseInt(txtCodeMedicineList.getText().trim());
            
        Vector vMeasure=new Vector();
        vMeasure=Measures.getAllMeasure();
        for(int j=0;j<vMeasure.size();j++) {
            objMeasure=(Measures)vMeasure.get(j);
            if(cblMensure.getSelectedItem().equals(objMeasure.getmeasureName())){
                objMedicineDetails.setMeasureCode(objMeasure.getmeasureCode());                    
            }           
         } 
           
        objMedicineDetails.setMedicineCode(medicineCode);
        objMedicineDetails.setPricePerUnit(Float.parseFloat(txtPriceUnit.getText().trim()));
        objMedicineDetails.setAvaiableAmount(Integer.parseInt(txtAvarible.getText().trim()));
        objMedicineDetails.setRegisterNumber(txtRegister.getText().trim());
        if(radioDomestic.isSelected() == true){
            objMedicineDetails.setOrigin("Domestic");
        }else
            objMedicineDetails.setOrigin("Foregin");
        objMedicineDetails.setUsed(txtUsed.getText().trim());        
        objMedicineDetails.setTermsOfUse(txtTermsofUser.getText());
        objMedicineDetails.setUseGuide(txtUserGuide.getText());       
                
        test = Medicines.updateMedicineDetails(objMedicineDetails);
        
        if (test == 1) {
            JOptionPane.showMessageDialog(this, "Update Medicine sucsessful !", "Update Medicine", JOptionPane.INFORMATION_MESSAGE);
            vLoad = Medicines.getAllMedicine();
            this.loadTableMedicine(nameTable, vLoad);
        } 
        else{
            JOptionPane.showMessageDialog(this,"Update Medicine Error!", "Update Medicine", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    public void DoActionMedicineDetails() throws SQLException{
        int code = Integer.parseInt(txtCodeMedicineList.getText().trim());
        Vector v = new Vector();
        v.add(code);
        ResultSet rs = DBHelper.executeQuery("spGetMedicineDetailsByCode",v);    
        if(!rs.next()){
            
            this.addMedicineMedicineDetails();
            return;
        }
        else{
            this.updateMedicineDetails();
            return;
        }    
    }
    
    public void loadTableMedicine(String nameTable,Vector vLoad) throws SQLException{
        modelMedicine.setNumRows(0);
        Vector columnMedicine = new Vector();
        columnMedicine.addElement("Code");
        columnMedicine.addElement("Name");
        columnMedicine.addElement("Type");
        columnMedicine.addElement("Supplier");        
        modelMedicine.setColumnIdentifiers(columnMedicine);
        Medicines objMedicine = new Medicines();
        for(int i=0; i< vLoad.size();i++){
            objMedicine = (Medicines) vLoad.get(i);
            Vector rowMedicine = new Vector();
            rowMedicine.addElement(objMedicine.getMedicineCode());  //0
            rowMedicine.addElement(objMedicine.getMedicineName());//1
            rowMedicine.addElement(objMedicine.getMedicineTypeCode());//2
            rowMedicine.addElement(objMedicine.getSupplierCode());//3            
            modelMedicine.addRow(rowMedicine);
        }
        tableMedicineList.setModel(modelMedicine);
        
        
        Vector vMedicine=new Vector();
        vMedicine=Medicines.getAllMedicine();
        cbmdMeasure.removeAllElements();
        cbmdMeasure.addElement("---- Choice Measure -----");          
            for(int j=0;j<vMedicine.size();j++) {
                objMedicine=(Medicines)vMedicine.get(j);
                txtUsed.setText(objMedicine.getUsed());
            }
        cblMensure.setModel(cbmdMeasure);
                
        
        Vector vMeasure=new Vector();
        vMeasure=Measures.getAllMeasure();
        cbmdMeasure.removeAllElements();
        cbmdMeasure.addElement("---- Choice Measure -----");          
            for(int j=0;j<vMeasure.size();j++) {
                objMeasure=(Measures)vMeasure.get(j);
                cbmdMeasure.addElement(objMeasure.getmeasureName());
            }
        cblMensure.setModel(cbmdMeasure);
        
        
       
        Vector vSupplier=new Vector();
        vSupplier=Suppliers.getAllSupplier();
        cbmdSupplier.removeAllElements();
        cbmdSupplier.addElement("---- Choice Supplier ----");

            for(int j=0;j<vSupplier.size();j++) {
                objSupplier=(Suppliers)vSupplier.get(j);
                cbmdSupplier.addElement(objSupplier.getsupplierName());
            }
        cblSuplier.setModel(cbmdSupplier);
         
        
        Vector vMedicineType=new Vector();
        vMedicineType=MedicineType.getAllMedicineType();
        cbmdMedicineType.removeAllElements();
        cbmdMedicineType.addElement("-- Choice Medicine Type --");          
            for(int j=0;j<vMedicineType.size();j++) {
                objMedicineType=(MedicineType)vMedicineType.get(j);
                cbmdMedicineType.addElement(objMedicineType.getMedicineTypeName());
            }           
        cblTypeMedicine.setModel(cbmdMedicineType);          
    }   
    
    public void resetForm(){
        txtCodeMedicineList.setText("");
        txtNameMedicineList.setText("");
        cblTypeMedicine.setSelectedIndex(0);
        cblSuplier.setSelectedIndex(0);
        txtTermsofUser.setText("");
        txtUsed.setText("");
        cblMensure.setSelectedIndex(0);
        txtPriceUnit.setText("");
        txtAvarible.setText("");
        txtRegister.setText("");        
        txtUserGuide.setText("");
        txtNameMedicineList.requestFocus();
    }
   public void resetFormMedicine(){
        
        txtTermsofUser.setText("");
        txtUsed.setText("");
        cblMensure.setSelectedIndex(0);
        txtPriceUnit.setText("");
        txtAvarible.setText("");
        txtRegister.setText("");        
        txtUserGuide.setText("");
        txtNameMedicineList.requestFocus();
    }
    
    public void deleteMedicine(){
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Medicine ?","Remove Medicine",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableMedicineList.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    test = Medicines.deleteMedicine(tableMedicineList.getValueAt(rArr[i],1).toString());
                    if(test == 1)
                        count++;
                }
                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Medicine","Remove Medicine",JOptionPane.INFORMATION_MESSAGE);
                    vLoad = Medicines.getAllMedicine();
                    this.loadTableMedicine(nameTable,vLoad);
                }else
                    JOptionPane.showMessageDialog(this,"Delete Medicine","Remove Medicine",JOptionPane.INFORMATION_MESSAGE);
                    vLoad = Medicines.getAllMedicine();
                    this.loadTableMedicine(nameTable,vLoad);
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
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMedicineList = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCodeMedicineList = new javax.swing.JTextField();
        txtNameMedicineList = new javax.swing.JTextField();
        cblTypeMedicine = new javax.swing.JComboBox();
        cblSuplier = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cblMensure = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtPriceUnit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTermsofUser = new javax.swing.JTextField();
        try {
            txtTermsofUser = new JFormattedTextField(new MaskFormatter("####-##-##"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtUsed = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtAvarible = new javax.swing.JTextField();
        txtRegister = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        radioDomestic = new javax.swing.JRadioButton();
        radioForeign = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtUserGuide = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        Update = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine List"));
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
        tableMedicineList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMedicineListMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(tableMedicineList);

        jLabel1.setText("Code");

        jLabel2.setText("Name");

        jLabel3.setText("Type");

        jLabel4.setText("Supplier");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cblSuplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNameMedicineList, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(txtCodeMedicineList, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cblTypeMedicine, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCodeMedicineList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNameMedicineList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cblTypeMedicine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cblSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine Detail"));
        jLabel5.setText("Measure");

        jLabel6.setText("PricePerUnit");

        jLabel7.setText("[yyyy-mm-dd]");

        jLabel8.setText("Terms OfUse ");

        jLabel9.setText("Used");

        txtUsed.setColumns(20);
        txtUsed.setRows(5);
        jScrollPane2.setViewportView(txtUsed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel10.setText("Avaiable Amount");

        jLabel11.setText("Register Number");

        jLabel12.setText("Origin");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        buttonGroup1.add(radioDomestic);
        radioDomestic.setText(" Domestic");
        radioDomestic.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioDomestic.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(radioForeign);
        radioForeign.setText("Foreign");
        radioForeign.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioForeign.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioDomestic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioForeign)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(radioDomestic)
                .addComponent(radioForeign))
        );

        jLabel13.setText("Use Guide");

        txtUserGuide.setColumns(20);
        txtUserGuide.setRows(5);
        jScrollPane3.setViewportView(txtUserGuide);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addComponent(cblMensure, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPriceUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                        .addComponent(txtTermsofUser)))
                .addGap(181, 181, 181)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtRegister)
                        .addComponent(txtAvarible, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cblMensure, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtAvarible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTermsofUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPriceUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtRegister, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/save.png")));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

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

        Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Update.png")));
        Update.setText("Update");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addGap(15, 15, 15)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Update))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addGap(81, 81, 81))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
        try {
            this.checkMedicineName();
            this.addMedicine();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
// TODO add your handling code here:
        try {
            this.updateMedicine();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_UpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
// TODO add your handling code here:
        this.deleteMedicine();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
// TODO add your handling code here:
        this.resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
// TODO add your handling code here:
        try {
// TODO add your handling code here:
            if(!this.checkMedicineDetails()){
                return;
            }
            this.DoActionMedicineDetails();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tableMedicineListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMedicineListMouseClicked
// TODO add your handling code here:
        int r = tableMedicineList.getSelectedRow();
        if(r<0){
            return;
        }
        txtCodeMedicineList.setText(""+tableMedicineList.getValueAt(r,0));
        txtNameMedicineList.setText(""+tableMedicineList.getValueAt(r,1));
        
        try {
            Vector vMedicineType=new Vector();
            vMedicineType=MedicineType.getAllMedicineType();
            for(int j=0;j<vMedicineType.size();j++) {
                objMedicineType=(MedicineType)vMedicineType.get(j);
                if(tableMedicineList.getValueAt(r,2).equals(objMedicineType.getMedicineTypeCode())){
                    cblTypeMedicine.setSelectedItem(objMedicineType.getMedicineTypeName());
                }
            }
        
            Vector vSupplier=new Vector();
            vSupplier=Suppliers.getAllSupplier();
            for(int j=0;j<vSupplier.size();j++) {
                objSupplier=(Suppliers)vSupplier.get(j);
                if(tableMedicineList.getValueAt(r,3).equals(objSupplier.getsupplierCode())){
                    cblSuplier.setSelectedItem(objSupplier.getsupplierName());
                }
            }
        
        int code = Integer.parseInt(txtCodeMedicineList.getText().trim());
        Vector v = new Vector();
        v.add(code);
        
        ResultSet rs = DBHelper.executeQuery("spGetMedicineDetailsByCode",v);    
        if(!rs.next()){
            this.resetFormMedicine();
        }
        else{        
            Float price= rs.getFloat("pricePerUnit");
            Integer amount = rs.getInt("avaiableAmount");
            txtAvarible.setText(amount.toString());
            txtTermsofUser.setText(rs.getString(8));
            txtPriceUnit.setText(price.toString());
            txtUsed.setText(rs.getString(7));
            txtRegister.setText(rs.getString(5));
            if(rs.getString(6).equals("Domestic")){
                radioDomestic.setSelected(true);
            }else
                radioForeign.setSelected(true);
                txtUserGuide.setText(rs.getString(9));
           
            Vector vMeasure=new Vector();
            vMeasure=Measures.getAllMeasure();
            for(int j=0;j<vMeasure.size();j++) {
                objMeasure=(Measures)vMeasure.get(j);
                if(rs.getInt(2) == (objMeasure.getmeasureCode()))   {
                    cblMensure.setSelectedItem(objMeasure.getmeasureName());
                    }
            }
        }    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }      
    }//GEN-LAST:event_tableMedicineListMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Update;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cblMensure;
    private javax.swing.JComboBox cblSuplier;
    private javax.swing.JComboBox cblTypeMedicine;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton radioDomestic;
    private javax.swing.JRadioButton radioForeign;
    private javax.swing.JTable tableMedicineList;
    private javax.swing.JTextField txtAvarible;
    private javax.swing.JTextField txtCodeMedicineList;
    private javax.swing.JTextField txtNameMedicineList;
    private javax.swing.JTextField txtPriceUnit;
    private javax.swing.JTextField txtRegister;
    private javax.swing.JTextField txtTermsofUser;
    private javax.swing.JTextArea txtUsed;
    private javax.swing.JTextArea txtUserGuide;
    // End of variables declaration//GEN-END:variables
    
}
