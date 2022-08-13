import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private int len = 0;

    private final TransactionNode begin = new TransactionNode(null, null, null);
    private final TransactionNode end = new TransactionNode(null, null, null);

    public TransactionsLinkedList() {
        begin.setNext(end);
        end.setPrevious(begin);
    }

    @Override
    public void addTransaction(Transaction newTransaction) {
        TransactionNode newNode = new TransactionNode(begin.getNext(), begin, newTransaction);
        begin.getNext().setPrevious(newNode);
        begin.setNext(newNode);
        len++;
    }

    @Override
    public void removeTransactionById(UUID id) {
        TransactionNode tmp = begin.getNext();
        while (tmp != end) {
            if (tmp.getData().getId().equals(id)) {
                tmp.getPrevious().setNext(tmp.getNext());
                tmp.getNext().setPrevious(tmp.getPrevious());

                tmp.setData(null);
                tmp.setPrevious(null);
                tmp.setNext(null);
                len--;
                return;
            }
            tmp = tmp.getNext();
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[len];
        TransactionNode tmp = begin.getNext();

        for (int i = 0; i < len; i++) {
            transactions[i] = tmp.getData();
            tmp = tmp.getNext();
        }

        return transactions;
    }
}
