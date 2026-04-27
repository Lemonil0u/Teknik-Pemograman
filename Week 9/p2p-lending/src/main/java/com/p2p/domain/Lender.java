package com.p2p.domain;

import java.math.BigDecimal;

/**
 * Domain class yang merepresentasikan pemberi pinjaman (lender) dalam sistem P2P Lending.
 * Lender dapat mendanai loan yang sudah disetujui.
 */
public class Lender {

    private String name;
    private BigDecimal balance; // saldo yang dimiliki lender

    /**
     * Constructor untuk inisialisasi data lender.
     *
     * @param name    nama lender
     * @param balance saldo awal lender
     */
    public Lender(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Mengecek apakah lender memiliki saldo yang cukup untuk mendanai sejumlah amount.
     *
     * @param amount jumlah yang akan didanai
     * @return true jika saldo mencukupi
     */
    public boolean hasSufficientBalance(BigDecimal amount) {
        return this.balance.compareTo(amount) >= 0;
    }

    /**
     * Mengurangi saldo lender setelah melakukan pendanaan.
     *
     * @param amount jumlah yang didanai
     * @throws IllegalArgumentException jika saldo tidak mencukupi
     */
    public void deductBalance(BigDecimal amount) {
        if (!hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Saldo lender tidak mencukupi untuk mendanai loan ini");
        }
        this.balance = this.balance.subtract(amount);
    }
}
