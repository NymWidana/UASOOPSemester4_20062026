/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formpemesanan;

import static formpemesanan.dbconnect.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author I Nyoman Widana
 * tgl : 7 Juni 2026
 * 
 */
public class Pesanan {
    // Atribut-atribut untuk menyimpan data pesanan
    private int id;
    private String NamaProduk;
    private int JumlahProduk;
    private double HargaProduk;
    private String TipePemesanan;
    private String TanggalPemesanan;
    private String TanggalDeadline;
    private String KategoriProduk;

    // Konstruktor untuk inisialisasi objek Pesanan
    public Pesanan(String NamaProduk, int JumlahProduk, double HargaProduk, String TipePemesanan, String TanggalPemesanan, String TanggalDeadline, String KategoriProduk) {
        this.NamaProduk = NamaProduk;
        this.JumlahProduk = JumlahProduk;
        this.HargaProduk = HargaProduk;
        this.TipePemesanan = TipePemesanan;
        this.TanggalPemesanan = TanggalPemesanan;
        this.TanggalDeadline = TanggalDeadline;
        this.KategoriProduk = KategoriProduk;
    }

    // Konstruktor tambahan untuk inisialisasi objek Pesanan dengan ID
    public Pesanan(int id, String NamaProduk, int JumlahProduk, double HargaProduk, String TipePemesanan, String TanggalPemesanan, String TanggalDeadline, String KategoriProduk) {
        this.id = id;
        this.NamaProduk = NamaProduk;
        this.JumlahProduk = JumlahProduk;
        this.HargaProduk = HargaProduk;
        this.TipePemesanan = TipePemesanan;
        this.TanggalPemesanan = TanggalPemesanan;
        this.TanggalDeadline = TanggalDeadline;
        this.KategoriProduk = KategoriProduk;
    }

    // Getter untuk mengakses atribut-atribut Pesanan
    public int getId() {
        return id;
    }
    public String getNamaProduk() {
        return NamaProduk;
    }
    public int getJumlahProduk() {
        return JumlahProduk;
    }
    public double getHargaProduk() {
        return HargaProduk;
    }
    public String getTipePemesanan() {
        return TipePemesanan;
    }
    public String getTanggalPemesanan() {
        return TanggalPemesanan;
    }
    public String getTanggalDeadline() {
        return TanggalDeadline;
    }
    public String getKategoriProduk() {
        return KategoriProduk;
    }
    // Metode untuk menghitung total harga berdasarkan jumlah produk dan harga produk
    public double getTotalHarga() {
        return JumlahProduk * HargaProduk;
    }

    // Metode untuk operasi CRUD (Create, Read, Update, Delete) pada database
    // Metode untuk mendapatkan semua pesanan dari database
    public static Pesanan[] all() {
        Pesanan[] pesananList = null;
        int index = 0;
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pesanan ORDER BY tanggal ASC");
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String namaProduk = rs.getString("produk");
                int jumlahProduk = rs.getInt("jumlah");
                double hargaProduk = rs.getDouble("harga");
                String tipePemesanan = rs.getString("tipe");
                String tanggalPemesanan = rs.getString("tanggal");
                String tanggalDeadline = rs.getString("deadline");
                String kategoriProduk = rs.getString("kategori");
                Pesanan pesanan = new Pesanan(id, namaProduk, jumlahProduk, hargaProduk, tipePemesanan, tanggalPemesanan, tanggalDeadline, kategoriProduk);
                if (pesananList == null) {
                    pesananList = new Pesanan[1];
                } else {
                    Pesanan[] tempList = new Pesanan[pesananList.length + 1];
                    System.arraycopy(pesananList, 0, tempList, 0, pesananList.length);
                    pesananList = tempList;
                }
                pesananList[index++] = pesanan;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesananList;
    }
    
    // Metode untuk mendapatkan pesanan berdasarkan ID dari database
    public static Pesanan getPesananById(int id) {
        Pesanan pesanan = null;
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pesanan WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idPesanan = rs.getInt("id");
                String namaProduk = rs.getString("produk");
                int jumlahProduk = rs.getInt("jumlah");
                double hargaProduk = rs.getDouble("harga");
                String tipePemesanan = rs.getString("tipe");
                String tanggalPemesanan = rs.getString("tanggal");
                String tanggalDeadline = rs.getString("deadline");
                String kategoriProduk = rs.getString("kategori");
                pesanan = new Pesanan(idPesanan, namaProduk, jumlahProduk, hargaProduk, tipePemesanan, tanggalPemesanan, tanggalDeadline, kategoriProduk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pesanan;
    }

    // Metode untuk menyimpan pesanan baru ke database
    public void save() {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO pesanan (produk, jumlah, harga, tipe, tanggal, deadline, kategori) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, NamaProduk);
            stmt.setInt(2, JumlahProduk);
            stmt.setDouble(3, HargaProduk);
            stmt.setString(4, TipePemesanan);
            stmt.setString(5, TanggalPemesanan);
            stmt.setString(6, TanggalDeadline);
            stmt.setString(7, KategoriProduk);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk memperbarui data pesanan di database berdasarkan ID
    public void update(int id) {
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE pesanan SET produk = ?, jumlah = ?, harga = ?, tipe = ?, tanggal = ?, deadline = ?, kategori = ? WHERE id = ?")) {
            stmt.setString(1, NamaProduk);
            stmt.setInt(2, JumlahProduk);
            stmt.setDouble(3, HargaProduk);
            stmt.setString(4, TipePemesanan);
            stmt.setString(5, TanggalPemesanan);
            stmt.setString(6, TanggalDeadline);
            stmt.setString(7, KategoriProduk);
            stmt.setInt(8, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menghapus pesanan dari database berdasarkan ID
    public static void delete(int id) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM pesanan WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
