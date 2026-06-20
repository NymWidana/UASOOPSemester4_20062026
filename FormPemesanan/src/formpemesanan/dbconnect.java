/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formpemesanan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author I Nyoman Widana
 * tgl : 7 Juni 2026
 * 
 */
public class dbconnect {
    // Atribut-atribut untuk koneksi database
    static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/pemesanan";
    static String DB_USERNAME = "root";
    static String DB_PASSWORD = "";
    public static Connection connect(){
        try {
            // Memuat driver JDBC dan membuat koneksi ke database
            Class.forName(DB_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            // Menangani kesalahan koneksi dan menampilkan pesan error
            System.out.println("Koneksi Gagal: " + e.getMessage());
            return null;
        }
    }
}
