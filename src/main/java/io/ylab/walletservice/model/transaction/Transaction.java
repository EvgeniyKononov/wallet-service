package io.ylab.walletservice.model.transaction;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private Long id;
    private Double sum;

    private LocalDateTime dateTime;

    public Transaction(Long id, Double sum) {
        this.id = id;
        this.sum = sum;
        this.dateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(sum, that.sum) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum, dateTime);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", sum=" + sum +
                ", dateTime=" + dateTime +
                '}';
    }
}
