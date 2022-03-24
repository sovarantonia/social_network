package repository.db;

import domain.User;
import repository.memory.MemoryRepoUser;
import validator.UserValidator;

import java.sql.*;
import java.util.ArrayList;

public class DbRepoUser extends MemoryRepoUser {
    private final String url;
    private final String username;
    private final String password;

    public DbRepoUser(String url, String username, String password, UserValidator validator) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void add(User u) {
        String sql = "insert into users (username, firstName, lastName, password) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, u.getUserName());
            ps.setString(2, u.getFirstName());
            ps.setString(3, u.getLastName());
            ps.setString(4, u.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(User u) {
        String sql = "delete from users where userName = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, u.getUserName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getObject(String userName) {
        String sql = "select* from users where userName = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            User u = new User((String) resultSet.getObject(2), (String) resultSet.getObject(3),
                    (String) resultSet.getObject(4), (String) resultSet.getObject(5));
            u.setId(resultSet.getLong(1));
            return u;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {


            while (resultSet.next()) {

                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(userName,firstName, lastName, password);
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}


