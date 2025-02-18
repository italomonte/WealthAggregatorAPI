package dev.italomonte.agregadordeinvestimentos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_account_stock")
public class AccountStock {

    @Embedded
    private AccountStockId id;

    @ManyToOne
    @MapsId("accountId") // indica qual campo da account vai ser usado para fazer o relactionamento
    @JoinColumn(name= "account_id") // indica qual vai ser a chave para gerar a foreng key
    private Account account;

    @ManyToOne
    @MapsId("stockId") // indica qual Atributo da account vai ser usado para fazer o relactionamento
    @JoinColumn(name= "stock_id") // indica qual vai ser a chave para gerar a foreng key
    private Stock stock;

    @Column(name = "quantity")
    private Integer quantity;

    public AccountStock() {
    }

    public AccountStock(AccountStockId id, Account account, Stock stock, Integer quantity) {
        this.id = id;
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
    }

    public AccountStockId getId() {
        return id;
    }

    public void setId(AccountStockId id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
