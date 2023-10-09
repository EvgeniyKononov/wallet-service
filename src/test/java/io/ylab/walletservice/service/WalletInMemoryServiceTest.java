package io.ylab.walletservice.service;

import io.ylab.walletservice.model.player.Player;
import io.ylab.walletservice.model.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WalletInMemoryServiceTest {

    private WalletInMemoryService service;
    private Player expectedPlayer;


    @BeforeEach
    void setUp() {
        service = new WalletInMemoryService();
        expectedPlayer = new Player(1L, "test", 0.0, new ArrayList<>());
    }

    @Test
    void registerPlayer_whenCreatePlayer_thenReturnNewPlayer() {
        Player actualPlayer = service.registerPlayer("test");
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    void registerPlayer_whenCreatePlayerWithSameLogin_thenThrowException() {
        service.registerPlayer("test");
        assertThrows(IllegalArgumentException.class, () -> service.registerPlayer("test"));
    }

    @Test
    void findPlayer_whenTryingToFindNotExistingPlayer_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> service.findPlayer("test"));
    }

    @Test
    void findPlayer_whenCreatePlayer_thenFoundCreatedPlayer() {
        service.registerPlayer("test");
        assertEquals(expectedPlayer, service.findPlayer("test"));
    }

    @Test
    void getAccount_whenRequestingPlayerAccount_thenGetActualAccount() {
        service.registerPlayer("test");
        assertEquals(expectedPlayer.getAccount(), service.getAccount("test"));
    }

    @Test
    void debitAccount_whenDebitMoreSumThanHaveInAccount_thenThrowingException() {
        service.registerPlayer("test");
        assertThrows(IllegalArgumentException.class, () -> service.debitAccount("test", 1L, 1.0));
    }

    @Test
    void debitAccount_whenDebitWithNotUniqueTransactionId_thenThrowingException() {
        service.registerPlayer("test");
        service.creditAccount("test", 1L, 10.0);
        assertThrows(IllegalArgumentException.class, () -> service.debitAccount("test", 1L, 9.0));
    }

    @Test
    void debitAccount_whenDebitFromAccount_thenAccountingDecreasingByAccordingSum() {
        service.registerPlayer("test");
        service.creditAccount("test", 1L, 10.0);
        service.debitAccount("test", 2L, 9.0);
        assertEquals(1.0, service.getAccount("test"));
    }

    @Test
    void creditAccount_whenCreditWithNotUniqueTransactionId_thenThrowingException() {
        service.registerPlayer("test");
        service.creditAccount("test", 1L, 10.0);
        assertThrows(IllegalArgumentException.class, () -> service.creditAccount("test", 1L, 9.0));
    }

    @Test
    void creditAccount_whenCreditToAccount_thenAccountingIncreasingByAccordingSum() {
        service.registerPlayer("test");
        service.creditAccount("test", 1L, 10.0);
        assertEquals(10.0, service.getAccount("test"));
    }

    @Test
    void getPlayerTransactions_whenRequestingTransactionHistory_thenReturnTransactionHistory() {
        service.registerPlayer("test");
        service.creditAccount("test", 1L, 10.0);
        service.debitAccount("test", 2L, 9.0);
        List<Transaction> actualTransaction = service.getPlayerTransactions("test");
        assertEquals(10.0, actualTransaction.get(0).getSum());
        assertEquals(-9.0, actualTransaction.get(1).getSum());
    }
}