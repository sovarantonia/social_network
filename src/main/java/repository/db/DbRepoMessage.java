package repository.db;

import domain.Message;
import repository.memory.MemoryRepoMessage;
import validator.MessageValidator;

import java.sql.*;
import java.util.ArrayList;

public class DbRepoMessage extends MemoryRepoMessage {
    private final String url;
    private final String username;
    private final String password;

    public DbRepoMessage(String url, String username, String password, MessageValidator messageValidator){
        super(messageValidator);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void add(Message m){
        String sql = "insert into messages(fromuser, touser, text, data) values (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, m.getFrom());
            ps.setString(2, m.getTo());
            ps.setString(3, m.getMessage());
            ps.setTimestamp(4, Timestamp.valueOf(m.getDate()));

            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Message m){
        String sql = "delete from messages where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, m.getId());
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Message getObject(Long id){
        String sql = "select* from messages where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            Message m = new Message(resultSet.getString("fromuser"), resultSet.getString("touser"),
                    resultSet.getString("text"), resultSet.getTimestamp("data").toLocalDateTime());
            m.setId(resultSet.getLong("id"));
            return m;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Message> getAll(){
        String sql = "select* from messages";
        ArrayList<Message> messages = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Message m = new Message(resultSet.getString("fromuser"), resultSet.getString("touser"),
                        resultSet.getString("text"), resultSet.getTimestamp("data").toLocalDateTime());
                m.setId(resultSet.getLong("id"));
                messages.add(m);
            }
            return messages;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return messages;
    }
}
