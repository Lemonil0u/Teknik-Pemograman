package com.p2p;

import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;
import com.p2p.service.LoanService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test untuk LoanService menggunakan TDD (Test-Driven Development).
 * Mencakup TC-01 s/d TC-04 sesuai spesifikasi Praktikum 9.
 *
 * Pendekatan: RED -> GREEN -> REFACTOR (Fowler Style)
 */
public class LoanServiceTest {

    private static final Logger logger = LogManager.getLogger(LoanServiceTest.class);

    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanService = new LoanService();
    }

    // ============================================================
    // TC-01: shouldRejectLoanWhenBorrowerNotVerified
    // ============================================================
    @Test
    @DisplayName("TC-01: Harus menolak loan jika borrower belum terverifikasi KYC")
    void shouldRejectLoanWhenBorrowerNotVerified() {
        logger.info("====== TC-01: shouldRejectLoanWhenBorrowerNotVerified ======");

        // ARRANGE
        logger.debug("[ARRANGE] Membuat borrower belum KYC (verified=false, creditScore=700)");
        Borrower borrower = new Borrower(false, 700);
        LoanService service = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);
        logger.debug("[ARRANGE] Amount pinjaman: {}", amount);

        // ACT + ASSERT
        logger.debug("[ACT] Borrower mencoba mengajukan loan");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createLoan(borrower, amount);
        });
        logger.warn("[ASSERT] Exception ditangkap: {}", exception.getMessage());
        logger.info("[RESULT] TC-01 PASSED - Sistem berhasil menolak loan dari borrower yang belum terverifikasi KYC");
    }

    // ============================================================
    // TC-02: shouldRejectLoanWhenAmountIsZeroOrNegative
    // ============================================================
    @Test
    @DisplayName("TC-02: Harus menolak loan jika amount adalah nol")
    void shouldRejectLoanWhenAmountIsZero() {
        logger.info("====== TC-02a: shouldRejectLoanWhenAmountIsZero ======");

        // ARRANGE
        logger.debug("[ARRANGE] Borrower valid (verified=true, creditScore=700)");
        Borrower borrower = new Borrower(true, 700);
        BigDecimal amount = BigDecimal.ZERO;
        logger.debug("[ARRANGE] Amount: {} (tidak valid)", amount);

        // ACT + ASSERT
        logger.debug("[ACT] Mengajukan loan dengan amount = 0");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
        logger.warn("[ASSERT] Exception ditangkap: {}", exception.getMessage());
        logger.info("[RESULT] TC-02a PASSED - Sistem berhasil menolak loan karena jumlah pinjaman bernilai nol");
    }

    @Test
    @DisplayName("TC-02: Harus menolak loan jika amount negatif")
    void shouldRejectLoanWhenAmountIsNegative() {
        logger.info("====== TC-02b: shouldRejectLoanWhenAmountIsNegative ======");

        // ARRANGE
        logger.debug("[ARRANGE] Borrower valid (verified=true, creditScore=700)");
        Borrower borrower = new Borrower(true, 700);
        BigDecimal amount = BigDecimal.valueOf(-500);
        logger.debug("[ARRANGE] Amount: {} (negatif, tidak valid)", amount);

        // ACT + ASSERT
        logger.debug("[ACT] Mengajukan loan dengan amount negatif");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
        logger.warn("[ASSERT] Exception ditangkap: {}", exception.getMessage());
        logger.info("[RESULT] TC-02b PASSED - Sistem berhasil menolak loan karena jumlah pinjaman bernilai negatif");
    }

    // ============================================================
    // TC-03: shouldApproveLoanWhenCreditScoreHigh
    // ============================================================
    @Test
    @DisplayName("TC-03: Harus menyetujui loan jika credit score tinggi (>= 600)")
    void shouldApproveLoanWhenCreditScoreHigh() {
        logger.info("====== TC-03: shouldApproveLoanWhenCreditScoreHigh ======");

        // ARRANGE
        int creditScore = 700;
        BigDecimal amount = BigDecimal.valueOf(5000);
        logger.debug("[ARRANGE] Borrower valid, credit score: {}", creditScore);
        Borrower borrower = new Borrower(true, creditScore);
        logger.debug("[ARRANGE] Amount pinjaman: {}", amount);

        // ACT
        logger.debug("[ACT] Mengajukan loan");
        Loan loan = loanService.createLoan(borrower, amount);
        logger.debug("[ACT] Loan diproses, status: {}", loan.getStatus());

        // ASSERT
        logger.debug("[ASSERT] Memverifikasi status loan sama dengan APPROVED");
        assertEquals(Loan.Status.APPROVED, loan.getStatus(), "Loan harus APPROVED jika credit score >= 600");
        logger.info("[RESULT] TC-03 PASSED - Sistem berhasil menyetujui loan dengan credit score {} yang memenuhi threshold", creditScore);
    }

    @Test
    @DisplayName("TC-03: Harus menyetujui loan jika credit score tepat di threshold (600)")
    void shouldApproveLoanWhenCreditScoreExactlyAtThreshold() {
        logger.info("====== TC-03 Edge Case: credit score tepat di threshold (600) ======");

        // ARRANGE
        int creditScore = 600;
        logger.debug("[ARRANGE] Borrower dengan credit score tepat threshold: {}", creditScore);
        Borrower borrower = new Borrower(true, creditScore);
        BigDecimal amount = BigDecimal.valueOf(3000);
        logger.debug("[ARRANGE] Amount pinjaman: {}", amount);

        // ACT
        logger.debug("[ACT] Mengajukan loan");
        Loan loan = loanService.createLoan(borrower, amount);
        logger.debug("[ACT] Loan diproses, status: {}", loan.getStatus());

        // ASSERT
        assertEquals(Loan.Status.APPROVED, loan.getStatus(), "Loan harus APPROVED jika credit score tepat 600");
        logger.info("[RESULT] TC-03 Edge Case PASSED - Sistem berhasil menyetujui loan dengan credit score tepat pada nilai threshold {}", creditScore);
    }

    // ============================================================
    // TC-04: shouldRejectLoanWhenCreditScoreLow
    // ============================================================
    @Test
    @DisplayName("TC-04: Harus menolak loan jika credit score rendah (< 600)")
    void shouldRejectLoanWhenCreditScoreLow() {
        logger.info("====== TC-04: shouldRejectLoanWhenCreditScoreLow ======");

        // ARRANGE
        int creditScore = 400;
        BigDecimal amount = BigDecimal.valueOf(5000);
        logger.debug("[ARRANGE] Borrower verified, credit score rendah: {}", creditScore);
        Borrower borrower = new Borrower(true, creditScore);
        logger.debug("[ARRANGE] Amount pinjaman: {}", amount);

        // ACT
        logger.debug("[ACT] Mengajukan loan");
        Loan loan = loanService.createLoan(borrower, amount);
        logger.debug("[ACT] Loan diproses, status: {}", loan.getStatus());

        // ASSERT
        logger.debug("[ASSERT] Memverifikasi status loan sama dengan REJECTED");
        assertEquals(Loan.Status.REJECTED, loan.getStatus(), "Loan harus REJECTED jika credit score < 600");
        logger.info("[RESULT] TC-04 PASSED - Sistem berhasil menolak loan karena credit score {} berada di bawah threshold", creditScore);
    }

    @Test
    @DisplayName("TC-04: Harus menolak loan jika credit score 599 (tepat 1 di bawah threshold)")
    void shouldRejectLoanWhenCreditScoreJustBelowThreshold() {
        logger.info("====== TC-04 Edge Case: credit score tepat 1 di bawah threshold (599) ======");

        // ARRANGE
        int creditScore = 599;
        logger.debug("[ARRANGE] Borrower dengan credit score: {}", creditScore);
        Borrower borrower = new Borrower(true, creditScore);
        BigDecimal amount = BigDecimal.valueOf(3000);
        logger.debug("[ARRANGE] Amount pinjaman: {}", amount);

        // ACT
        logger.debug("[ACT] Mengajukan loan");
        Loan loan = loanService.createLoan(borrower, amount);
        logger.debug("[ACT] Loan diproses, status: {}", loan.getStatus());

        // ASSERT
        assertEquals(Loan.Status.REJECTED, loan.getStatus(), "Loan harus REJECTED jika credit score 599");
        logger.info("[RESULT] TC-04 Edge Case PASSED - Sistem berhasil menolak loan karena credit score {} berada tepat di bawah nilai threshold", creditScore);
    }
}
