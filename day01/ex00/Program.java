public class Program {

    public static void main(String[] args) {
        User first = new User();
        first.setId(1);
        first.setName("Lol");
        first.setBalance(10);
        System.out.println(first);

        User second = new User();
        second.setId(2);
        second.setName("Kek");
        second.setBalance(-13);
        System.out.println(second);
        second.setBalance(13);
        System.out.println(second);

        Transaction transaction1 = new Transaction();
        transaction1.setRecipient(first);
        transaction1.setSender(second);
        transaction1.setTransferCategory(Transaction.Category.CREDIT);
        transaction1.setTransferAmount(55);
        System.out.println(transaction1);
        transaction1.setTransferAmount(-55);
        System.out.println(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setRecipient(second);
        transaction2.setSender(first);
        transaction2.setTransferCategory(Transaction.Category.DEBIT);
        transaction2.setTransferAmount(13);
        System.out.println(transaction2);
    }
}
