/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

import CrudsAdmin.*;
import Admin.AdminDashboard;
import Admin.Taskpage;
import config.dbConnector;
import config.Session;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import myApp.LoginForm;


/**
 *
 * @author PC15
 */
public class crud_task extends javax.swing.JFrame {   
    public crud_task() {
        initComponents();
        loadProjectNames();
        loadAssignUsers();
        setupProjectComboListener();

        // Enable combo boxes for Add mode
        pname.setEnabled(true);
        assignuser.setEnabled(true);

        update.setEnabled(false);
    }
    
    public void loadTaskForEdit(int taskIdToEdit) {
    dbConnector dbc = null;

    try {
        dbc = new dbConnector();

        String sql = "SELECT t.*, p.p_name, p.p_salary, p.p_date, p.p_duedate, u.u_fname, u.u_id " +
                     "FROM tbl_task t " +
                     "JOIN tbl_project p ON t.p_id = p.p_id " +
                     "JOIN tbl_users u ON t.u_id = u.u_id " +
                     "WHERE t.t_id = ?";
        PreparedStatement pst = dbc.connect.prepareStatement(sql);
        pst.setInt(1, taskIdToEdit);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            crud_task at = this;

            final String projectName = rs.getString("p_name");
            final int assignId = rs.getInt("user_assign"); // Assigned user ID
            final String projectSalary = rs.getString("p_salary");
            final String taskStatus = rs.getString("t_status");
            final java.util.Date startDate = rs.getDate("p_date");
            final java.util.Date dueDate = rs.getDate("p_duedate");
            final String makerName = rs.getString("u_fname");
            final int makerId = rs.getInt("u_id"); // ✅ This is the maker's ID

            // Load project names
            at.loadProjectNames();

            // Load assignable users
            at.loadAssignUsers();

            // Set assigned user by ID
            try (PreparedStatement pstUser = dbc.connect.prepareStatement(
                         "SELECT u_fname FROM tbl_users WHERE u_id = ?")) {
                pstUser.setInt(1, assignId);
                try (ResultSet rsUser = pstUser.executeQuery()) {
                    if (rsUser.next()) {
                        String assignedName = rsUser.getString("u_fname");
                        boolean foundUser = false;
                        for (int i = 0; i < at.assignuser.getItemCount(); i++) {
                            if (at.assignuser.getItemAt(i).equalsIgnoreCase(assignedName)) {
                                at.assignuser.setSelectedIndex(i);
                                foundUser = true;
                                break;
                            }
                        }
                        if (!foundUser) {
                            at.assignuser.addItem(assignedName);
                            at.assignuser.setSelectedItem(assignedName);
                        }
                    } else {
                        if (at.assignuser.getItemCount() > 0) {
                            at.assignuser.setSelectedIndex(0);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(at, "Error loading assigned user: " + ex.getMessage());
            }

            // ✅ Set Maker Name and Maker ID fields
            at.umaker.setText(makerName);
            at.user_id.setText(String.valueOf(makerId));

            // Other task info
            at.salary.setText(projectSalary);
            at.date.setDate(startDate);
            at.due.setDate(dueDate);
            at.status.setSelectedItem(taskStatus);
            at.t_id.setText(String.valueOf(taskIdToEdit));

            // Lock project selection
            at.pname.setSelectedItem(projectName);
            at.pname.setEnabled(false);

            // Enable update button
            at.update.setEnabled(true);

            // Show the form
            at.setVisible(true);

            // Close parent window if any
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parent != null) parent.dispose();

        } else {
            JOptionPane.showMessageDialog(null, "Error: Task not found.");
        }

        rs.close();
        pst.close();

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error loading task: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        if (dbc != null) {
            try {
                dbc.connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    public void loadProjectNames() {
    dbConnector dbc = null;
    try {
        dbc = new dbConnector();
        String sqlProjects = "SELECT p_name FROM tbl_project ORDER BY p_name";
        PreparedStatement pstProjects = dbc.connect.prepareStatement(sqlProjects);
        ResultSet rsProjects = pstProjects.executeQuery();

        pname.removeAllItems();
        pname.addItem("Select Project"); // Optional default

        while (rsProjects.next()) {
            pname.addItem(rsProjects.getString("p_name"));
        }

        rsProjects.close();
        pstProjects.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to load projects: " + ex.getMessage());
    } finally {
        if (dbc != null) {
            try {
                dbc.connect.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}


    public void loadAssignUsers() {
    assignuser.removeAllItems();
    assignuser.addItem("Select User"); // Optional default
    try {
        dbConnector dbc = new dbConnector();
        String sql = "SELECT u_fname FROM tbl_users ORDER BY u_fname ASC";
        PreparedStatement pst = dbc.connect.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            assignuser.addItem(rs.getString("u_fname"));
        }
        rs.close();
        pst.close();
        dbc.connect.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage());
    }
}

    // Call this method on project combo selection change to update salary field
    private void updateSalaryField() {
        String selectedProject = (String) pname.getSelectedItem();

        if (selectedProject == null || selectedProject.equals("Select Project")) {
            salary.setText("");
            return;
        }

        dbConnector db = new dbConnector();
        String query = "SELECT p_salary FROM tbl_project WHERE p_name = ? LIMIT 1";

        try (PreparedStatement pst = db.getConnection().prepareStatement(query)) {
            pst.setString(1, selectedProject);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                salary.setText(rs.getString("p_salary"));
            } else {
                salary.setText("Not Found");
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            salary.setText("Error");
        } finally {
            try {
                if (db.getConnection() != null && !db.getConnection().isClosed()) {
                    db.getConnection().close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Add a listener on your pname JComboBox to call updateSalaryField when selection changes
    private void setupProjectComboListener() {
        pname.addActionListener(e -> updateSalaryField());
    }

// This method will be called when the project combo box selection changes



       

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        status = new javax.swing.JComboBox<>();
        cancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        assign_name = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        date = new com.toedter.calendar.JDateChooser();
        due = new com.toedter.calendar.JDateChooser();
        update = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        pname = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        t_id = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        user_id = new javax.swing.JTextField();
        umaker = new javax.swing.JTextField();
        assignuser = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        salary = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jLabel9.setText("jLabel9");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(51, 255, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "Active", "Not Active", "Completed" }));
        jPanel2.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 291, 220, 30));

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel2.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 361, 81, 28));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Start Date:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 222, 120, 26));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Due Date:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 256, 120, 26));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Maker ID");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 57, 120, 26));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Status:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 292, 120, 26));

        assign_name.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        assign_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        assign_name.setText("Assign Name:");
        jPanel2.add(assign_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 120, 26));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Project Name:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, 120, 26));

        date.setEnabled(false);
        jPanel2.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 222, 220, 28));

        due.setEnabled(false);
        jPanel2.add(due, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 256, 220, 29));

        update.setText("Update");
        update.setEnabled(false);
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel2.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 327, 81, 28));

        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });
        jPanel2.add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 81, 28));

        pname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Project", "Item 2", "Item 3", "Item 4" }));
        pname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pnameActionPerformed(evt);
            }
        });
        jPanel2.add(pname, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 92, 220, 26));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("task_id");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 21, 110, 26));

        t_id.setEnabled(false);
        jPanel2.add(t_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 220, 30));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Maker Name:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 157, 120, 26));

        user_id.setEnabled(false);
        jPanel2.add(user_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 56, 220, 30));

        umaker.setEnabled(false);
        jPanel2.add(umaker, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 220, 25));

        assignuser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select User", "Item 2", "Item 3", "Item 4" }));
        assignuser.setEnabled(false);
        assignuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignuserActionPerformed(evt);
            }
        });
        jPanel2.add(assignuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 220, 26));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Salary:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 120, 26));

        salary.setEnabled(false);
        salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salaryActionPerformed(evt);
            }
        });
        jPanel2.add(salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 220, 25));

        jPanel1.add(jPanel2);
        jPanel2.setBounds(290, 20, 370, 400);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ADD TASK FORM");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 30, 220, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Untitled Project.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 680, 450);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
    Session sess = Session.getInstance();

    if (sess.getU_id() == 0) {
        JOptionPane.showMessageDialog(null, "No Account. Login First!");
        LoginForm lf = new LoginForm();
        lf.setVisible(true);
        this.dispose();
        return;
    }

    dbConnector db = new dbConnector();

    try {
        int projectId = sess.getP_id();

        if (projectId > 0) {
            String query = "SELECT p_name, p_salary FROM tbl_project WHERE p_id = ?";
            PreparedStatement pst = db.connect.prepareStatement(query);
            pst.setInt(1, projectId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String projectName = rs.getString("p_name");
                String projectSalary = rs.getString("p_salary");

                umaker.setEnabled(false);   // prevent changing the maker name
                user_id.setEnabled(false);  // prevent changing the u_id
                assignuser.setEnabled(false); // prevent changing assigned user
                assignuser.setSelectedItem(Session.getInstance().getU_fname());

                // Set project name only if in Add mode
                if (pname.isEnabled()) {
                    pname.setSelectedItem(projectName);
                    boolean found = false;
                    for (int i = 0; i < pname.getItemCount(); i++) {
                        if (pname.getItemAt(i).equalsIgnoreCase(projectName)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        pname.addItem(projectName);
                        pname.setSelectedItem(projectName);
                    }
                }

                salary.setText(projectSalary);
            }

            rs.close();
            pst.close();
        }

        // ✅ Only set user fields if creating a new task
        if (t_id.getText().isEmpty() || t_id.getText().equals("0")) {
            umaker.setText(sess.getU_fname() != null ? sess.getU_fname() : "");
            user_id.setText(String.valueOf(sess.getU_id()));
        }

        // ✅ Do not overwrite t_id if editing
        if (t_id.getText().isEmpty() || t_id.getText().equals("0")) {
            t_id.setText("0");
        }

        assignuser.setSelectedItem("Select User");

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error loading project info: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (db.connect != null && !db.connect.isClosed()) {
                db.connect.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    }//GEN-LAST:event_formWindowActivated

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        UserDashboard ads = new UserDashboard();
            ads.setVisible(true);
            Taskpageuser tp = new Taskpageuser();
            tp.setVisible(true);
            ads.mainDesktop.add(tp);
            this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
    if (t_id.getText().isEmpty() || 
        pname.getSelectedItem() == null || pname.getSelectedItem().equals("Select Project") ||
        umaker.getText().isEmpty() || 
        status.getSelectedItem() == null || status.getSelectedItem().toString().equalsIgnoreCase("SELECT")) {

        JOptionPane.showMessageDialog(null, "All fields are required and status must be selected!");
        return;
    }

    // Task ID validation
    String taskIdText = t_id.getText().trim();
    System.out.println("DEBUG: Task ID text = '" + taskIdText + "'");
    if (taskIdText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Task ID cannot be empty.");
        return;
    }

    int taskId;
    try {
        taskId = Integer.parseInt(taskIdText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid Task ID format: " + taskIdText);
        return;
    }

    // User ID (task maker) validation
    String userIdText = user_id.getText().trim();  // Assuming this field is in your form
    if (userIdText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "User ID (task maker) is missing.");
        return;
    }

    int u_id;
    try {
        u_id = Integer.parseInt(userIdText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid User ID format: " + userIdText);
        return;
    }

    dbConnector db = new dbConnector();

    try {
        String selectedProject = pname.getSelectedItem().toString();
        int assignUserId = Session.getInstance().getU_id(); // Logged-in user (assignee)

        int p_id = -1;

        // Get project ID based on project name
        String getPidQuery = "SELECT p_id FROM tbl_project WHERE p_name = ?";
        PreparedStatement pst1 = db.connect.prepareStatement(getPidQuery);
        pst1.setString(1, selectedProject);
        ResultSet rs1 = pst1.executeQuery();

        if (rs1.next()) {
            p_id = rs1.getInt("p_id");
        } else {
            JOptionPane.showMessageDialog(null, "Project not found.");
            rs1.close();
            pst1.close();
            return;
        }
        rs1.close();
        pst1.close();

        // Get status
        String statusValue = status.getSelectedItem().toString();
        if (statusValue.equalsIgnoreCase("SELECT") || statusValue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a valid status.");
            return;
        }

        // Update task with correct values
        String updateQuery = "UPDATE tbl_task SET p_id = ?, u_id = ?, user_assign = ?, t_status = ?, accept = ? WHERE t_id = ?";
        PreparedStatement pst3 = db.connect.prepareStatement(updateQuery);
        pst3.setInt(1, p_id);
        pst3.setInt(2, u_id);               // task maker
        pst3.setInt(3, assignUserId);       // assigned to current user
        pst3.setString(4, statusValue);
        pst3.setString(5, "Accepted");
        pst3.setInt(6, taskId);

        int rowsAffected = pst3.executeUpdate();
        pst3.close();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Task updated successfully!");

            UserDashboard uds = new UserDashboard();
            uds.setVisible(true);

            Taskpageuser tp = new Taskpageuser();
            uds.mainDesktop.add(tp);
            tp.setVisible(true);

            this.dispose();
        } else { 
            JOptionPane.showMessageDialog(null, "No task was updated. Double-check task ID or values.");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (db.connect != null && !db.connect.isClosed()) {
                db.connect.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error closing database connection: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_updateActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
         pname.setSelectedItem("");
    }//GEN-LAST:event_clearActionPerformed

    private void pnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pnameActionPerformed
    String selectedProject = (String) pname.getSelectedItem();
    if (selectedProject == null || selectedProject.equals("Select Project")) {
        date.setDate(null);
        due.setDate(null);
        salary.setText(""); // clear salary too
        return;
    }

    dbConnector db = new dbConnector();
    try {
        String query = "SELECT p_date, p_duedate, p_salary FROM tbl_project WHERE p_name = ?";
        try (PreparedStatement pst = db.connect.prepareStatement(query)) {
            pst.setString(1, selectedProject);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                java.sql.Date startDate = rs.getDate("p_date");
                java.sql.Date dueDate = rs.getDate("p_duedate");
                String salaryValue = rs.getString("p_salary");

                date.setDate(startDate != null ? new java.util.Date(startDate.getTime()) : null);
                due.setDate(dueDate != null ? new java.util.Date(dueDate.getTime()) : null);
                salary.setText(salaryValue != null ? salaryValue : "");
            } else {
                date.setDate(null);
                due.setDate(null);
                salary.setText("");
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error fetching project data: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (db.connect != null && !db.connect.isClosed()) {
                db.connect.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error closing DB connection: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_pnameActionPerformed

    private void assignuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assignuserActionPerformed

    private void salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salaryActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(crud_task.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crud_task.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crud_task.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crud_task.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new crud_task().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel assign_name;
    public javax.swing.JComboBox<String> assignuser;
    private javax.swing.JButton cancel;
    private javax.swing.JButton clear;
    public com.toedter.calendar.JDateChooser date;
    public com.toedter.calendar.JDateChooser due;
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
    public javax.swing.JPanel jPanel2;
    public javax.swing.JComboBox<String> pname;
    public javax.swing.JTextField salary;
    public javax.swing.JComboBox<String> status;
    public javax.swing.JTextField t_id;
    public javax.swing.JTextField umaker;
    public javax.swing.JButton update;
    public javax.swing.JTextField user_id;
    // End of variables declaration//GEN-END:variables
}
