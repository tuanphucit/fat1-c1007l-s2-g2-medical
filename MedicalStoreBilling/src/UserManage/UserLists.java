/*
 * UserLists.java
 *
 * Created on April 10, 2012, 4:03 PM
 */

package UserManage;
import Check.Check;
import GUI.Main;
import ConnectDatabase.DBHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author  
 */
public class UserLists extends javax.swing.JPanel {
     private DefaultTableModel modelUser = new DefaultTableModel();
    
    String nameTable = "Users";
    
    Vector vLoad = new Vector();
    /** Creates new form UserLists */
    public UserLists() {
        initComponents();
         try {
            txtUserCode.setEditable(false);
            vLoad = Users.getAllUser();
            this.loadTableUser(nameTable,vLoad);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     public void loadTableUser(String nameTable,Vector vLoad) throws SQLException{    
        modelUser.setNumRows(0);
        Vector columnUser = new Vector();
        columnUser.addElement("UserCode");
        columnUser.addElement("UserName");
        columnUser.addElement("Password");
        columnUser.addElement("FullName");
        columnUser.addElement("UserTypeCode");
        columnUser.addElement("Address");
        columnUser.addElement("Phone");
        columnUser.addElement("Email");
        columnUser.addElement("Active");
        modelUser.setColumnIdentifiers(columnUser);
        Users objUser = new Users();
        for(int i=0; i< vLoad.size();i++){
            objUser = (Users) vLoad.get(i);
            Vector rowUser = new Vector();
            rowUser.addElement(objUser.getuserCode());
            rowUser.addElement(objUser.getnameLogin());
            rowUser.addElement(objUser.getPassword());
            rowUser.addElement(objUser.getfullName());
            rowUser.addElement(objUser.getuserTypeCode());
            rowUser.addElement(objUser.getuserAddress());
            rowUser.addElement(objUser.getuserPhone());
            rowUser.addElement(objUser.getuserEmail());
         if(objUser.getuserActive()==1){

            rowUser.addElement("True");
            }
 else
         {
                rowUser.addElement("Flase");
            }
            modelUser.addRow(rowUser);
        }
        tableLists.setModel(modelUser);
    }
    public void checkUserName(){       
        String name = txtUserName.getText().trim();       
        boolean test = false;
        try {
            ResultSet rs = DBHelper.executeQuery("spGetNameLogin");
            while(rs.next()){
                if(name.equals(rs.getString("nameLogin"))){
                    txtUserName.setText("");
                    txtUserName.requestFocus();
                    test = true;                      
                }
            }
            if(test == true){
                JOptionPane.showMessageDialog(this,"User existed !","Add new user",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public boolean checkUser(){
        int test = 0;
        Check c = new Check();        
        // Kiem tra nameLogin tu cho nay
        if(!c.checkSpace(txtUserName.getText()) || !c.check(txtUserName.getText())){
            JOptionPane.showMessageDialog(this,"UserName Invalid","Check User",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(!c.check(txtPassWord.getText())){
            JOptionPane.showMessageDialog(this,"Password Invalid","Check User",JOptionPane.ERROR_MESSAGE);
            txtPassWord.setText("");
            txtPassWord.requestFocus();
            return false;
        }
        else  if(!c.check(txtFullName.getText())){
            JOptionPane.showMessageDialog(this,"FullName Invalid","Check User",JOptionPane.ERROR_MESSAGE);
            txtFullName.setText("");
            txtFullName.requestFocus();
            return false;
        }
        else  if(!c.check(txtAddress.getText())){
            JOptionPane.showMessageDialog(this,"Address Invalid","Check User",JOptionPane.ERROR_MESSAGE);
            txtAddress.setText("");
            txtAddress.requestFocus();
            return false;
        }
        else  if(!c.checkPhone(txtPhone.getText())){
            JOptionPane.showMessageDialog(this,"Phone Invalid","Check User",JOptionPane.ERROR_MESSAGE);
            txtPhone.setText("");
            txtPhone.requestFocus();
            return false;
        }
        else  if(!c.checkEmail(txtEmail.getText())){
            JOptionPane.showMessageDialog(this,"Email Invalid","Check User",JOptionPane.ERROR_MESSAGE);
            txtEmail.setText("");
            txtEmail.requestFocus();
            return false;
        }
        return true;
    }
    public void AddUser(){
        checkUserName();
        int test = 0;
        if(!this.checkUser()){
            return;
        }
        Users objUser = new Users();
        //int userCode = Integer.parseInt(txtUserCode.getText().trim());
        //objUser.setuserCode(userCode);
        objUser.setnameLogin(txtUserName.getText().trim());
        objUser.setPassword(txtPassWord.getText().trim());
        objUser.setfullName(txtFullName.getText().trim());
        if(cblUserType.getSelectedItem().toString().equals("Manage")){
            objUser.setuserTypeCode("M");
        }
        else if(cblUserType.getSelectedItem().toString().equals("Accountant")){
            objUser.setuserTypeCode("A");
        }else
            objUser.setuserTypeCode("S");
        objUser.setuserAddress(txtAddress.getText().trim());
        objUser.setuserPhone(txtPhone.getText().trim());
        objUser.setuserEmail(txtEmail.getText().trim());
        if(radioActive.isSelected()== true){
            objUser.setuserActive(1);
        }else
            objUser.setuserActive(0);
        
        try {
            test = Users.insertUser(objUser);
            if(test ==1){
                JOptionPane.showMessageDialog(this,"Add new user successfully !","Add new user",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Users.getAllUser();
                this.loadTableUser(nameTable,vLoad);
            }else
                JOptionPane.showMessageDialog(this,"Add new user error","Add new user",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void updateUser(){
        int test = 0;
        if(!this.checkUser()){
            return;
        }
        Users objUser = new Users();
        int userCode = Integer.parseInt(txtUserCode.getText().trim());
        objUser.setuserCode(userCode);
        objUser.setnameLogin(txtUserName.getText().trim());
        objUser.setPassword(txtPassWord.getText().trim());
        objUser.setfullName(txtFullName.getText().trim());
        if(cblUserType.getSelectedItem().toString().equals("Manage")){
            objUser.setuserTypeCode("M");
        }
        else if(cblUserType.getSelectedItem().toString().equals("Accountant")){
            objUser.setuserTypeCode("A");
        }else
            objUser.setuserTypeCode("S");
        objUser.setuserAddress(txtAddress.getText().trim());
        objUser.setuserPhone(txtPhone.getText().trim());
        objUser.setuserEmail(txtEmail.getText().trim());
        if(radioActive.isSelected()== true){
            objUser.setuserActive(1);
        }else
            objUser.setuserActive(0);
        
        try {            
            test = Users.updateUser(objUser);
            if(test ==1){
                JOptionPane.showMessageDialog(this,"Update user successfully !","Update user",JOptionPane.INFORMATION_MESSAGE);
                vLoad = Users.getAllUser();
                this.loadTableUser(nameTable,vLoad);
            }else
                JOptionPane.showMessageDialog(this,"Update user error","Update user",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteUser(){
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete User ?","Remove user",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableLists.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    test = Users.deleteUser(tableLists.getValueAt(rArr[i],1).toString());
                    if(test == 1)
                        count++;
                }
                if(count > 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" user","Remove User",JOptionPane.INFORMATION_MESSAGE);
                    vLoad = Users.getAllUser();
                    this.loadTableUser(nameTable,vLoad);
                }else
                    JOptionPane.showMessageDialog(this,"Delete user error !","Remove user",JOptionPane.ERROR_MESSAGE);
            }catch(SQLException ex){
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void resetForm(){
        txtUserCode.setText("");
        txtUserName.setText("");
        txtPassWord.setText("");
        txtFullName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        cblUserType.setSelectedIndex(0);
        txtUserName.requestFocus();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        tableUserList = new javax.swing.JScrollPane();
        tableLists = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtUserCode = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        txtPassWord = new javax.swing.JPasswordField();
        txtFullName = new javax.swing.JTextField();
        cblUserType = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        radioActive = new javax.swing.JRadioButton();
        radioNotActive = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        BtnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("User List"));
        tableLists.setModel(new javax.swing.table.DefaultTableModel(
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
        tableLists.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListsMouseClicked(evt);
            }
        });

        tableUserList.setViewportView(tableLists);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableUserList, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tableUserList, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("User Infomation Details"));
        jLabel1.setText("User Code");

        jLabel2.setText("User Name");

        jLabel3.setText("Pass Word");

        jLabel4.setText("Full Name");

        jLabel5.setText("User Type");

        jLabel6.setText("User Active");

        cblUserType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-------Select User Type------", "Manage", "Accountant", "Seller" }));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        buttonGroup1.add(radioActive);
        radioActive.setSelected(true);
        radioActive.setText("Active");
        radioActive.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioActive.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup1.add(radioNotActive);
        radioNotActive.setText("NotActive");
        radioNotActive.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        radioNotActive.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(radioActive)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(radioNotActive)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(radioActive)
                .addComponent(radioNotActive))
        );

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane2.setViewportView(txtAddress);

        jLabel7.setText("Address");

        jLabel8.setText("Phone");

        jLabel9.setText("Email");

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
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUserCode, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassWord)
                    .addComponent(txtFullName)
                    .addComponent(cblUserType, 0, 183, Short.MAX_VALUE)
                    .addComponent(txtUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(13, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtUserCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cblUserType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel6))
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/system_users.png")));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/reset.png")));
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        BtnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/delete.png")));
        BtnDelete.setText("Delete");
        BtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDeleteActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnAdd)
                .addGap(19, 19, 19)
                .addComponent(btnUpdate)
                .addGap(17, 17, 17)
                .addComponent(BtnDelete)
                .addGap(19, 19, 19)
                .addComponent(btnReset)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnDelete)
                    .addComponent(btnReset)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(144, 144, 144)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
// TODO add your handling code here:
        this.resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void BtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteActionPerformed
// TODO add your handling code here:
        this.deleteUser();
    }//GEN-LAST:event_BtnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// TODO add your handling code here:
        this.updateUser();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
        this.AddUser();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tableListsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListsMouseClicked
// TODO add your handling code here:
        int r = tableLists.getSelectedRow();
        if(r<0){
            return;
        }
        txtUserCode.setText(""+tableLists.getValueAt(r,0));
        txtUserName.setText(""+tableLists.getValueAt(r,1));
        txtPassWord.setText(""+tableLists.getValueAt(r,2));
        txtFullName.setText(""+tableLists.getValueAt(r,3));
        if(tableLists.getValueAt(r,4).equals("M")){
            cblUserType.setSelectedItem("Manage");
        } else if(tableLists.getValueAt(r,4).equals("A")){
            cblUserType.setSelectedItem("Accountant");
        } else
            cblUserType.setSelectedItem("Seller");
        txtAddress.setText(""+tableLists.getValueAt(r,5));
        txtPhone.setText(""+tableLists.getValueAt(r,6));
        txtEmail.setText(""+tableLists.getValueAt(r,7));
        
        if(tableLists.getValueAt(r,8).equals(1)){
            radioActive.setSelected(true);
        }else{
            radioNotActive.setSelected(true);
        }
    }//GEN-LAST:event_tableListsMouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnDelete;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cblUserType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton radioActive;
    private javax.swing.JRadioButton radioNotActive;
    private javax.swing.JTable tableLists;
    private javax.swing.JScrollPane tableUserList;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JPasswordField txtPassWord;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUserCode;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
    
}
