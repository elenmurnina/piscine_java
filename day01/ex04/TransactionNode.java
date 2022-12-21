class TransactionNode {

    private TransactionNode next;
    private TransactionNode previous;
    private Transaction data;

    private TransactionNode() {}

    TransactionNode(Transaction data) {
        this.data = data;
    }

    public TransactionNode(TransactionNode next, TransactionNode previous, Transaction data) {
        this.next = next;
        this.previous = previous;
        this.data = data;
    }

    public TransactionNode getNext() {
        return next;
    }

    public void setNext(TransactionNode next) {
        this.next = next;
    }

    public TransactionNode getPrevious() {
        return previous;
    }

    public void setPrevious(TransactionNode previous) {
        this.previous = previous;
    }

    public Transaction getData() {
        return data;
    }

    public void setData(Transaction data) {
        this.data = data;
    }
}