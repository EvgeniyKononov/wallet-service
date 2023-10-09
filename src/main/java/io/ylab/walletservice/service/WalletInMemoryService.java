package io.ylab.walletservice.service;

import io.ylab.walletservice.model.player.Player;
import io.ylab.walletservice.model.transaction.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which allow to create new players, find them, make debit/credit of player's account
 * and see transactions history. All data store in memory
 */
public class WalletInMemoryService {
    private Long playerId;
    private final Map<String, Player> players;
    private final Map<Long, Transaction> transactions;

    public WalletInMemoryService() {
        this.playerId = 1L;
        this.players = new HashMap<>();
        this.transactions = new HashMap<>();
    }

    /**
     * Method for creation the new players
     * @param login the unique name of player
     * @return the created player
     */
    public Player registerPlayer(String login) {
        if (players.containsKey(login)) {
            throw new IllegalArgumentException("Игрок с таким логином уже существует");
        }
        Player player = new Player(playerId, login);
        players.put(login, player);
        playerId++;
        return player;
    }

    /**
     * Method for searching the players
     * @param login the login of searching player
     * @return the found player
     */
    public Player findPlayer(String login) {
        if (!players.containsKey(login)) {
            throw new IllegalArgumentException("Игрока с таким логином не существует");
        }
        return players.get(login);
    }

    /**
     * Method for getting the current account balance of player
     * @param login the login player
     * @return the current account balance of player
     */
    public Double getAccount(String login) {
        Player player = findPlayer(login);
        return player.getAccount();
    }

    /**
     * Method for debit of player account
     * @param login the login player
     * @param transactionId the unique id of transaction
     * @param sum sum of debit, which cannot be less than current account balance of player
     * @return player with updated account and transactions
     */
    public Player debitAccount(String login, Long transactionId, Double sum) {
        if (transactions.containsKey(transactionId)) {
            throw new IllegalArgumentException("Транзакция с таким ID уже существует");
        }
        Player player = findPlayer(login);
        if (player.getAccount() < sum) {
            throw new IllegalArgumentException("Недостаточно средств на счете игрока");
        }
        Transaction transaction = new Transaction(transactionId, -sum);
        transactions.put(transactionId, transaction);
        List<Transaction> playerTransactions = player.getTransactions();
        playerTransactions.add(transaction);
        players.put(login, new Player(player.getId(), login, player.getAccount() - sum, playerTransactions));
        return players.get(login);
    }

    /**
     * Method for credit of player account
     * @param login the login player
     * @param transactionId the unique id of transaction
     * @param sum sum of credit
     * @return player with updated account and transactions
     */
    public Player creditAccount(String login, Long transactionId, Double sum) {
        if (transactions.containsKey(transactionId)) {
            throw new IllegalArgumentException("Транзакция с таким ID уже существует");
        }
        Player player = findPlayer(login);
        Transaction transaction = new Transaction(transactionId, sum);
        transactions.put(transactionId, transaction);
        List<Transaction> playerTransactions = player.getTransactions();
        playerTransactions.add(transaction);
        players.put(login, new Player(player.getId(), login, player.getAccount() + sum, playerTransactions));
        return players.get(login);
    }

    /**
     * Method for getting history of players transactions
     * @param login the login player
     * @return list of players transactions
     */
    public List<Transaction> getPlayerTransactions(String login) {
        Player player = findPlayer(login);
        return player.getTransactions();
    }

}
