import java.util.UUID;

public class TransactionsService {
    private UsersList usersList = new UsersArrayList();

    TransactionsService() {}

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public int retrieveUserBalance(int userId) {
        return usersList.findById(userId).getBalance();
    }

    public void transferTransaction(int senderId,
                                    int recipientId,
                                    int transferAmount) throws IllegalTransactionException {
        User sender = usersList.findById(senderId);
        if (sender.getBalance() < transferAmount) {
            throw new IllegalTransactionException();
        }
        User recipient = usersList.findById(recipientId);
        Transaction senderTransaction = new Transaction(recipient,
                sender, Transaction.Category.DEBIT, (-1 * transferAmount));
        Transaction recipientTransaction = senderTransaction.createCopyForSecondUser();
        usersList.findById(senderId).setBalance(sender.getBalance() - transferAmount);
        sender.getTransactionsList().addTransaction(senderTransaction);
        usersList.findById(recipientId).setBalance(recipient.getBalance() + transferAmount);
        recipient.getTransactionsList().addTransaction(recipientTransaction);
    }

    public Transaction[] retrieveUserTransaction(int userId) {
        User user = usersList.findById(userId);
        return user.getTransactionsList().toArray();
    }

    public void removeTransactionFromUser(int userId, UUID transactionId) {
        User user = usersList.findById(userId);
        user.getTransactionsList().removeTransactionById(transactionId);
    }

    public Transaction[] checkValidityOfTransactions() {
        int unpairedTransactionsCounter = 0;
        for (int i = 0; i < usersList.getUserCount(); i++) {
            unpairedTransactionsCounter += countUserUnpairedTransactions(usersList.findByIndex(i));
        }
        Transaction[] unpairedTransactionsArray = new Transaction[unpairedTransactionsCounter];
        int position = 0;
        for (int i = 0; i < usersList.getUserCount(); i++) {
            position = addTransactionsFromUserToArrayFromPosition(usersList.findByIndex(i),
                    unpairedTransactionsArray, position);
        }
        return unpairedTransactionsArray;
    }

    private int addTransactionsFromUserToArrayFromPosition(User user, Transaction[] transactions, int position) {
        Transaction[] userTransactionArray = user.getTransactionsList().toArray();
        for (int i = 0; i < userTransactionArray.length; i++) {
            Transaction[] partnerTransactionArray;
            if (userTransactionArray[i].getRecipient() == user) {
                partnerTransactionArray =
                        usersList.findById(userTransactionArray[i].getSender().getId())
                                .getTransactionsList().toArray();
            } else {
                partnerTransactionArray = usersList.findById(userTransactionArray[i].getRecipient().getId())
                        .getTransactionsList().toArray();
            }
            boolean notFound = true;
            for (int j = 0; j < partnerTransactionArray.length; j++) {
                if (userTransactionArray[i].getId() == partnerTransactionArray[j].getId()) {
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                transactions[position] = userTransactionArray[i];
                position++;
            }
        }
        return position;
    }

    private int countUserUnpairedTransactions(User user) {
        int unpairedTransactionsCounter = 0;
        Transaction[] userTransactionArray = user.getTransactionsList().toArray();
        for (int i = 0; i < userTransactionArray.length; i++) {
            Transaction[] partnerTransactionArray;
            if (userTransactionArray[i].getRecipient().getId() == user.getId()) {
                partnerTransactionArray =
                        usersList.findById(userTransactionArray[i].getSender().getId())
                                .getTransactionsList().toArray();
            } else {
                partnerTransactionArray =
                        usersList.findById(userTransactionArray[i].getRecipient().getId())
                        .getTransactionsList().toArray();
            }
            boolean notFound = true;
            for (int j = 0; j < partnerTransactionArray.length; j++) {
                if (userTransactionArray[i].getId() == partnerTransactionArray[j].getId()) {
                    notFound = false;
                    break;
                }
            }

            if (notFound) {
                unpairedTransactionsCounter++;
            }
        }
        return unpairedTransactionsCounter;
    }
}
