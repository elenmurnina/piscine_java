public class User {
    private final int id;
    private String name;
    private int balance;
    private TransactionsList transactionsList;

    public User() {
        this.id = UserIdsGenerator.getInstance().generateId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("The initial user balance cannot be negative");
            this.balance = 0;
        }
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(TransactionsList transactionsList) {
        this.transactionsList = transactionsList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", balance=" + getBalance() +
                ", transactionsList=" + getTransactionsList() + '}';
    }
}
