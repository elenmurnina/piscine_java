public class UsersArrayList implements UsersList {
    private static final int DEFAULT_CAPACITY = 10;

    private User[] users = new User[DEFAULT_CAPACITY];
    private int userCount = 0;

    @Override
    public void addUser(User newUser) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getId() == newUser.getId()) {
                return;
            }
        }

        if (userCount >= users.length) {
            User[] tmpUsers = new User[users.length * 2];
            for (int i = 0; i < users.length; i++) {
                tmpUsers[i] = users[i];
            }
            users = tmpUsers;
        }

        users[userCount] = newUser;
        userCount++;
    }

    @Override
    public User findById(int id) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getId() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User findByIndex(int index) {
        if (index >= userCount) {
            throw new UserNotFoundException();
        }
        return users[index];
    }

    @Override
    public int getUserCount() {
        return userCount;
    }
}