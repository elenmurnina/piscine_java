public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("Can't find Transaction with this identifier");
    }
}