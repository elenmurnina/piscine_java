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

    public Transaction() {
        id = UUID.randomUUID();
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
        if ((transferAmount > 0 && transferCategory == Category.DEBIT)
                || (transferAmount < 0 && transferCategory == Category.CREDIT)) {
            this.transferAmount = transferAmount;
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + getId() +
                ", recipient=" + getRecipient() +
                ", sender=" + getSender() +
                ", transferCategory=" + getTransferCategory() +
                ", transferAmount=" + getTransferAmount() +
                '}';
    }
}
