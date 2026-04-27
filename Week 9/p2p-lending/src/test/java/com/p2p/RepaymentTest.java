package com.p2p;

import com.p2p.domain.Borrower;
import com.p2p.domain.Lender;
import com.p2p.domain.Loan;
import com.p2p.service.FundingService;
import com.p2p.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test untuk fitur Repayment (pembayaran cicilan).
 * Mencakup TC-10 s/d TC-13.
 */
public class RepaymentTest {

    private LoanService loanService;
    private FundingService fundingService;

    @BeforeEach
    void setUp() {
        loanService = new LoanService();
        fundingService = new FundingService();
    }

    // Helper: membuat loan yang sudah ACTIVE
    private Loan createActiveLoan(BigDecimal amount) {
        Borrower borrower = new Borrower(true, 700);
        Loan loan = loanService.createLoan(borrower, amount);

        Lender lender = new Lender("Investor", amount.add(BigDecimal.valueOf(1000)));
        fundingService.fundLoan(lender, loan);
        fundingService.activateLoan(loan);

        return loan;
    }

    // ============================================================
    // TC-10: shouldAllowRepaymentWhenLoanActive
    // ============================================================

    /**
     * TC-10
     * Skenario : Loan ACTIVE, borrower melakukan pembayaran
     * Expected : Pembayaran diterima (tidak melempar exception)
     */
    @Test
    @DisplayName("TC-10: Harus menerima pembayaran jika loan ACTIVE")
    void shouldAllowRepaymentWhenLoanActive() {

        // ARRANGE
        Loan loan = createActiveLoan(BigDecimal.valueOf(5000));
        BigDecimal payment = BigDecimal.valueOf(1000);

        // ACT + ASSERT (tidak boleh melempar exception)
        assertDoesNotThrow(() -> {
            loan.repay(payment);
        });
    }

    // ============================================================
    // TC-11: shouldRejectRepaymentWhenAmountInvalid
    // ============================================================

    /**
     * TC-11
     * Skenario : Borrower mencoba membayar dengan amount <= 0
     * Expected : Exception dilempar
     */
    @Test
    @DisplayName("TC-11: Harus menolak pembayaran jika amount <= 0")
    void shouldRejectRepaymentWhenAmountInvalid() {

        // ARRANGE
        Loan loan = createActiveLoan(BigDecimal.valueOf(5000));

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            loan.repay(BigDecimal.ZERO);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            loan.repay(BigDecimal.valueOf(-100));
        });
    }

    // ============================================================
    // TC-12: shouldReduceOutstandingWhenRepay
    // ============================================================

    /**
     * TC-12
     * Skenario : Loan ACTIVE, borrower bayar sebagian
     * Expected : Outstanding berkurang sesuai jumlah bayar
     */
    @Test
    @DisplayName("TC-12: Outstanding harus berkurang setelah pembayaran")
    void shouldReduceOutstandingWhenRepay() {

        // ARRANGE
        Loan loan = createActiveLoan(BigDecimal.valueOf(5000));
        BigDecimal payment = BigDecimal.valueOf(2000);

        // ACT
        loan.repay(payment);

        // ASSERT
        assertEquals(BigDecimal.valueOf(3000), loan.getOutstanding(),
                "Outstanding harus berkurang dari 5000 menjadi 3000 setelah bayar 2000");
    }

    // ============================================================
    // TC-13: shouldCompleteLoanWhenOutstandingZero
    // ============================================================

    /**
     * TC-13
     * Skenario : Loan ACTIVE, borrower membayar lunas (full payment)
     * Expected : Status = COMPLETED, outstanding = 0
     */
    @Test
    @DisplayName("TC-13: Loan harus COMPLETED jika dibayar lunas")
    void shouldCompleteLoanWhenOutstandingZero() {

        // ARRANGE
        Loan loan = createActiveLoan(BigDecimal.valueOf(5000));
        BigDecimal fullPayment = BigDecimal.valueOf(5000); // bayar lunas

        // ACT
        loan.repay(fullPayment);

        // ASSERT
        assertEquals(Loan.Status.COMPLETED, loan.getStatus(),
                "Loan harus COMPLETED setelah dibayar lunas");
        assertEquals(BigDecimal.ZERO, loan.getOutstanding(),
                "Outstanding harus 0 setelah dibayar lunas");
    }

    /**
     * TC-13 (lanjutan - cicilan bertahap hingga lunas)
     * Skenario : Bayar cicilan 3x hingga outstanding = 0
     * Expected : Status = COMPLETED
     */
    @Test
    @DisplayName("TC-13: Loan harus COMPLETED setelah cicilan bertahap hingga lunas")
    void shouldCompleteLoanAfterMultipleRepayments() {

        // ARRANGE
        Loan loan = createActiveLoan(BigDecimal.valueOf(3000));

        // ACT - bayar cicilan bertahap
        loan.repay(BigDecimal.valueOf(1000)); // outstanding: 2000
        loan.repay(BigDecimal.valueOf(1000)); // outstanding: 1000
        loan.repay(BigDecimal.valueOf(1000)); // outstanding: 0 -> COMPLETED

        // ASSERT
        assertEquals(Loan.Status.COMPLETED, loan.getStatus(),
                "Loan harus COMPLETED setelah semua cicilan terbayar");
        assertEquals(BigDecimal.ZERO, loan.getOutstanding());
    }
}
