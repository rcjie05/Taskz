/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class dbConnector {

    public Connection connect;

    public dbConnector() {
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/task", "root", "");
        } catch (SQLException ex) {
            System.out.println("Can't connect to database: " + ex.getMessage());
        }
    }

    // Function to retrieve data
    public ResultSet getData(String sql) throws SQLException {
        Statement stmt = connect.createStatement();
        ResultSet rst = stmt.executeQuery(sql);
        return rst;
    }

    // Function to save data
    public boolean insertData(String sql) {
        try {
            PreparedStatement pst = connect.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Inserted Successfully!");
            pst.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Connection Error: " + ex);
            return false;
        }
    }

    // Function to update data
    public void updateData(String sql) {
        try {
            PreparedStatement pst = connect.prepareStatement(sql);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data Updated Successfully!");
            } else {
                System.out.println("Data Update Failed!");
            }
            pst.close();
        } catch (SQLException ex) {
            System.out.println("Connection Error: " + ex);
        }
    }

    public void deleteData(String query) {
        try {
            PreparedStatement pst = connect.prepareStatement(query);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "User deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No user was deleted. Please check the ID.");
            }
            pst.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting user: " + ex.getMessage());
        }
    }
    public boolean insertLog(int userId, String action) {
        try {
            String sql = "INSERT INTO tbl_logs (u_id, l_action, l_timestamp) VALUES (?, ?, NOW())";
            PreparedStatement pst = connect.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setString(2, action);
            pst.executeUpdate();
            pst.close();
            System.out.println("Log inserted successfully!");
            return true;
        } catch (SQLException ex) {
            System.out.println("Error inserting log: " + ex.getMessage());
            return false;
        }
    }

    // Corrected getConnection() method
    public Connection getConnection() {
        return connect;
    }
}