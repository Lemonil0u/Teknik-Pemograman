package com.p2p.domain;

/**
 * Domain class yang merepresentasikan peminjam (borrower) dalam sistem P2P Lending.
 * Borrower harus melewati proses KYC (Know Your Customer) sebelum dapat mengajukan pinjaman.
 */
public class Borrower {

    // Status verifikasi KYC borrower
    private boolean verified;

    // Nilai credit score borrower (threshold: 600)
    private int creditScore;

    /**
     * Constructor untuk inisialisasi data borrower.
     *
     * @param verified    status verifikasi KYC
     * @param creditScore nilai credit score
     */
    public Borrower(boolean verified, int creditScore) {
        this.verified = verified;
        this.creditScore = creditScore;
    }

    /**
     * Getter untuk mengecek apakah borrower sudah terverifikasi KYC.
     *
     * @return true jika sudah verified, false jika belum
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Getter untuk mengambil nilai credit score borrower.
     *
     * @return nilai credit score
     */
    public int getCreditScore() {
        return creditScore;
    }

    // ========================
    // DOMAIN BEHAVIOR (NEW)
    // ========================

    /**
     * Mengecek apakah borrower memenuhi syarat untuk mengajukan pinjaman.
     * Borrower harus sudah terverifikasi KYC.
     *
     * @return true jika bisa mengajukan loan, false jika tidak
     */
    public boolean canApplyLoan() {
        return verified;
    }
}
