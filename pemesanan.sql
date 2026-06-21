-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 21, 2026 at 09:46 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pemesanan`
--

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE `pesanan` (
  `id` int NOT NULL,
  `produk` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `jumlah` int NOT NULL,
  `tanggal` date NOT NULL,
  `tipe` enum('online','offline') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `deadline` date NOT NULL,
  `harga` decimal(16,2) NOT NULL,
  `kategori` enum('elektronik','fashion','makanan','peralatan') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pesanan`
--

INSERT INTO `pesanan` (`id`, `produk`, `jumlah`, `tanggal`, `tipe`, `deadline`, `harga`, `kategori`) VALUES
(1, 'smartphone samsung a15', 2, '2026-05-01', 'online', '2026-05-10', '2500000.00', 'elektronik'),
(2, 'laptop asus vivobook', 1, '2026-05-02', 'offline', '2026-05-12', '7500000.00', 'elektronik'),
(3, 'headset logitech g435', 3, '2026-05-03', 'online', '2026-05-08', '850000.00', 'elektronik'),
(4, 'printer canon g2020', 1, '2026-05-04', 'offline', '2026-05-15', '2200000.00', 'peralatan'),
(5, 'smartwatch xiaomi band 8', 4, '2026-05-05', 'online', '2026-05-09', '550000.00', 'elektronik'),
(6, 'tablet ipad mini', 2, '2026-05-06', 'offline', '2026-05-16', '6500000.00', 'elektronik'),
(7, 'kamera sony alpha 7', 1, '2026-04-07', 'online', '2026-04-20', '15000000.00', 'fashion'),
(8, 'speaker jbl flip 6', 5, '2026-05-08', 'offline', '2026-05-18', '1800000.00', 'elektronik'),
(9, 'keyboard mechanical rexus', 3, '2026-05-10', 'offline', '2026-05-14', '450000.00', 'elektronik'),
(10, 'drone dji mini 3', 1, '2026-05-14', 'offline', '2026-05-25', '8500000.00', 'elektronik');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pesanan`
--
ALTER TABLE `pesanan`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
