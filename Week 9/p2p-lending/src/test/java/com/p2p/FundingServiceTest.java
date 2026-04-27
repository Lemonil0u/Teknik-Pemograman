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
 * Unit Test untuk FundingService menggunakan TDD.
 * Mencakup TC-05 s/d TC-09 (Funding & Disbursement).
 */
public class FundingServiceTest {

    private LoanService loanService;
    private FundingService fundingService;

    @BeforeEach
    void setUp() {
        loanService = new LoanService();
        fundingService = new FundingService();
    }

    // Helper: membuat loan yang sudah APPROVED
    private Loan createApprovedLoan(BigDecimal amount) {
        Borrower borrower = new Borrower(true, 700);
        return loanService.createLoan(borrower, amount);
    }

    // ============================================================
    // TC-05: shouldAllowFundingWhenBalanceSufficient
    // ============================================================

    /**
     * TC-05
     * Skenario : Lender memiliki saldo cukup, funding dilakukan
     * Expected : Funding berhasil, loan menjadi FUNDED
     */
    @Test
    @DisplayName("TC-05: Harus berhasil mendanai loan jika saldo lender mencukupi")
    void shouldAllowFundingWhenBalanceSufficient() {

        // ARRANGE
        Loan loan = createApprovedLoan(BigDecimal.valueOf(5000));
        Lender lender = new Lender("Budi", BigDecimal.valueOf(10000)); // saldo cukup

        // ACT
        fundingService.fundLoan(lender, loan);

        // ASSERT
        assertEquals(Loan.Status.FUNDED, loan.getStatus(),
                "Loan harus FUNDED setelah berhasil didanai");
        assertEquals(BigDecimal.valueOf(5000), lender.getBalance(),
                "Saldo lender harus berkurang setelah funding");
    }

    // ============================================================
    // TC-06: shouldRejectFundingWhenBalanceNotEnough
    // ============================================================

    /**
     * TC-06
     * Skenario : Lender memiliki saldo kurang, funding dilakukan
     * Expected : Exception dilempar
     */
    @Test
    @DisplayName("TC-06: Harus menolak funding jika saldo lender tidak cukup")
    void shouldRejectFundingWhenBalanceNotEnough() {

        // ARRANGE
        Loan loan = createApprovedLoan(BigDecimal.valueOf(5000));
        Lender lender = new Lender("Ani", BigDecimal.valueOf(1000)); // saldo tidak cukup

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            fundingService.fundLoan(lender, loan);
        });
    }

    // ============================================================
    // TC-07: shouldMarkLoanAsFundedWhenFullyFunded
    // ============================================================

    /**
     * TC-07
     * Skenario : Loan didanai penuh
     * Expected : Status = FUNDED
     */
    @Test
    @DisplayName("TC-07: Loan harus berstatus FUNDED jika didanai penuh")
    void shouldMarkLoanAsFundedWhenFullyFunded() {

        // ARRANGE
        Loan loan = createApprovedLoan(BigDecimal.valueOf(3000));
        Lender lender = new Lender("Citra", BigDecimal.valueOf(5000));

        // ACT
        fundingService.fundLoan(lender, loan);

        // ASSERT
        assertEquals(Loan.Status.FUNDED, loan.getStatus());
    }

    // ============================================================
    // TC-08: shouldNotActivateLoanIfNotFunded
    // ============================================================

    /**
     * TC-08
     * Skenario : Loan belum FUNDED, coba diaktifkan
     * Expected : Exception dilempar
     */
    @Test
    @DisplayName("TC-08: Tidak boleh mengaktifkan loan yang belum FUNDED")
    void shouldNotActivateLoanIfNotFunded() {

        // ARRANGE
        Loan loan = createApprovedLoan(BigDecimal.valueOf(3000));
        // loan masih APPROVED, belum FUNDED

        // ACT + ASSERT
        assertThrows(IllegalStateException.class, () -> {
            fundingService.activateLoan(loan);
        });
    }

    // ============================================================
    // TC-09: shouldActivateLoanWhenFunded
    // ============================================================

    /**
     * TC-09
     * Skenario : Loan berstatus FUNDED, kemudian diaktifkan
     * Expected : Status = ACTIVE
     */
    @Test
    @DisplayName("TC-09: Loan harus ACTIVE setelah diaktifkan dari status FUNDED")
    void shouldActivateLoanWhenFunded() {

        // ARRANGE
        Loan loan = createApprovedLoan(BigDecimal.valueOf(3000));
        Lender lender = new Lender("Doni", BigDecimal.valueOf(5000));
        fundingService.fundLoan(lender, loan); // loan menjadi FUNDED

        // ACT
        fundingService.activateLoan(loan);

        // ASSERT
        assertEquals(Loan.Status.ACTIVE, loan.getStatus(),
                "Loan harus ACTIVE setelah diaktifkan");
    }
}
