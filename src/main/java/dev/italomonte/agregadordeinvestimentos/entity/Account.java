package dev.italomonte.agregadordeinvestimentos.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_accounts")
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.UUID) // Quando persistir se o campo n estiver preenchido ele gera um campo de id aleatório
    private UUID account_id;

    @OneToOne
    @JoinColumn(name = "account")
    @PrimaryKeyJoinColumn
    private BillingAddress billingAddress;

    @ManyToOne // varias contas para um usário
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "description")
    private String description;

    public Account() {
    }

    public Account(String description, UUID account_id) {
        this.description = description;
        this.account_id = account_id;
    }

    public UUID getAccount_id() {
        return account_id;
    }

    public void setAccount_id(UUID account_id) {
        this.account_id = account_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
