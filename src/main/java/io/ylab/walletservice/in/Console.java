package io.ylab.walletservice.in;

import io.ylab.walletservice.service.WalletInMemoryService;

import java.util.Scanner;

/**
 * Class for managing wallet by console
 */
public class Console {
    /**
     * The main method of class.
     * The structure of menu:
     * 1 - Зарегестрировать нового пользователя - register the new player
     * 2 - Авторизоваться - enter to the system by exact player. It required to get access for options 3-7
     * 3 - Проверить текущий баланс - check the current account
     * 4 - Дебет счёта - debit of account
     * 5 - Кредит счёта - credit of account
     * 6 - Просмотреть историю транзакций - get transactions history
     * 7 - Закончить сессию  - exit from system by earlier authorized player
     * 8 - Выйти - close program
     */
    public static void startSession() {
        WalletInMemoryService service = new WalletInMemoryService();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        String currentPlayer = null;
        while (choice != 8) {
            printMenu();
            choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1: {
                        System.out.println("Введите логин нового игрока");
                        scanner.nextLine();
                        String login = scanner.nextLine();
                        System.out.println(service.registerPlayer(login));
                        break;
                    }

                    case 2: {
                        if (currentPlayer == null) {
                            System.out.println("Введите логин для авторизации");
                            scanner.nextLine();
                            String login = scanner.nextLine();
                            System.out.println(service.findPlayer(login));
                            currentPlayer = login;
                        } else {
                            System.out.println("Вы не вышли из предыдущей сессии");
                        }
                        break;
                    }
                    case 3: {
                        if (currentPlayer != null) {
                            System.out.println(service.getAccount(currentPlayer));
                        } else {
                            System.out.println("Вы не авторизовались");
                        }
                        break;
                    }
                    case 4: {
                        if (currentPlayer != null) {
                            System.out.println("Введите уникальный номер транзакции:");
                            Long transactionId = scanner.nextLong();
                            System.out.println("Введите сумму дебита:");
                            Double sum = scanner.nextDouble();
                            System.out.println(service.debitAccount(currentPlayer, transactionId, sum));
                        } else {
                            System.out.println("Вы не авторизовались");
                        }
                        break;
                    }
                    case 5: {
                        if (currentPlayer != null) {
                            System.out.println("Введите уникальный номер транзакции:");
                            Long transactionId = scanner.nextLong();
                            System.out.println("Введите сумму кредита:");
                            Double sum = scanner.nextDouble();
                            System.out.println(service.creditAccount(currentPlayer, transactionId, sum));
                        } else {
                            System.out.println("Вы не авторизовались");
                        }
                        break;
                    }
                    case 6: {
                        if (currentPlayer != null) {
                            System.out.println(service.getPlayerTransactions(currentPlayer));
                        } else {
                            System.out.println("Вы не авторизовались");
                        }
                        break;
                    }
                    case 7: {
                        if (currentPlayer != null) {
                            currentPlayer = null;
                            System.out.println("Вы вышли из сессии");
                        } else {
                            System.out.println("Вы не авторизовались");
                        }
                        break;
                    }
                    case 8: {
                        break;
                    }
                    default: {
                        System.out.println("Введите цифру от 1 до 8");
                        break;
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Зарегестрировать нового пользователя");
        System.out.println("2 - Авторизоваться");
        System.out.println("3 - Проверить текущий баланс");
        System.out.println("4 - Дебет счёта");
        System.out.println("5 - Кредит счёта");
        System.out.println("6 - Просмотреть историю транзакций");
        System.out.println("7 - Закончить сессию");
        System.out.println("8 - Выйти");
    }
}