public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException() {
        super ("Not enough money on the sender's account.");
    }
}
