public class UserIdsGenerator {

    private static final UserIdsGenerator INSTANCE = new UserIdsGenerator();
    private int currentId = 0;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        return INSTANCE;
    }

    public int generateId() {
        currentId++;
        return currentId;
    }
}
