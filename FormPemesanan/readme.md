# 📦 Aplikasi Form Pemesanan Berbasis Java Swing & MySQL

Aplikasi desktop manajemen transaksi pemesanan produk yang dibangun menggunakan bahasa pemrograman **Java (OOP)**, framework GUI **Java Swing**, dan sistem manajemen basis data **MySQL** melalui konektivitas **JDBC (Java Database Connectivity)**. Proyek ini dirancang untuk memenuhi standar penilaian praktikum Pemrograman Berorientasi Objek (PBO).

---

## 🚀 Fitur Utama

- **Manajemen CRUD Lengkap**: Mendukung operasi pembuatan (*Create*), pembacaan (*Read*), pengubahan (*Update*), dan penghapusan (*Delete*) data pesanan secara langsung ke database MySQL.
- **Penerapan Pilar OOP**: 
  - **Encapsulation**: Proteksi variabel state internal pada kelas `Pesanan.java` menggunakan modifier `private` dan diakses melalui metode *Getter*.
  - **Polymorphism (Constructor Overloading)**: Menyediakan konstruktor ganda untuk membedakan instansiasi entri data baru dengan pemetaan data yang ditarik dari database.
- **Logika Cerdas Input Tanggal (Dynamic Date)**: Validasi otomatis kapasitas maksimum hari (28, 29, 30, atau 31 hari) pada komponen ComboBox Tanggal berdasarkan kombinasi Bulan dan Tahun pilihan user (mendukung deteksi tahun kabisat otomatis lewat engine `java.time.YearMonth`).
- **Antarmuka Responsif (UI Sync)**: Sinkronisasi visual instan antara form masukan, komponen `JTable`, dan database MySQL melalui tombol Refresh dan event seleksi baris tabel.

---

## 🛠️ Struktur File Proyek

Proyek ini terbagi ke dalam beberapa modul kelas utama di dalam package `formpemesanan`:

```text
src/formpemesanan/
├── FormPemesanan.java         # Main class / Entry point untuk menjalankan aplikasi
├── FrameFormPemesanan.java    # Desain UI Jframe kontainer dan kontrol event handler
├── FrameFormPemesanan.form    # Metadata layout form XML bawaan IDE NetBeans
├── Pesanan.java               # Model entitas objek pembentuk data dan kueri CRUD SQL
└── dbconnect.java             # Konfigurasi driver JDBC dan pembukaan koneksi MySQL

```

---

## 🗄️ Spesifikasi Database

Aplikasi ini menggunakan database bernama `pemesanan` dengan tabel bernama `pesanan`. Berikut adalah skema strukturnya:

| Nama Kolom | Tipe Data | Atribut | Keterangan |
| --- | --- | --- | --- |
| `id` | INT | Primary Key, Auto Increment | ID Unik Pesanan |
| `produk` | VARCHAR(100) | Not Null | Nama produk yang dipesan |
| `jumlah` | INT | Not Null | Kuantitas jumlah barang |
| `harga` | DOUBLE | Not Null | Harga satuan produk |
| `tipe` | VARCHAR(20) | Not Null | Metode pemesanan (Online / Offline) |
| `tanggal` | DATE | Not Null | Tanggal masuk pesanan |
| `deadline` | DATE | Not Null | Batas waktu penyelesaian |
| `kategori` | VARCHAR(50) | Not Null | Kelompok kategori produk |

### Kueri SQL Pembuatan Tabel:

```sql
CREATE DATABASE IF NOT EXISTS pemesanan;
USE pemesanan;

CREATE TABLE IF NOT EXISTS pesanan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    produk VARCHAR(100) NOT NULL,
    jumlah INT NOT NULL,
    harga DOUBLE NOT NULL,
    tipe VARCHAR(20) NOT NULL,
    tanggal DATE NOT NULL,
    deadline DATE NOT NULL,
    kategori VARCHAR(50) NOT NULL
);

```

---

## ⚙️ Cara Instalasi dan Menjalankan Proyek

### Prerequisites (Persyaratan Sistem)

1. **Java Development Kit (JDK)** versi 8 atau yang lebih baru (sangat direkomendasikan JDK 11/17+ karena menggunakan kelas `java.time.YearMonth`).
2. **MySQL Server** (bisa menggunakan bundel XAMPP/AppServ).
3. **MySQL JDBC Connector Driver** (`mysql-connector-j`).
4. IDE pilihan seperti **NetBeans**, **Eclipse**, atau **IntelliJ IDEA**.

### Langkah demi Langkah

1. **Kloning Repositori ini:**
```bash
git clone [https://github.com/username/project-repo-name.git](https://github.com/username/project-repo-name.git)

```


2. **Setup Database:**
* Jalankan MySQL Server (melalui XAMPP Control Panel).
* Buka `phpMyAdmin` atau terminal database kamu.
* Buat database baru bernama `pemesanan` dan eksekusi perintah SQL pembuatan tabel di atas.


3. **Konfigurasi Koneksi:**
* Jika kamu menggunakan username atau password database yang berbeda, sesuaikan konfigurasinya di dalam file `src/formpemesanan/dbconnect.java`:
```java
static String DB_URL = "jdbc:mysql://localhost:3306/pemesanan";
static String DB_USERNAME = "root"; // Sesuaikan username database kamu
static String DB_PASSWORD = "";     // Sesuaikan password database kamu

```




4. **Tambahkan Library JDBC:**
* Pastikan file `mysql-connector-j-xxx.jar` sudah ditambahkan ke dalam bagian **Libraries** proyek di IDE kamu.


5. **Jalankan Aplikasi:**
* Buka file `FormPemesanan.java`.
* Klik kanan dan pilih **Run File** (atau tekan `Shift + F6` di NetBeans).



---

## 👤 Identitas Pengembang

* **Nama:** I Nyoman Widana
* **Program Studi:** Teknik Informatika
* **Instansi:** Institut Bisnis dan Teknologi Indonesia (INSTIKI)
