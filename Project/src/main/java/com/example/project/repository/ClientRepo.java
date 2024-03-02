package com.example.project.repository;

import com.example.project.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepo implements Repository<Long, Client> {
    private String url;
    private String usernamee;
    private String passwd;

    public ClientRepo(String url, String usernamee, String passwd) {
        this.url = url;
        this.usernamee = usernamee;
        this.passwd = passwd;
    }

    @Override
    public Iterable<Client> findAll() {
        List<Client> all = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, usernamee, passwd);
             PreparedStatement statement = connection.prepareStatement("SELECT * from client");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String name = resultSet.getString("nume");

                Client client=new Client(username,name);
                all.add(client);
            }
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }
}
