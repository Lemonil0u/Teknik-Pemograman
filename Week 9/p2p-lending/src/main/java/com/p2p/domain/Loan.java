package com.p2p.domain;

import java.math.BigDecimal;

/**
 * Domain class yang merepresentasikan pinjaman (loan) dalam sistem P2P Lending.
 * Loan memiliki siklus hidup: PENDING -> APPROVED/REJECTED -> FUNDED -> ACTIVE -> COMPLETED
 */
public class Loan {

    /**
     * Enum untuk status loan
     */
    public enum Status {
        PENDING,    // Status awal saat loan dibuat
        APPROVED,   // Loan disetujui berdasarkan credit score
        REJECTED,   // Loan ditolak karena credit score rendah
        FUNDED,     // Loan sudah terpenuhi pendanaannya
        ACTIVE,     // Loan sudah dicairkan (disbursed)
        COMPLETED   // Loan sudah dilunasi sepenuhnya
    }

    private Status status;
    private BigDecimal amount;          // jumlah pinjaman
    private BigDecimal outstanding;     // sisa tagihan

    /**
     * Saat loan dibuat, status awal adalah PENDING.
     */
    public Loan() {
        this.status = Status.PENDING;
        this.outstanding = BigDecimal.ZERO;
    }

    // ========================
    // GETTERS & SETTERS
    // ========================

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        this.outstanding = amount; // outstanding = jumlah pinjaman saat dibuat
    }

    public BigDecimal getOutstanding() {
        return outstanding;
    }

    // ========================
    // DOMAIN BEHAVIOR
    // ========================

    /**
     * Menyetujui loan (credit score memenuhi threshold).
     */
    public void approve() {
        this.status = Status.APPROVED;
    }

    /**
     * Menolak loan (credit score di bawah threshold).
     */
    public void reject() {
        this.status = Status.REJECTED;
    }

    /**
     * Menandai loan sebagai sudah terdanai sepenuhnya.
     */
    public void markAsFunded() {
        this.status = Status.FUNDED;
    }

    /**
     * Mengaktifkan (mencairkan) loan.
     * Hanya bisa dilakukan jika status FUNDED.
     *
     * @throws IllegalStateException jika loan belum FUNDED
     */
    public void activate() {
        if (this.status != Status.FUNDED) {
            throw new IllegalStateException("Loan harus berstatus FUNDED sebelum dapat diaktifkan");
        }
        this.status = Status.ACTIVE;
    }

    /**
     * Memproses pembayaran cicilan dari borrower.
     * Hanya bisa dilakukan jika loan ACTIVE.
     *
     * @param paymentAmount jumlah pembayaran
     * @throws IllegalStateException    jika loan tidak ACTIVE
     * @throws IllegalArgumentException jika jumlah pembayaran <= 0
     */
    public void repay(BigDecimal paymentAmount) {
        if (this.status != Status.ACTIVE) {
            throw new IllegalStateException("Pembayaran hanya bisa dilakukan pada loan yang ACTIVE");
        }
        if (paymentAmount == null || paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Jumlah pembayaran harus lebih dari 0");
        }

        this.outstanding = this.outstanding.subtract(paymentAmount);

        // Jika outstanding <= 0, loan dinyatakan COMPLETED
        if (this.outstanding.compareTo(BigDecimal.ZERO) <= 0) {
            this.outstanding = BigDecimal.ZERO;
            this.status = Status.COMPLETED;
        }
    }
}
