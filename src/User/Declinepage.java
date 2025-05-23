/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import Admin.*;
import CrudsAdmin.crud_users;
import config.Session;
import java.awt.Color;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import config.dbConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author SCC-COLLEGE
 */
public class Declinepage extends javax.swing.JInternalFrame {

    /**
     * Creates new form Userpage
     */
    public Declinepage() {
        initComponents();
        
        displayData();

        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);

        searchBar.setOpaque(false);
        searchBar.setBackground(new Color(0, 0, 0, 0));
    }

    public void displayData() {
        dbConnector dbc = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            dbc = new dbConnector();

            // Query joins tbl_task, tbl_project, and tbl_users to get user first name
            String sql = "SELECT t.t_id, p.p_name, u.u_fname AS assigned_user, p.p_date, p.p_duedate, t.t_status, t.accept " +
             "FROM tbl_task t " +
             "JOIN tbl_project p ON t.p_id = p.p_id " +
             "LEFT JOIN tbl_users u ON t.user_assign = u.u_id " +
             "WHERE t.accept IS NULL OR t.accept = 'Declined'";

            pst = dbc.connect.prepareStatement(sql);
            rs = pst.executeQuery();

            userTable.setModel(DbUtils.resultSetToTableModel(rs));

            // Disable auto resize for horizontal scroll
            userTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            // Set preferred widths for better scrolling experience
            int[] columnWidths = {70, 150, 120, 100, 100, 100, 90};
            for (int i = 0; i < columnWidths.length; i++) {
                if (i < userTable.getColumnModel().getColumnCount()) {
                    userTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                }
            }

            // Set column headers explicitly
            userTable.getColumnModel().getColumn(0).setHeaderValue("Task ID");
            userTable.getColumnModel().getColumn(1).setHeaderValue("Project Name");
            userTable.getColumnModel().getColumn(2).setHeaderValue("Assigned User");
            userTable.getColumnModel().getColumn(3).setHeaderValue("Project Start Date");
            userTable.getColumnModel().getColumn(4).setHeaderValue("Project Due Date");
            userTable.getColumnModel().getColumn(5).setHeaderValue("Task Status");
            userTable.getColumnModel().getColumn(6).setHeaderValue("Acceptance");

            userTable.getTableHeader().repaint();

        } catch (SQLException ex) {
            System.out.println("Errors: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (dbc != null && dbc.connect != null && !dbc.connect.isClosed()) dbc.connect.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        accept = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        searchBar.setMinimumSize(new java.awt.Dimension(8, 20));
        searchBar.setPreferredSize(new java.awt.Dimension(8, 20));
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });
        jPanel1.add(searchBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 200, 23));

        searchButton.setText("SEARCH");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        jPanel1.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, -1, 20));

        accept.setText("Accept");
        accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptActionPerformed(evt);
            }
        });
        jPanel1.add(accept, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, -1));

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(userTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 520, 250));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel2.setText("Decline Task Page");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Untitled Project.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBarActionPerformed

    private void acceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptActionPerformed
    int selectedRow = userTable.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select a task to accept.");
        return;
    }

    int taskId = (int) userTable.getValueAt(selectedRow, 0);

    // Get current logged-in user's ID (assuming session provides this)
    Session sess = Session.getInstance();
    int currentUserId = sess.getU_id(); // Assuming your Session class has user ID getter

    dbConnector dbc = new dbConnector();

    // Step 1: Check if the task is already accepted
    String checkQuery = "SELECT accept FROM tbl_task WHERE t_id = ?";
    try (PreparedStatement checkPst = dbc.connect.prepareStatement(checkQuery)) {
        checkPst.setInt(1, taskId);
        ResultSet rs = checkPst.executeQuery();

        if (rs.next()) {
            String acceptStatus = rs.getString("accept");
            if ("Accepted".equalsIgnoreCase(acceptStatus)) {
                JOptionPane.showMessageDialog(null, "This task has already been accepted.");
                return;
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Step 2: Accept the task and assign the user by ID
    String updateQuery = "UPDATE tbl_task SET accept = 'Accepted', user_assign = ? WHERE t_id = ?";
    try (PreparedStatement pst = dbc.connect.prepareStatement(updateQuery)) {
        pst.setInt(1, currentUserId);
        pst.setInt(2, taskId);
        int rowsAffected = pst.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Task accepted successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Error accepting task.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        displayData();
        try {
            if (dbc.connect != null && !dbc.connect.isClosed()) {
                dbc.connect.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_acceptActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String keyword = searchBar.getText().trim();

    dbConnector dbc = new dbConnector();

    // Use proper JOINs for search
    String query = "SELECT t.t_id, p.p_name, u.u_fname AS assigned_user, p.p_date, p.p_duedate, t.t_status, t.accept " +
                   "FROM tbl_task t " +
                   "JOIN tbl_project p ON t.p_id = p.p_id " +
                   "JOIN tbl_users u ON t.user_assign = u.u_id " +
                   "WHERE u.u_fname LIKE ? OR p.p_name LIKE ? OR CAST(t.t_id AS CHAR) LIKE ?";

    try (PreparedStatement pst = dbc.connect.prepareStatement(query)) {
        String likeKeyword = "%" + keyword + "%";
        pst.setString(1, likeKeyword);
        pst.setString(2, likeKeyword);
        pst.setString(3, likeKeyword);

        ResultSet rs = pst.executeQuery();

        if (!rs.isBeforeFirst()) {
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"Message"});
            model.addRow(new Object[]{"No results found for \"" + keyword + "\""});
            userTable.setModel(model);
        } else {
            userTable.setModel(DbUtils.resultSetToTableModel(rs));
            userTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            int[] columnWidths = {70, 150, 120, 100, 100, 100, 90};
            for (int i = 0; i < columnWidths.length; i++) {
                if (i < userTable.getColumnModel().getColumnCount()) {
                    userTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
                }
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (dbc.connect != null && !dbc.connect.isClosed()) {
                dbc.connect.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_searchButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    public javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
