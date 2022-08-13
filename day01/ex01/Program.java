public class Program {

    public static void main(String[] args) {
        User first = new User();
        first.setName("Lol");
        first.setBalance(10);
        System.out.println(first);

        User second = new User();
        second.setName("Kek");
        second.setBalance(13);
        System.out.println(second);

        UserIdsGenerator checkId = UserIdsGenerator.getInstance();

        User third = new User();
        third.setName("Azaza");
        third.setBalance(21);
        System.out.println(third);

        User fourth = new User();
        fourth.setName("Ahah");
        fourth.setBalance(15);
        System.out.println(fourth);
    }
}
