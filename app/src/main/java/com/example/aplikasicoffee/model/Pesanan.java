package com.example.aplikasicoffee.model;

public class Pesanan {
    private String id, namaCoffee, alamat, jumlahPesanan, harga, keterangan;

    public Pesanan() {

    }

    public Pesanan(String namaCoffee, String alamat, String jumlahPesanan, String harga, String keterangan) {
        this.namaCoffee = namaCoffee;
        this.alamat = alamat;
        this.jumlahPesanan = jumlahPesanan;
        this.harga = harga;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaCoffee() {
        return namaCoffee;
    }

    public void setNamaCoffee(String namaCoffee) {
        this.namaCoffee = namaCoffee;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getJumlahPesanan() {
        return jumlahPesanan;
    }

    public void setJumlahPesanan(String jumlahPesanan) {
        this.jumlahPesanan = jumlahPesanan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
