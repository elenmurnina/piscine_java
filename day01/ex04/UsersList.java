public interface UsersList {
    void addUser(User newUser);
    User findById(int id);
    User findByIndex(int index);
    int getUserCount();
}
