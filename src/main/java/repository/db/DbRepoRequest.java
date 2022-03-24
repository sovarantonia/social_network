package repository.db;

import domain.Request;
import domain.Status;
import repository.memory.MemoryRepoRequest;
import validator.RequestValidator;

import java.sql.*;
import java.util.ArrayList;

public class DbRepoRequest extends MemoryRepoRequest {
    private final String url;
    private final String username;
    private final String password;

    public DbRepoRequest(String url, String username, String password, RequestValidator requestValidator) {
        super(requestValidator);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void add(Request r){
        String sql = "insert into requests (username1, username2, status) values (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1,r.getFrom());
            ps.setString(2, r.getTo());
            ps.setString(3,  r.getStatus().toString());

            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Request r){
        String sql = "delete from requests where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, r.getId());
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Request getObject(Long id){
        String sql = "select* from requests where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            Request r = new Request(resultSet.getString("username1"), resultSet.getString("username2"));
            r.setStatus(Status.valueOf(resultSet.getString("status")));
            r.setId(resultSet.getLong("id"));
            return r;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Request> getAll(){
        String sql = "select* from requests";
        ArrayList<Request> requests = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Request r = new Request(resultSet.getString("username1"), resultSet.getString("username2"));
                r.setStatus(Status.valueOf(resultSet.getString("status")));
                r.setId(resultSet.getLong("id"));
                requests.add(r);
            }
            return requests;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return requests;
    }
}
