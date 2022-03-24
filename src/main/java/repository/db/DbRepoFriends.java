package repository.db;

import domain.Friendship;
import repository.memory.MemoryRepoFriends;
import validator.FriendshipValidator;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DbRepoFriends extends MemoryRepoFriends {

    private final String url;
    private final String username;
    private final String password;

    public DbRepoFriends(String url, String username, String password, FriendshipValidator friendshipValidator) {
        super(friendshipValidator);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }


    @Override
    public void add(Friendship fr) {
        String sql = "insert into friendships(username1, username2, friendshipdate) values (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, fr.getUsername1());
            ps.setString(2, fr.getUsername2());
            ps.setDate(3, convertToDateViaSqlDate(fr.getFriendshipDate()));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Friendship fr){
        String sql = "delete from friendships where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, fr.getId());
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public Friendship getObject(Long id){
        String sql = "select* from friendships where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();

            Friendship fr = new Friendship(resultSet.getString("username1"),
                    resultSet.getString("username2"),
                    (resultSet.getDate("friendshipdate").toLocalDate()));
            fr.setId(resultSet.getLong("id"));
            return fr;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Friendship> getAll(){
        String sql = "select* from friendships";
        ArrayList<Friendship> friendships = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Friendship fr = new Friendship(resultSet.getString("username1"),
                        resultSet.getString("username2"),
                        (resultSet.getDate("friendshipdate").toLocalDate()));

                fr.setId(resultSet.getLong("id"));
                friendships.add(fr);
            }
            return friendships;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return friendships;
    }
}
