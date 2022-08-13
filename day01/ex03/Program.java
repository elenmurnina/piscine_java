public class Program {

    public static void main(String[] args) {

        TransactionsList transactions = new TransactionsLinkedList();

        User first = new User();
        first.setName("Lol");
        first.setBalance(10);

        User second = new User();
        second.setName("Kek");
        second.setBalance(1333);

        User third = new User();
        third.setName("Azaza");
        third.setBalance(21);

        User fourth = new User();
        fourth.setName("Ahah");
        fourth.setBalance(15);

        for (int i = 0; i < 10; i++) {
            Transaction tmp;
            if (i % 2 == 0) {
                tmp = new Transaction(first, second, Transaction.Category.DEBIT, -2);
            } else {
                tmp = new Transaction(third, fourth, Transaction.Category.CREDIT, 13);
            }
            transactions.addTransaction(tmp);
        }

        for (Transaction t : transactions.toArray()) {
            System.out.println(t);
        }
        System.out.println();

        TransactionsList transactions2 = new TransactionsLinkedList();
        Transaction t1 = new Transaction(first, second, Transaction.Category.DEBIT, -2);
        Transaction t2 = new Transaction(first, second, Transaction.Category.DEBIT, -2);
        Transaction t3 = new Transaction(first, second, Transaction.Category.DEBIT, -2);
        transactions2.addTransaction(t1);
        transactions2.addTransaction(t2);
        transactions2.addTransaction(t3);

        for (Transaction t : transactions2.toArray()) {
            System.out.println(t);
        }
        System.out.println();

        transactions2.removeTransactionById(t2.getId());

        for (Transaction t : transactions2.toArray()) {
            System.out.println(t);
        }
        System.out.println();

        System.out.println(first.getTransactionsList());
        first.setTransactionsList(transactions);
        System.out.println(first.getTransactionsList());
    }
}
