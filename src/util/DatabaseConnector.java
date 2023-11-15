/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.*;
import java.util.ArrayList;
import model.Patient;

/**
 *
 * @author gauravvraii
 */
public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/test?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    /**
    * Privatized constructor so as to not allow object creation
    */
    private DatabaseConnector() {
    }
    /**
    * Insert given user to database
    * @see User
    * @param patient User object to be added
    */
    public static void addPatient(Patient patient) {
    //add to database
        String query = "INSERT INTO test(NAME,AGE) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME,PASSWORD)) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            int rows = stmt.executeUpdate();
            System.out.println("Rows impacted : " + rows);      
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
    * Return lost of all users in database
    * @see Patient
    * @return list of Patient
    */
    public static ArrayList<Patient> getAllpatients() {
        // return list of users from db
        ArrayList<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM test";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME,PASSWORD)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Patient p = new Patient();
                p.setName(rs.getString("name"));
                p.setAge(rs.getInt("age"));
                p.setId(rs.getInt("id"));
                patients.add(p);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            }
        return patients;
        }
        /**
        * Delete given user from database
        * @see User
        * @param p User to be deleted
        *
        */
        public static void deletePatient(Patient p) {
            String query = "delete from test where id = ?";
                try (Connection conn = DriverManager.getConnection(URL, USERNAME,PASSWORD)) {
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1, p.getId());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
            }
        }
        /**
        * Edit given user details in the database
        * @param oldPatient existing user in database
        * @param newPatient modified user details to be added
        */
        public static void updatePatient(Patient oldPatient, Patient newPatient) {
            String query = "UPDATE test SET name=?, age=? WHERE id=?";
                try (Connection conn = DriverManager.getConnection(URL, USERNAME,PASSWORD)) {
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, newPatient.getName());
                    stmt.setInt(2, newPatient.getAge());
                    stmt.setInt(3, oldPatient.getId());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
        }
       }
    }
