CREATE DATABASE IF NOT EXISTS pharmacy_db;
USE pharmacy_db;

-- Table for Medicines
CREATE TABLE IF NOT EXISTS ilaclar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ad VARCHAR(255) NOT NULL,
    etken_madde VARCHAR(255) NOT NULL,
    aciklama TEXT
);

-- Table for Pharmacies
CREATE TABLE IF NOT EXISTS eczaneler (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ad VARCHAR(255) NOT NULL,
    adres TEXT,
    koordinat_x DOUBLE,
    koordinat_y DOUBLE
);

-- Table for Pharmacists (Users)
CREATE TABLE IF NOT EXISTS eczacilar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kullanici_adi VARCHAR(100) UNIQUE NOT NULL,
    sifre VARCHAR(255) NOT NULL,
    eczane_id INT,
    FOREIGN KEY (eczane_id) REFERENCES eczaneler(id)
);

-- Table for Stock
CREATE TABLE IF NOT EXISTS stoklar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    eczane_id INT,
    ilac_id INT,
    adet INT DEFAULT 0,
    aktif_mi BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (eczane_id) REFERENCES eczaneler(id),
    FOREIGN KEY (ilac_id) REFERENCES ilaclar(id)
);

-- Insert Dummy Data
INSERT INTO ilaclar (ad, etken_madde, aciklama) VALUES
('Parol', 'Paracetamol', 'Ağrı kesici ve ateş düşürücü'),
('Minoset', 'Paracetamol', 'Ağrı kesici ve ateş düşürücü'),
('Aspirin', 'Asetilsalisilik asit', 'Ağrı kesici'),
('Augmentin', 'Amoksisilin', 'Antibiyotik'),
('Klamoks', 'Amoksisilin', 'Antibiyotik');

INSERT INTO eczaneler (ad, adres, koordinat_x, koordinat_y) VALUES
('Merkez Eczanesi', 'Atatürk Caddesi No:1', 10.0, 20.0),
('Şifa Eczanesi', 'Cumhuriyet Sokak No:5', 12.0, 22.0),
('Güneş Eczanesi', 'Güneşli Mah. No:10', 50.0, 60.0);

INSERT INTO eczacilar (kullanici_adi, sifre, eczane_id) VALUES
('admin', '1234', 1),
('sifa_admin', '1234', 2);

INSERT INTO stoklar (eczane_id, ilac_id, adet, aktif_mi) VALUES
(1, 1, 50, TRUE),
(1, 4, 10, TRUE),
(2, 2, 30, TRUE),
(2, 4, 5, TRUE),
(3, 3, 100, TRUE);
