import java.util.UUID;

public class Transaction {
    private final UUID id;
    private User recipient;
    private User sender;
    private Category transferCategory;
    private int transferAmount;

    public enum Category {
        DEBIT,
        CREDIT
    }

    private Transaction(Transaction other) {
        id = other.id;
        this.recipient = other.recipient;
        this.sender = other.sender;
        this.transferCategory = other.transferCategory;
        this.transferAmount = other.transferAmount;
    }

    public Transaction(User recipient, User sender, Category transferCategory, int transferAmount) {
        id = UUID.randomUUID();
        setRecipient(recipient);
        setSender(sender);
        setTransferCategory(transferCategory);
        setTransferAmount(transferAmount);
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Category getTransferCategory() {
        return transferCategory;
    }

    public void setTransferCategory(Category transferCategory) {
        this.transferCategory = transferCategory;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(int transferAmount) {
        if (transferAmount > 0 && transferCategory == Category.DEBIT) {
            System.out.println("Outgoing payments can be negative amounts only");
        } else if (transferAmount < 0 && transferCategory == Category.CREDIT) {
            System.out.println("Incoming payments can be positive amounts only");
        } else {
            this.transferAmount = transferAmount;
        }
    }

    public Transaction createCopyForSecondUser() {
        Transaction copy = new Transaction(this);
        if (this.transferCategory == Category.DEBIT) {
            copy.setTransferCategory(Category.CREDIT);
            copy.setTransferAmount(this.getTransferAmount() * (-1));
        } else {
            copy.setTransferCategory(Category.DEBIT);
        }
        return copy;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + getId() +
                ", recipient id=" + getRecipient().getId() +
                ", sender id=" + getSender().getId() +
                ", transferCategory=" + getTransferCategory() +
                ", transferAmount=" + getTransferAmount() +
                '}';
    }
}
