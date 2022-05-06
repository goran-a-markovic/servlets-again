package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    public void insert(User user);
    public User getUserById(int id);
    public List<User> getAllUsers();
    public void update(User user);
    public boolean delete(int id);

}
