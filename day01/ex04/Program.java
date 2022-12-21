import java.util.UUID;

public class Program {

    public static void main(String[] args) {

        TransactionsService service = new TransactionsService();

        User first = new User("Lol", 250);
        User second = new User("Kek", 1333);

        service.addUser(first);
        service.addUser(second);

        System.out.print("Befor transaction: \nbalance first: \n");
        System.out.println(service.retrieveUserBalance(first.getId()));
        System.out.println("balance second: ");
        System.out.println(service.retrieveUserBalance(second.getId()));
        service.transferTransaction(first.getId(), second.getId(), 150);
        System.out.print("Aftre transaction first->second (150): \nbalance first: \n");
        System.out.println(service.retrieveUserBalance(first.getId()));
        System.out.println("balance second: ");
        System.out.println(service.retrieveUserBalance(second.getId()));
        service.transferTransaction(first.getId(), second.getId(), 10);
        service.transferTransaction(first.getId(), second.getId(), 15);

        UUID idTransaction = UUID.randomUUID();
        Transaction[] firstUserTransaction = service.retrieveUserTransaction(first.getId());
        for(Transaction t : firstUserTransaction) {
            System.out.println(t);
            idTransaction = t.getId();
        }
        service.removeTransactionFromUser(first.getId(), idTransaction);
        System.out.println();

        firstUserTransaction = service.retrieveUserTransaction(first.getId());
        for(Transaction t : firstUserTransaction) {
            System.out.println(t);
        }
        System.out.println();

        Transaction[] secondUserTransaction = service.retrieveUserTransaction(second.getId());
        for (Transaction t : secondUserTransaction) {
            System.out.println(t);
        }
        System.out.println();

        Transaction[] invalidList = service.checkValidityOfTransactions();
        System.out.println("Unpaired transactions: " + invalidList.length);
        for (Transaction t : invalidList) {
            System.out.println(t);
        }
        service.removeTransactionFromUser(second.getId(), idTransaction);
        invalidList = service.checkValidityOfTransactions();
        System.out.println("Unpaired transactions: " + invalidList.length);
        for (Transaction t : invalidList) {
            System.out.println(t);
        }
        System.out.println();

        System.out.println("Balance first: " + service.retrieveUserBalance(first.getId()));
        System.out.println("Balance second: " + service.retrieveUserBalance(second.getId()));
        service.transferTransaction(second.getId(), first.getId(), 1000);
        System.out.println("After transaction second->first (1000) \nBalance first: " + service.retrieveUserBalance(first.getId()));
        System.out.println("Balance second: " + service.retrieveUserBalance(second.getId()));
        System.out.println();


        secondUserTransaction = service.retrieveUserTransaction(second.getId());
        for (Transaction t : secondUserTransaction) {
            service.removeTransactionFromUser(second.getId(), t.getId());
        }
        System.out.println();


        invalidList = service.checkValidityOfTransactions();
        System.out.println("Unpaired transactions after deleting all transactions of second user: " + invalidList.length);
        for (Transaction t : invalidList) {
            System.out.println(t);
        }

        System.out.println();
        try {
            service.transferTransaction(first.getId(), second.getId(), 3042);
        } catch (IllegalTransactionException ex) {
            System.out.println("EXCEPTION MESSAGE: " + ex);
        }
    }
}
