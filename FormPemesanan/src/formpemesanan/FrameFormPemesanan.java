/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formpemesanan;

// Import statements untuk kelas-kelas yang digunakan dalam aplikasi
import java.time.YearMonth;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author I Nyoman Widana
 * tgl : 7 Juni 2026
 * 
 */
public class FrameFormPemesanan extends javax.swing.JFrame {

    // Logger untuk mencatat informasi dan kesalahan dalam aplikasi
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrameFormPemesanan.class.getName());

    /**
     * Creates new form FrameFormPemesanan
     */

    // Model untuk tabel pesanan dan variabel untuk menyimpan ID pesanan yang sedang dipilih
    DefaultTableModel model = new DefaultTableModel();

    // Variabel untuk menyimpan ID pesanan yang sedang dipilih (-1 berarti tidak ada yang dipilih)
    int selectedPesananId = -1;

    // Array untuk nama bulan dan tanggal yang akan digunakan dalam ComboBox
    String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    // Array untuk tanggal 1-31 yang akan digunakan dalam ComboBox
    String[] tanggal = new String[31];

    // Konstruktor untuk inisialisasi form dan komponen-komponennya
    public FrameFormPemesanan() {
        // Inisialisasi komponen GUI dan pengaturan awal form
        initComponents();

        // Set up model untuk tabel pesanan dan konfigurasi ComboBox untuk kategori produk, tanggal pemesanan, dan tanggal deadline
        // Set up model untuk tabel pesanan
        tbPesanan.setModel(model);
        model.addColumn("ID");
        model.addColumn("Nama Produk");
        model.addColumn("Jumlah");
        model.addColumn("Tanggal Pemesanan");
        model.addColumn("Tanggal Deadline");
        model.addColumn("Tipe Pemesanan");
        model.addColumn("Harga Per Produk");
        model.addColumn("Total Harga");
        model.addColumn("Kategori Produk");

        // Konfigurasi ComboBox untuk kategori produk
        String[] kategoriProduk = {"Elektronik", "Fashion", "Makanan", "Peralatan"};
        cbxKategoriProduk.removeAllItems();
        for (String kategori : kategoriProduk) {
            cbxKategoriProduk.addItem(kategori);
        }

        // Konfigurasi ComboBox untuk tanggal pemesanan dan tanggal deadline
        for (int i = 1; i <= 31; i++) {
            tanggal[i - 1] = String.valueOf(i);
        }
        cbxTanggalPemesanan.removeAllItems();
        for (String tgl : tanggal) {
            cbxTanggalPemesanan.addItem(tgl);
        }
        cbxBulanPemesanan.removeAllItems();
        for (String bln : bulan) {
            cbxBulanPemesanan.addItem(bln);
        }
        String[] tahun = new String[10];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            tahun[i] = String.valueOf(currentYear + i);
        }
        cbxTahunPemesanan.removeAllItems();
        for (String thn : tahun) {
            cbxTahunPemesanan.addItem(thn);
        }
        cbxTanggalDeadline.removeAllItems();
        for (String tgl : tanggal) {
            cbxTanggalDeadline.addItem(tgl);
        }
        cbxBulanDeadline.removeAllItems();
        for (String bln : bulan) {
            cbxBulanDeadline.addItem(bln);
        }
        cbxTahunDeadline.removeAllItems();
        for (String thn : tahun) {
            cbxTahunDeadline.addItem(thn);
        }

        // Set nilai default untuk form inputan dan jalankan penyesuaian tanggal pertama kali saat form dibuka
        tfNamaProduk.setText("");
        tfJumlahProduk.setText("");
        tfHargaPerProduk.setText("");
        rbTipePemesananOnline.setSelected(true);

        // Jalankan penyesuaian pertama kali saat form dibuka
        sesuaikanTanggal(cbxTahunPemesanan, cbxBulanPemesanan, cbxTanggalPemesanan);
        sesuaikanTanggal(cbxTahunDeadline, cbxBulanDeadline, cbxTanggalDeadline);

        // Tampilkan data pesanan yang sudah ada di database saat form dibuka
        listPesanan(Pesanan.all());
    }

    // Metode untuk menampilkan daftar pesanan di tabel berdasarkan data yang diambil dari database
    public void listPesanan(Pesanan[] pesananList) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        model.setRowCount(0);
        if (pesananList != null) {
            for (Pesanan pesanan : pesananList) {
                Object[] rowData = {
                    pesanan.getId(),
                    pesanan.getNamaProduk(),
                    pesanan.getJumlahProduk(),
                    pesanan.getTanggalPemesanan(),
                    pesanan.getTanggalDeadline(),
                    pesanan.getTipePemesanan(),
                    pesanan.getHargaProduk(),
                    pesanan.getTotalHarga(),
                    pesanan.getKategoriProduk()
                };
                model.addRow(rowData);
            }
        }
    }

    // Metode untuk menyegarkan daftar pesanan di tabel dengan mengambil data terbaru dari database
    public void refreshPesanan() {
        listPesanan(Pesanan.all());
    }

    // Metode untuk membersihkan form inputan setelah menyimpan atau memperbarui pesanan
    public void clearForm() {
        tfNamaProduk.setText("");
        tfJumlahProduk.setText("");
        tfHargaPerProduk.setText("");
        rbTipePemesananOnline.setSelected(true);
        cbxTanggalPemesanan.setSelectedIndex(0);
        cbxBulanPemesanan.setSelectedIndex(0);
        cbxTahunPemesanan.setSelectedIndex(0);
        cbxTanggalDeadline.setSelectedIndex(0);
        cbxBulanDeadline.setSelectedIndex(0);
        cbxTahunDeadline.setSelectedIndex(0);
        cbxKategoriProduk.setSelectedIndex(0);
    }

    // Metode untuk menyimpan pesanan baru ke database dengan mengambil data dari form inputan
    public void simpanPesanan() {
        String namaProduk = tfNamaProduk.getText();
        int jumlahProduk = Integer.parseInt(tfJumlahProduk.getText());
        double hargaPerProduk = Double.parseDouble(tfHargaPerProduk.getText());
        String tipePemesanan = rbTipePemesananOnline.isSelected() ? "online" : "offline";
        String tanggalPemesanan = cbxTahunPemesanan.getSelectedItem().toString() + "-" + String.format("%02d", Arrays.asList(bulan).indexOf(cbxBulanPemesanan.getSelectedItem().toString()) + 1) + "-" + String.format("%02d", Integer.parseInt(cbxTanggalPemesanan.getSelectedItem().toString()));
        String tanggalDeadline = cbxTahunDeadline.getSelectedItem().toString() + "-" + String.format("%02d", Arrays.asList(bulan).indexOf(cbxBulanDeadline.getSelectedItem().toString()) + 1) + "-" + String.format("%02d", Integer.parseInt(cbxTanggalDeadline.getSelectedItem().toString()));
        String kategoriProduk = cbxKategoriProduk.getSelectedItem().toString().toLowerCase();

        Pesanan pesananBaru = new Pesanan(namaProduk, jumlahProduk, hargaPerProduk, tipePemesanan, tanggalPemesanan, tanggalDeadline, kategoriProduk);
        pesananBaru.save();
        refreshPesanan();
        clearForm();
    }

    // Metode untuk memperbarui data pesanan yang sudah ada di database berdasarkan ID dengan mengambil data dari form inputan
    public void updatePesanan(int id) {
        String namaProduk = tfNamaProduk.getText();
        int jumlahProduk = Integer.parseInt(tfJumlahProduk.getText());
        double hargaPerProduk = Double.parseDouble(tfHargaPerProduk.getText());
        String tipePemesanan = rbTipePemesananOnline.isSelected() ? "online" : "offline";
        String tanggalPemesanan = cbxTahunPemesanan.getSelectedItem().toString() + "-" + String.format("%02d", Arrays.asList(bulan).indexOf(cbxBulanPemesanan.getSelectedItem().toString()) + 1) + "-" + String.format("%02d", Integer.parseInt(cbxTanggalPemesanan.getSelectedItem().toString()));
        String tanggalDeadline = cbxTahunDeadline.getSelectedItem().toString() + "-" + String.format("%02d", Arrays.asList(bulan).indexOf(cbxBulanDeadline.getSelectedItem().toString()) + 1) + "-" + String.format("%02d", Integer.parseInt(cbxTanggalDeadline.getSelectedItem().toString()));
        String kategoriProduk = cbxKategoriProduk.getSelectedItem().toString().toLowerCase();

        Pesanan pesananUpdate = new Pesanan(namaProduk, jumlahProduk, hargaPerProduk, tipePemesanan, tanggalPemesanan, tanggalDeadline, kategoriProduk);
        pesananUpdate.update(id);
        refreshPesanan();
        clearForm();
    }

    // Metode untuk mendapatkan pesanan berdasarkan ID dari database
    public Pesanan getPesananById(int id) {
        return Pesanan.getPesananById(id);
    }

    // Metode untuk menyiapkan form inputan dengan data pesanan yang sudah ada di database berdasarkan ID untuk keperluan edit
    public void siapkanFormUntukEdit(int id) {
        Pesanan pesanan = getPesananById(id);
        if (pesanan != null) {
            tfNamaProduk.setText(pesanan.getNamaProduk());
            tfJumlahProduk.setText(String.valueOf(pesanan.getJumlahProduk()));
            tfHargaPerProduk.setText(String.valueOf(pesanan.getHargaProduk()));
            if (pesanan.getTipePemesanan().equals("online")) {
                rbTipePemesananOnline.setSelected(true);
            } else {
                rbTipePemesananOffline.setSelected(true);
            }
            String[] tanggalPemesananParts = pesanan.getTanggalPemesanan().split("-");
            cbxTanggalPemesanan.setSelectedItem(tanggal[Integer.parseInt(tanggalPemesananParts[2]) - 1]);
            cbxBulanPemesanan.setSelectedItem(bulan[Integer.parseInt(tanggalPemesananParts[1]) - 1]);
            cbxTahunPemesanan.setSelectedItem(tanggalPemesananParts[0]);
            String[] tanggalDeadlineParts = pesanan.getTanggalDeadline().split("-");
            cbxTanggalDeadline.setSelectedItem(tanggal[Integer.parseInt(tanggalDeadlineParts[2]) - 1]);
            cbxBulanDeadline.setSelectedItem(bulan[Integer.parseInt(tanggalDeadlineParts[1]) - 1]);
            cbxTahunDeadline.setSelectedItem(tanggalDeadlineParts[0]);
            cbxKategoriProduk.setSelectedItem(pesanan.getKategoriProduk().substring(0, 1).toUpperCase() + pesanan.getKategoriProduk().substring(1).toLowerCase());
        }
    }
    
    // Metode untuk melakukan validasi inputan sebelum menyimpan atau memperbarui pesanan, yang akan menampilkan pesan error jika ada field yang kosong atau format inputan yang salah
    public int validasiInput() {
        if (tfNamaProduk.getText().isEmpty() || tfJumlahProduk.getText().isEmpty() || tfHargaPerProduk.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        try {
            Integer.parseInt(tfJumlahProduk.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah Produk harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        try {
            Double.parseDouble(tfHargaPerProduk.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga Per Produk harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        return 1;
     }

    // Metode untuk menghapus pesanan dari database berdasarkan ID dan menyegarkan daftar pesanan di tabel setelah penghapusan
    public void hapusPesanan(int id) {
        Pesanan.delete(id);
        refreshPesanan();
    }

    // Metode untuk menyesuaikan isi ComboBox Tanggal berdasarkan bulan dan tahun yang dipilih untuk memastikan tanggal yang valid sesuai dengan jumlah hari dalam bulan tersebut
    private void sesuaikanTanggal(javax.swing.JComboBox<String> cbxThn, javax.swing.JComboBox<String> cbxBln, javax.swing.JComboBox<String> cbxTgl) {
        // 1. Pastikan tidak null
        if (cbxThn.getSelectedItem() == null || cbxBln.getSelectedItem() == null || cbxTgl.getSelectedItem() == null) {
            return;
        }

        String thnStr = cbxThn.getSelectedItem().toString();
        String tglStr = cbxTgl.getSelectedItem().toString();

        // 2. Cegah error jika isi combobox masih teks cetakan NetBeans seperti "Item 1"
        if (!thnStr.matches("\\d+") || !tglStr.matches("\\d+")) {
            return; 
        }

        try {
            // Ambil nilai tahun dan bulan yang sedang dipilih
            int tahunTerpilih = Integer.parseInt(thnStr);
            String namaBulanTerpilih = cbxBln.getSelectedItem().toString();
            int bulanTerpilih = Arrays.asList(this.bulan).indexOf(namaBulanTerpilih) + 1;

            // Ambil tanggal yang sedang dipilih sebelum di-update
            int tanggalSebelumnya = Integer.parseInt(tglStr);

            // Hitung jumlah hari maksimum menggunakan Java Time
            YearMonth yearMonth = YearMonth.of(tahunTerpilih, bulanTerpilih);
            int maxHari = yearMonth.lengthOfMonth();

            // Update isi ComboBox Tanggal
            cbxTgl.removeAllItems();
            for (int i = 1; i <= maxHari; i++) {
                cbxTgl.addItem(String.valueOf(i));
            }

            // Kembalikan pilihan ke tanggal sebelumnya atau tanggal maks yang mungkin
            if (tanggalSebelumnya <= maxHari) {
                cbxTgl.setSelectedItem(String.valueOf(tanggalSebelumnya));
            } else {
                cbxTgl.setSelectedItem(String.valueOf(maxHari));
            }
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal menyesuaikan tanggal", e);
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

        TipePemesanan = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfNamaProduk = new javax.swing.JTextField();
        tfJumlahProduk = new javax.swing.JTextField();
        rbTipePemesananOnline = new javax.swing.JRadioButton();
        rbTipePemesananOffline = new javax.swing.JRadioButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        tfHargaPerProduk = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbxKategoriProduk = new javax.swing.JComboBox<>();
        cbxTanggalPemesanan = new javax.swing.JComboBox<>();
        cbxBulanPemesanan = new javax.swing.JComboBox<>();
        cbxTahunPemesanan = new javax.swing.JComboBox<>();
        cbxTanggalDeadline = new javax.swing.JComboBox<>();
        cbxBulanDeadline = new javax.swing.JComboBox<>();
        cbxTahunDeadline = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPesanan = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 700));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Form Pemesanan");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Nama Produk");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Jumlah Produk");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal Pemesanan");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tipe Pemesanan");

        tfNamaProduk.setText("jTextField1");

        tfJumlahProduk.setText("jTextField2");

        TipePemesanan.add(rbTipePemesananOnline);
        rbTipePemesananOnline.setSelected(true);
        rbTipePemesananOnline.setText("Online");
        rbTipePemesananOnline.addActionListener(this::rbTipePemesananOnlineActionPerformed);

        TipePemesanan.add(rbTipePemesananOffline);
        rbTipePemesananOffline.setText("Offline");
        rbTipePemesananOffline.addActionListener(this::rbTipePemesananOfflineActionPerformed);

        btnSimpan.setText("Simpan");
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSimpanMouseClicked(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHapusMouseClicked(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshMouseClicked(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKeluarMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Harga Per Produk");

        tfHargaPerProduk.setText("jTextField1");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Tanggal Deadline");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Kategori Produk");

        cbxKategoriProduk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxTanggalPemesanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxBulanPemesanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxBulanPemesanan.addActionListener(this::cbxBulanPemesananActionPerformed);

        cbxTahunPemesanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTahunPemesanan.addActionListener(this::cbxTahunPemesananActionPerformed);

        cbxTanggalDeadline.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxBulanDeadline.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxBulanDeadline.addActionListener(this::cbxBulanDeadlineActionPerformed);

        cbxTahunDeadline.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTahunDeadline.addActionListener(this::cbxTahunDeadlineActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRefresh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnKeluar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(rbTipePemesananOnline, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(rbTipePemesananOffline, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(tfNamaProduk)
                                        .addComponent(tfJumlahProduk)
                                        .addComponent(tfHargaPerProduk)
                                        .addComponent(cbxKategoriProduk, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbxBulanPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxTanggalPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxTahunPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbxBulanDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxTanggalDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbxTahunDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(390, 404, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfNamaProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfJumlahProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tfHargaPerProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rbTipePemesananOnline)
                    .addComponent(rbTipePemesananOffline))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbxTanggalPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxBulanPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTahunPemesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbxTanggalDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxBulanDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTahunDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbxKategoriProduk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnRefresh)
                    .addComponent(btnKeluar))
                .addContainerGap())
        );

        tbPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama Produk", "Jumlah", "Tanggal", "Deadline", "Tipe", "Harga", "Total Harga", "Kategori"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbPesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPesananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPesanan);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbTipePemesananOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTipePemesananOnlineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbTipePemesananOnlineActionPerformed

    private void rbTipePemesananOfflineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTipePemesananOfflineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbTipePemesananOfflineActionPerformed

    // Metode untuk menangani event klik pada baris tabel pesanan, yang akan menampilkan data pesanan yang dipilih di form inputan untuk keperluan edit
    private void tbPesananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPesananMouseClicked
        int selectedRow = tbPesanan.getSelectedRow();
        if (selectedRow >= 0) {
            btnSimpan.setText("Update");
            int pesananId = (int) model.getValueAt(selectedRow, 0);
            selectedPesananId = pesananId;
            siapkanFormUntukEdit(pesananId);
        }
    }//GEN-LAST:event_tbPesananMouseClicked

    // Metode untuk menangani event klik pada tombol Refresh, yang akan menyegarkan daftar pesanan di tabel dengan mengambil data terbaru dari database dan mengembalikan form inputan ke kondisi default
    private void btnRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshMouseClicked
        refreshPesanan();
        selectedPesananId = -1;
        clearForm();
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnRefreshMouseClicked

    // Metode untuk menangani event klik pada tombol Keluar, yang akan menampilkan konfirmasi kepada pengguna sebelum menutup aplikasi
    private void btnKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKeluarMouseClicked
        int opsi = JOptionPane.showOptionDialog(
                    this, 
                    "Yakin Akan Menutup Aplikasi?", 
                    "Konfirmasi", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    null, 
                    null);
        if(opsi == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_btnKeluarMouseClicked

    // Metode untuk menangani event klik pada tombol Simpan, yang akan menyimpan pesanan baru atau memperbarui pesanan yang sudah ada di database setelah konfirmasi dari pengguna
    private void btnSimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimpanMouseClicked
        // Cek apakah sedang dalam mode edit (selectedPesananId != -1) atau mode simpan baru (selectedPesananId == -1)
        // Jika selectedPesananId -1, berarti sedang dalam mode simpan baru, maka tampilkan konfirmasi untuk simpan
        if (selectedPesananId == -1) {
            // Validasi inputan sebelum menyimpan, jika validasi gagal (mengembalikan 0), maka tidak lanjut ke konfirmasi simpan
            if (validasiInput() == 0) {
                return;
            }
            int opsi = JOptionPane.showOptionDialog(
                    this, 
                    "Yakin Akan Menyimpan Pesanan?", 
                    "Konfirmasi", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    null, 
                    null);
            if(opsi == JOptionPane.YES_OPTION){
                simpanPesanan();
            }
        } 
        // Jika selectedPesananId tidak -1, berarti sedang dalam mode edit, maka tampilkan konfirmasi untuk update
        else {
            // Validasi inputan sebelum update, jika validasi gagal (mengembalikan 0), maka tidak lanjut ke konfirmasi update
            if (validasiInput() == 0) {
                return;
            }
            int opsi = JOptionPane.showOptionDialog(
                    this, 
                    "Yakin Akan Mengupdate Pesanan?", 
                    "Konfirmasi", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    null, 
                    null);
            if(opsi == JOptionPane.YES_OPTION){
                updatePesanan(selectedPesananId);
                selectedPesananId = -1;
                btnSimpan.setText("Simpan");
            }
        }
    }//GEN-LAST:event_btnSimpanMouseClicked

    // Metode untuk menangani event klik pada tombol Hapus, yang akan menghapus pesanan yang dipilih dari database setelah konfirmasi dari pengguna
    private void btnHapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHapusMouseClicked
        if (selectedPesananId != -1) {
            int opsi = JOptionPane.showOptionDialog(
                    this, 
                    "Yakin Akan Menghapus Pesanan?", 
                    "Konfirmasi", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    null, 
                    null);
            if(opsi == JOptionPane.YES_OPTION){
                hapusPesanan(selectedPesananId);
                selectedPesananId = -1;
                clearForm();
                btnSimpan.setText("Simpan");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih pesanan yang ingin dihapus dari tabel.");
        }
    }//GEN-LAST:event_btnHapusMouseClicked

    private void cbxBulanPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxBulanPemesananActionPerformed
        sesuaikanTanggal(cbxTahunPemesanan, cbxBulanPemesanan, cbxTanggalPemesanan);
    }//GEN-LAST:event_cbxBulanPemesananActionPerformed

    private void cbxBulanDeadlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxBulanDeadlineActionPerformed
        sesuaikanTanggal(cbxTahunDeadline, cbxBulanDeadline, cbxTanggalDeadline);
    }//GEN-LAST:event_cbxBulanDeadlineActionPerformed

    private void cbxTahunPemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTahunPemesananActionPerformed
        sesuaikanTanggal(cbxTahunPemesanan, cbxBulanPemesanan, cbxTanggalPemesanan);
    }//GEN-LAST:event_cbxTahunPemesananActionPerformed

    private void cbxTahunDeadlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTahunDeadlineActionPerformed
        sesuaikanTanggal(cbxTahunDeadline, cbxBulanDeadline, cbxTanggalDeadline);
    }//GEN-LAST:event_cbxTahunDeadlineActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrameFormPemesanan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup TipePemesanan;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cbxBulanDeadline;
    private javax.swing.JComboBox<String> cbxBulanPemesanan;
    private javax.swing.JComboBox<String> cbxKategoriProduk;
    private javax.swing.JComboBox<String> cbxTahunDeadline;
    private javax.swing.JComboBox<String> cbxTahunPemesanan;
    private javax.swing.JComboBox<String> cbxTanggalDeadline;
    private javax.swing.JComboBox<String> cbxTanggalPemesanan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbTipePemesananOffline;
    private javax.swing.JRadioButton rbTipePemesananOnline;
    private javax.swing.JTable tbPesanan;
    private javax.swing.JTextField tfHargaPerProduk;
    private javax.swing.JTextField tfJumlahProduk;
    private javax.swing.JTextField tfNamaProduk;
    // End of variables declaration//GEN-END:variables
}
