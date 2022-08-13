public class Program {

    public static void main(String[] args) {
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

        UsersArrayList usersList = new UsersArrayList();
        usersList.addUser(first);
        usersList.addUser(second);
        usersList.addUser(third);
        usersList.addUser(fourth);

        try {
            System.out.println(usersList.findByIndex(1));
            System.out.println(usersList.findById(1));
            usersList.findByIndex(4242);
        } catch (UserNotFoundException e) {
            System.out.println(e);
        }

        for (int i = 1; i < 12; i++) {
            User tempUser = new User();
            tempUser.setName("Temp User " + i);
            tempUser.setBalance(i * i);
            usersList.addUser(tempUser);
        }

        for (int i = 1; i <= usersList.getUserCount(); i++) {
            System.out.println(usersList.findById(i));
        }
    }
}
