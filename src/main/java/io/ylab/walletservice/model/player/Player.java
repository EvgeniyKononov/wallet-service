package io.ylab.walletservice.model.player;

import io.ylab.walletservice.model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private Long id;
    private String login;
    private Double account;
    private List<Transaction> transactions;

    public Player(Long playerId, String login) {
        this.id = playerId;
        this.login = login;
        this.account = 0.0;
        this.transactions = new ArrayList<>();
    }

    public Player(Long id, String login, Double account, List<Transaction> transactions) {
        this.id = id;
        this.login = login;
        this.account = account;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double funds) {
        this.account = funds;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(login, player.login)
                && Objects.equals(account, player.account) && Objects.equals(transactions, player.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, account, transactions);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", account=" + account +
                ", transactions=" + transactions +
                '}';
    }
}
