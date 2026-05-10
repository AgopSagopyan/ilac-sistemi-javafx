-- phpMyAdmin SQL Dump
-- Eczane İlaç Stok Takip Sistemi

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `eczane_sistemi`
--
CREATE DATABASE IF NOT EXISTS `eczane_sistemi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_turkish_ci;
USE `eczane_sistemi`;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `roller`
--

CREATE TABLE `roller` (
  `id` int(11) NOT NULL,
  `rol_adi` varchar(50) COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `roller`
--

INSERT INTO `roller` (`id`, `rol_adi`) VALUES
(1, 'Admin'),
(2, 'Eczaci'),
(3, 'Kullanici');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `eczaneler`
--

CREATE TABLE `eczaneler` (
  `id` int(11) NOT NULL,
  `eczane_adi` varchar(150) COLLATE utf8mb4_turkish_ci NOT NULL,
  `adres` text COLLATE utf8mb4_turkish_ci NOT NULL,
  `enlem` double DEFAULT NULL,
  `boylam` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `kullanicilar`
--

CREATE TABLE `kullanicilar` (
  `id` int(11) NOT NULL,
  `ad` varchar(100) COLLATE utf8mb4_turkish_ci NOT NULL,
  `soyad` varchar(100) COLLATE utf8mb4_turkish_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_turkish_ci NOT NULL,
  `sifre` varchar(255) COLLATE utf8mb4_turkish_ci NOT NULL,
  `rol_id` int(11) NOT NULL,
  `eczane_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `ilac_turleri`
--

CREATE TABLE `ilac_turleri` (
  `id` int(11) NOT NULL,
  `tur_adi` varchar(100) COLLATE utf8mb4_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Tablo döküm verisi `ilac_turleri`
--
INSERT INTO `ilac_turleri` (`id`, `tur_adi`) VALUES
(1, 'Ağrı Kesici'),
(2, 'Antibiyotik'),
(3, 'Vitamin'),
(4, 'Soğuk Algınlığı');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `ilaclar`
--

CREATE TABLE `ilaclar` (
  `id` int(11) NOT NULL,
  `ilac_adi` varchar(150) COLLATE utf8mb4_turkish_ci NOT NULL,
  `tur_id` int(11) NOT NULL,
  `fotograf_yolu` varchar(255) COLLATE utf8mb4_turkish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `stoklar`
--

CREATE TABLE `stoklar` (
  `id` int(11) NOT NULL,
  `eczane_id` int(11) NOT NULL,
  `ilac_id` int(11) NOT NULL,
  `miktar` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_turkish_ci;

--
-- Dökümü yapılmış tablolar için indeksler
--

ALTER TABLE `roller`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `eczaneler`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `kullanicilar`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `rol_id` (`rol_id`),
  ADD KEY `eczane_id` (`eczane_id`);

ALTER TABLE `ilac_turleri`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `ilaclar`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tur_id` (`tur_id`);

ALTER TABLE `stoklar`
  ADD PRIMARY KEY (`id`),
  ADD KEY `eczane_id` (`eczane_id`),
  ADD KEY `ilac_id` (`ilac_id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

ALTER TABLE `roller`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `eczaneler`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `kullanicilar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `ilac_turleri`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `ilaclar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `stoklar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

ALTER TABLE `kullanicilar`
  ADD CONSTRAINT `fk_kullanici_rol` FOREIGN KEY (`rol_id`) REFERENCES `roller` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_kullanici_eczane` FOREIGN KEY (`eczane_id`) REFERENCES `eczaneler` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `ilaclar`
  ADD CONSTRAINT `fk_ilac_tur` FOREIGN KEY (`tur_id`) REFERENCES `ilac_turleri` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `stoklar`
  ADD CONSTRAINT `fk_stok_eczane` FOREIGN KEY (`eczane_id`) REFERENCES `eczaneler` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_stok_ilac` FOREIGN KEY (`ilac_id`) REFERENCES `ilaclar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
