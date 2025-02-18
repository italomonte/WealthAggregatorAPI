package dev.italomonte.agregadordeinvestimentos.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_accounts")
public class Account {


    @JoinColumn(name = "user_id")
    @ManyToOne // varias contas para um usário
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
}
