/*
 * Report.java
 *
 * Created on April 10, 2012, 8:38 PM
 */

package BillManage;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  
 */
public class Report extends javax.swing.JPanel {
    private DefaultTableModel ModelTableBillWaitting = new DefaultTableModel();
    Vector vloadWaitting = new Vector();
    
     private DefaultTableModel ModelTableBillOverdue = new DefaultTableModel();
    Vector vloadOverdue = new Vector();
    
     private DefaultTableModel ModelTableBillComplete = new DefaultTableModel();
    Vector vloadComplete = new Vector();
    /** Creates new form Report */
    public Report() {
        initComponents();
        try {
            vloadWaitting = Bills.getBillsWaittingForReport();
            this.loadBillWaitting("vBillWaitting",vloadWaitting);
            
            vloadOverdue = Bills.getBillsOverdueForReport();
            this.loadBillOverdue("vBillOverdue",vloadOverdue);
            
             vloadComplete = Bills.getBillsCompleteForReport();
            this.loadBillComplete("vBillComplete",vloadComplete);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
     public void loadBillWaitting(String namTable,Vector vload){
        ModelTableBillWaitting.setNumRows(0);
        Vector columnBillWaitting = new Vector();
        columnBillWaitting.addElement("BillTYpe");
        columnBillWaitting.addElement("customerName");
        columnBillWaitting.addElement("Relationship");
        columnBillWaitting.addElement("Price");
        columnBillWaitting.addElement("status");
        columnBillWaitting.addElement("Creater");
        ModelTableBillWaitting.setColumnIdentifiers(columnBillWaitting);
        Bills objBills = new Bills();
        for(int i = 0; i<vload.size();i++){
            objBills = (Bills) vload.get(i);
            Vector rowBillWaitting = new Vector();
            rowBillWaitting.addElement(objBills.getbillType());
            rowBillWaitting.addElement(objBills.getcustomerName());
            rowBillWaitting.addElement(objBills.getrelationship());
            rowBillWaitting.addElement(objBills.getprice());
            rowBillWaitting.addElement(objBills.getstatus());
            rowBillWaitting.addElement(objBills.getuserName());
            ModelTableBillWaitting.addRow(rowBillWaitting);
        }
        tableBillsWaitting.setModel(ModelTableBillWaitting);
    }
    
    //load billoverdue
     public void loadBillOverdue(String namTable,Vector vload){
        ModelTableBillOverdue.setNumRows(0);
        Vector columnBillOverdue = new Vector();
        columnBillOverdue.addElement("BillTYpe");
        columnBillOverdue.addElement("customerName");
        columnBillOverdue.addElement("Relationship");
        columnBillOverdue.addElement("ExpiredDate");
        columnBillOverdue.addElement("Price");
        columnBillOverdue.addElement("status");
        columnBillOverdue.addElement("Creater");
        ModelTableBillOverdue.setColumnIdentifiers(columnBillOverdue);
        Bills objBills = new Bills();
        for(int i = 0; i<vload.size();i++){
            objBills = (Bills) vload.get(i);
            Vector rowBillOverdue = new Vector();
            rowBillOverdue.addElement(objBills.getbillType());
            rowBillOverdue.addElement(objBills.getcustomerName());
            rowBillOverdue.addElement(objBills.getrelationship());
            rowBillOverdue.addElement(objBills.getexpiredTime());
            rowBillOverdue.addElement(objBills.getprice());
            rowBillOverdue.addElement(objBills.getstatus());
            rowBillOverdue.addElement(objBills.getuserName());
            ModelTableBillOverdue.addRow(rowBillOverdue);
        }
        tableBillsOverDue.setModel(ModelTableBillOverdue);
    }
     
      public void loadBillComplete(String namTable,Vector vload){
        ModelTableBillComplete.setNumRows(0);
        Vector columnBillComplete = new Vector();
        columnBillComplete.addElement("BillCode");
        columnBillComplete.addElement("BillTYpe");
        columnBillComplete.addElement("customerName");
        columnBillComplete.addElement("Relationship");
        columnBillComplete.addElement("Price");
        columnBillComplete.addElement("status");
        columnBillComplete.addElement("Creater");
        ModelTableBillComplete.setColumnIdentifiers(columnBillComplete);
        Bills objBills = new Bills();
        for(int i = 0; i<vload.size();i++){
            objBills = (Bills) vload.get(i);
            Vector rowBillComplete = new Vector();
            rowBillComplete.addElement(objBills.getbillCode());
            rowBillComplete.addElement(objBills.getbillType());
            rowBillComplete.addElement(objBills.getcustomerName());
            rowBillComplete.addElement(objBills.getrelationship());
            rowBillComplete.addElement(objBills.getprice());
            rowBillComplete.addElement(objBills.getstatus());
            rowBillComplete.addElement(objBills.getuserName());
            ModelTableBillComplete.addRow(rowBillComplete);
        }
        tableBillsComplete.setModel(ModelTableBillComplete);
    }
    
      
     public void deleteBill(){
        Vector vLoadBill = new Vector();
        int show = JOptionPane.showConfirmDialog(this,"Are you sure delete Bill ?","Remove Bill",JOptionPane.YES_NO_OPTION);
        if(show == JOptionPane.YES_OPTION){
            int test = 0, count = 0;
            int [] rArr = tableBillsComplete.getSelectedRows();
            try{
                for(int i = 0; i < rArr.length; i++){
                    int billCode = Integer.parseInt(tableBillsComplete.getValueAt(rArr[i],0).toString());
                    test = Bills.deleteBill(billCode);
                    if(test == 1)
                        count++;
                }
                if(count >= 0){
                    JOptionPane.showMessageDialog(this,"Delete "+count+" Bill","Remove Bill",JOptionPane.INFORMATION_MESSAGE);
                    vLoadBill = Bills.getBillsCompleteForReport();
       //             vLoadBill=Bills.getAllBillsWaitting();
                    this.loadBillComplete("vBillNotDetails",vLoadBill);
                }
               
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBillsWaitting = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableBillsOverDue = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnRemove = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableBillsComplete = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        jTabbedPane1.addTab("Bill Waiting", new javax.swing.ImageIcon(getClass().getResource("/Icon/bill.png")), jPanel1); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tableBillsOverDue.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableBillsOverDue);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        jTabbedPane1.addTab("Bill Overdue", new javax.swing.ImageIcon(getClass().getResource("/Icon/bill.png")), jPanel2); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/Remove.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tableBillsComplete.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableBillsComplete);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel3)
                .addContainerGap(773, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(689, Short.MAX_VALUE)
                .addComponent(btnRemove)
                .addGap(44, 44, 44))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnRemove)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bill Complete", new javax.swing.ImageIcon(getClass().getResource("/Icon/bill.png")), jPanel3); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 837, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
this.deleteBill();
        // TOD
    }//GEN-LAST:event_btnRemoveActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRemove;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tableBillsComplete;
    private javax.swing.JTable tableBillsOverDue;
    private javax.swing.JTable tableBillsWaitting;
    // End of variables declaration//GEN-END:variables
    
}
