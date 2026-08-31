import java.util.ArrayList;
import java.util.Scanner;

class Account {

    private String userId;
    private int pin;
    private long balance;

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Account(String userId, int pin, long balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public int getPin() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public boolean withdraw(long amount) {

        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }
}

class Transaction {

    private String type;
    private long amount;

    public Transaction(String type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}

class Bank {

    private ArrayList<Account> accounts = new ArrayList<>();

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account findAccount(String userId) {

        for (Account account : accounts) {

            if (account.getUserId().equals(userId)) {
                return account;
            }
        }

        return null;
    }
}

class ATM {

    private Bank bank;
    private Scanner sc;

    public ATM(Bank bank) {
        this.bank = bank;
        this.sc = new Scanner(System.in);
    }

    public void start() {

        Account currentAccount = authenticate();

        if (currentAccount == null) {
            return;
        }

        System.out.println("\nLogin successful!");

        while (true) {

            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("==============================");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showTransactionHistory(currentAccount);
                    break;

                case 2:
                    withdraw(currentAccount);
                    break;

                case 3:
                    deposit(currentAccount);
                    break;

                case 4:
                    transfer(currentAccount);
                    break;

                case 5:
                    System.out.println("Thank you for using our ATM.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private Account authenticate() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("\nEnter User ID: ");
            String userId = sc.next();

            Account account = bank.findAccount(userId);

            if (account == null) {

                System.out.println("Invalid User ID.");
                attempts++;

                continue;
            }

            System.out.print("Enter PIN: ");
            int pin = sc.nextInt();

            if (pin == account.getPin()) {
                return account;
            }

            System.out.println("Invalid PIN.");
            attempts++;
        }

        System.out.println("\nToo many incorrect attempts.");
        System.out.println("Access denied.");

        return null;
    }

    private void showTransactionHistory(Account account) {

        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (account.getTransactions().isEmpty()) {

            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : account.getTransactions()) {

            System.out.println(
                transaction.getType()
                + " : Rs. "
                + transaction.getAmount()
            );
        }
    }

    private void withdraw(Account account) {

        System.out.print("\nEnter amount to withdraw: ");
        long amount = sc.nextLong();

        if (amount <= 0) {

            System.out.println("Invalid amount.");
            return;
        }

        if (account.withdraw(amount)) {

            account.getTransactions().add(
                new Transaction("Withdraw", amount)
            );

            System.out.println("Withdrawal successful.");
            System.out.println(
                "Remaining balance: Rs. "
                + account.getBalance()
            );

        } else {

            System.out.println("Insufficient funds.");
        }
    }

    private void deposit(Account account) {

        System.out.print("\nEnter amount to deposit: ");
        long amount = sc.nextLong();

        if (amount <= 0) {

            System.out.println("Invalid amount.");
            return;
        }

        account.deposit(amount);

        account.getTransactions().add(
            new Transaction("Deposit", amount)
        );

        System.out.println("Deposit successful.");
        System.out.println(
            "Current balance: Rs. "
            + account.getBalance()
        );
    }

    private void transfer(Account sender) {

        System.out.print("\nEnter recipient User ID: ");
        String recipientId = sc.next();

        Account recipient = bank.findAccount(recipientId);

        if (recipient == null) {

            System.out.println("Recipient account not found.");
            return;
        }

        if (recipient == sender) {

            System.out.println(
                "You cannot transfer money to your own account."
            );

            return;
        }

        System.out.print("Enter amount to transfer: ");
        long amount = sc.nextLong();

        if (amount <= 0) {

            System.out.println("Invalid amount.");
            return;
        }

        if (sender.getBalance() < amount) {

            System.out.println("Insufficient funds.");
            return;
        }

        sender.withdraw(amount);
        recipient.deposit(amount);

        sender.getTransactions().add(
            new Transaction("Transfer to " + recipient.getUserId(), amount)
        );

        recipient.getTransactions().add(
            new Transaction("Transfer from " + sender.getUserId(), amount)
        );

        System.out.println("Transfer successful.");

        System.out.println(
            "Your new balance: Rs. "
            + sender.getBalance()
        );
    }
}

public class ATMInterface {

    public static void main(String[] args) {

        Bank bank = new Bank();

        Account account1 =
            new Account("user1", 1324, 10000);

        Account account2 =
            new Account("user2", 5678, 10000);

        Account account3 =
            new Account("user3", 9110, 10000);

        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(account3);

        ATM atm = new ATM(bank);

        atm.start();
    }
}