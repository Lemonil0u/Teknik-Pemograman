package com.p2p.service;

import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;
import java.math.BigDecimal;

/**
 * Service class untuk mengelola proses pengajuan pinjaman (Loan Creation).
 * Menerapkan pendekatan TDD (Test-Driven Development) dengan refactoring Fowler Style.
 *
 * Alur: RED -> GREEN -> REFACTOR
 */
public class LoanService {

    // Threshold credit score untuk persetujuan loan
    private static final int CREDIT_SCORE_THRESHOLD = 600;

    /**
     * Membuat loan baru berdasarkan data borrower dan jumlah pinjaman.
     *
     * Business Rules:
     * 1. Borrower harus terverifikasi KYC
     * 2. Amount harus lebih dari 0
     * 3. Jika credit score >= 600 -> APPROVED
     * 4. Jika credit score < 600  -> REJECTED
     *
     * @param borrower borrower yang mengajukan pinjaman
     * @param amount   jumlah pinjaman yang diajukan
     * @return Loan yang sudah diproses (APPROVED / REJECTED)
     * @throws IllegalArgumentException jika borrower belum verified atau amount tidak valid
     */
    public Loan createLoan(Borrower borrower, BigDecimal amount) {

        // ========================
        // VALIDASI (delegasi ke domain)
        // ========================
        validateBorrower(borrower);
        validateAmount(amount);

        // ========================
        // CREATE LOAN (domain object)
        // ========================
        Loan loan = new Loan();
        loan.setAmount(amount);

        // ========================
        // BUSINESS ACTION (domain behavior)
        // ========================
        if (borrower.getCreditScore() >= CREDIT_SCORE_THRESHOLD) {
            loan.approve();
        } else {
            loan.reject();
        }

        return loan;
    }

    // ========================
    // PRIVATE VALIDATION METHOD
    // ========================

    /**
     * Memvalidasi borrower sebelum proses loan.
     * Fowler: Extract Method + Move Method
     *
     * @param borrower borrower yang akan divalidasi
     * @throws IllegalArgumentException jika borrower belum terverifikasi KYC
     */
    private void validateBorrower(Borrower borrower) {
        if (!borrower.canApplyLoan()) {
            throw new IllegalArgumentException("Borrower not verified");
        }
    }

    /**
     * Memvalidasi jumlah pinjaman.
     *
     * @param amount jumlah pinjaman
     * @throws IllegalArgumentException jika amount null atau <= 0
     */
    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Jumlah pinjaman harus lebih dari 0");
        }
    }
}
