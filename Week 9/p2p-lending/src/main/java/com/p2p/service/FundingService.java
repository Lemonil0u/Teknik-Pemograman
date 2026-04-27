package com.p2p.service;

import com.p2p.domain.Lender;
import com.p2p.domain.Loan;
import java.math.BigDecimal;

/**
 * Service class untuk mengelola proses pendanaan pinjaman (Funding).
 *
 * Business Rules:
 * - Lender dapat mendanai loan yang sudah APPROVED
 * - Lender tidak boleh mendanai jika saldo tidak cukup
 * - Loan menjadi FUNDED jika dana terpenuhi
 */
public class FundingService {

    /**
     * Lender mendanai loan.
     *
     * @param lender lender yang mendanai
     * @param loan   loan yang didanai
     * @throws IllegalArgumentException jika saldo lender tidak mencukupi
     * @throws IllegalStateException    jika loan belum APPROVED
     */
    public void fundLoan(Lender lender, Loan loan) {

        // Validasi: loan harus APPROVED sebelum bisa didanai
        if (loan.getStatus() != Loan.Status.APPROVED) {
            throw new IllegalStateException("Loan harus berstatus APPROVED sebelum dapat didanai");
        }

        BigDecimal loanAmount = loan.getAmount();

        // Validasi: saldo lender harus mencukupi
        if (!lender.hasSufficientBalance(loanAmount)) {
            throw new IllegalArgumentException("Saldo lender tidak mencukupi untuk mendanai loan ini");
        }

        // Kurangi saldo lender
        lender.deductBalance(loanAmount);

        // Tandai loan sebagai FUNDED
        loan.markAsFunded();
    }

    /**
     * Mengaktifkan (disbursement) loan yang sudah FUNDED.
     *
     * @param loan loan yang akan diaktifkan
     * @throws IllegalStateException jika loan belum FUNDED
     */
    public void activateLoan(Loan loan) {
        loan.activate(); // business logic ada di domain Loan
    }
}
