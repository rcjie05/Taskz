/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Printer;

import Admin.AdminDashboard;
import Admin.Taskpage;
import config.PanelPrinter;
import config.dbConnector;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;




/**
 *
 * @author PC15
 */
public class printer extends javax.swing.JFrame {
    
    public printer() {
    initComponents();
}
    
    public printer(String t_id, String u_id, String umaker, String pname,
               String assignuser, String email, String contact,
               String gender, String status, String salary) {
    initComponents();

    this.t_id.setText(t_id);
    this.u_id.setText(u_id);
    this.umaker.setText(umaker);
    this.pname.setText(pname);
    this.assignuser.setText(assignuser);
    this.email.setText(email);
    this.contact.setText(contact);
    this.gender.setText(gender);
    this.status.setText(status);
    this.salary.setText(salary);
    }


      public void loadTaskAndUserData(String taskId) {
        dbConnector db = new dbConnector();
        try (Connection conn = db.getConnection()) {
            // Modified SQL: Added project name and salary from project table
            String query = "SELECT " +
                "t.t_id, t.t_status, t.user_assign, " +
                "u.u_id, CONCAT(u.u_fname, ' ', u.u_lname) AS user_name, " +
                "u.u_email, u.u_contact, u.u_gender, " +
                "p.p_name, p.p_salary, " +
                "u.user_image_path, " + 
                "u.u_maker " +  // Assuming you have this field, or replace accordingly
                "FROM tbl_task t " +
                "JOIN tbl_users u ON t.user_assign = u.u_id " +
                "JOIN tbl_projects p ON t.project_id = p.p_id " +
                "WHERE t.t_id = ?";

            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, taskId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Extract values from ResultSet
                String tId = rs.getString("t_id");
                String uId = rs.getString("u_id");
                String uMaker = rs.getString("u_maker");   // or get from some column
                String projectName = rs.getString("p_name");
                String assignUser = rs.getString("user_name");  // or t.user_assign?
                String emailStr = rs.getString("u_email");
                String contactStr = rs.getString("u_contact");
                String genderStr = rs.getString("u_gender");
                String statusStr = rs.getString("t_status");
                String salaryStr = rs.getString("p_salary");


                t_id.setText(tId);
                u_id.setText(uId);
                umaker.setText(uMaker != null ? uMaker : ""); // check null if needed
                pname.setText(projectName);
                assignuser.setText(assignUser);
                email.setText(emailStr);
                contact.setText(contactStr);
                gender.setText(genderStr);
                status.setText(statusStr);
                salary.setText(salaryStr);

                // Load image
                String imagePath = rs.getString("user_image_path");
                if (imagePath != null && !imagePath.isEmpty()) {
                    image.setIcon(ResizeImage(imagePath, null, image));
                    image.setText("");
                } else {
                    image.setIcon(null);
                    image.setText("No Image");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Task not found.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ImageIcon ResizeImage(String ImagePath, byte[] pic, JLabel label) {
        ImageIcon MyImage;
        if (ImagePath != null) {
            MyImage = new ImageIcon(ImagePath);
        } else {
            MyImage = new ImageIcon(pic);
        }

        int newHeight = getHeightFromWidth(ImagePath, label.getWidth());
        if (newHeight == -1) {
            newHeight = label.getHeight();
        }

        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public static int getHeightFromWidth(String imagePath, int desiredWidth) {
        try {
            File imageFile = new File(imagePath);
            BufferedImage image = ImageIO.read(imageFile);
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            return (int) ((double) desiredWidth / originalWidth * originalHeight);
        } catch (IOException ex) {
            System.out.println("No image found!");
        }
        return -1;
    }
       

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
        page = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        taskID = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        image = new javax.swing.JLabel();
        salary = new javax.swing.JLabel();
        umaker = new javax.swing.JLabel();
        pname = new javax.swing.JLabel();
        assignuser = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        contact = new javax.swing.JLabel();
        gender = new javax.swing.JLabel();
        t_id = new javax.swing.JLabel();
        u_id = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        prints = new javax.swing.JButton();
        cancels = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jLabel9.setText("jLabel9");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        page.setBackground(new java.awt.Color(51, 255, 153));
        page.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Salary of Project:");
        page.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 110, 26));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Email:");
        page.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 110, 26));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Approve By:");
        page.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 110, 26));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Project Name:");
        page.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 110, 26));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Contact:");
        page.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 110, 26));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Gender:");
        page.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 110, 26));

        taskID.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        taskID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        taskID.setText("Task ID:");
        page.add(taskID, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 110, 26));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText(" Employee Name:");
        page.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 110, 26));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Status of Project:");
        page.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 110, 26));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("User ID:");
        page.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 110, 26));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Salary Receipt");
        page.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 30));

        image.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        image.setText("No I mage");
        image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        page.add(image, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 100, 100));
        page.add(salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 220, 26));
        page.add(umaker, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, 26));
        page.add(pname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 120, 26));
        page.add(assignuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 220, 26));
        page.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 220, 26));
        page.add(contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 220, 26));
        page.add(gender, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 220, 26));
        page.add(t_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 120, 26));
        page.add(u_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 120, 26));
        page.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, 220, 26));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel10.setText("\" Thank you for your Hard work in our Company \"");
        page.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        jPanel1.add(page, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 410, 430));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TASK MANAGEMENT");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 30));

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel23.setText("SYSTEM");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 140, 30));

        prints.setText("PRINT");
        prints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printsActionPerformed(evt);
            }
        });
        jPanel1.add(prints, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 90, 80, 30));

        cancels.setText("CANCEL");
        cancels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelsActionPerformed(evt);
            }
        });
        jPanel1.add(cancels, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 130, 80, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Untitled Project.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 680, -1));

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
       
    }//GEN-LAST:event_formWindowActivated

    private void printsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printsActionPerformed
    PanelPrinter printer = new PanelPrinter(page);
        printer.printPanel();
    }//GEN-LAST:event_printsActionPerformed

    private void cancelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelsActionPerformed
        AdminDashboard ads = new AdminDashboard();
            ads.setVisible(true);
            Taskpage tp = new Taskpage();
            tp.setVisible(true);
            ads.mainDesktop.add(tp);
            this.dispose();
    }//GEN-LAST:event_cancelsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Nimbus look and feel not found.");
        }

        java.awt.EventQueue.invokeLater(() -> {
            new printer().setVisible(true);
        });
   
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel assignuser;
    private javax.swing.JButton cancels;
    private javax.swing.JLabel contact;
    private javax.swing.JLabel email;
    private javax.swing.JLabel gender;
    private javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel page;
    private javax.swing.JLabel pname;
    private javax.swing.JButton prints;
    private javax.swing.JLabel salary;
    private javax.swing.JLabel status;
    private javax.swing.JLabel t_id;
    private javax.swing.JLabel taskID;
    private javax.swing.JLabel u_id;
    private javax.swing.JLabel umaker;
    // End of variables declaration//GEN-END:variables
}