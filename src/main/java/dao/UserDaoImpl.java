package dao;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    Connection connection;

    public UserDaoImpl() {
        connection = ConnectionManager.getConnection();
    }

    @Override
    public void insert(User user) {
        String query = "INSERT INTO users (first_name, last_name, email) VALUES( ?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            int count = statement.executeUpdate();
            if (count == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                int user_id = resultSet.getInt("id");
                System.out.println("Generated user ID is : " + user_id);
            } else {
                System.out.println("Something went wrong when creating a user!");
                }
            } catch (SQLException e) {
                e.printStackTrace();

        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String sql = "select * from users;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = getUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = getUser(resultSet);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(ResultSet resultSet) {
        try {
            int idData = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            return new User(idData, firstName, lastName, email);
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }


    @Override
    public void update(User user) {
        String sql = "update users set first_name = ?, last_name = ?, email = ? where id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getUserId());
            int count = preparedStatement.executeUpdate();
            if (count == 1) System.out.println("Update successful!");
            else System.out.println("Something went wrong with the update");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from users where id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                System.out.println("Deletion successful!");
                return true;
            }
            else {
                System.out.println("Something went wrong with the deletion!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
