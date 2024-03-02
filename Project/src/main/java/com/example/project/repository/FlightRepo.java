package com.example.project.repository;

import com.example.project.entities.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightRepo implements Repository<Long, Flight> {
    private String url;
    private String username;
    private String passwd;

    public FlightRepo(String url, String username, String passwd) {
        this.url = url;
        this.username = username;
        this.passwd = passwd;
    }

    @Override
    public Iterable<Flight> findAll() {
        List<Flight> all = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, passwd);
             PreparedStatement statement = connection.prepareStatement("SELECT * from flight");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id=resultSet.getLong("idf");
                String from = resultSet.getString("fromf");
                String to = resultSet.getString("tof");
                LocalDateTime departure=resultSet.getTimestamp("departure").toLocalDateTime();
                LocalDateTime landing=resultSet.getTimestamp("landing").toLocalDateTime();
                Integer seats=resultSet.getInt("seats");

                Flight fl=new Flight(id,from,to,departure,landing,seats);
                all.add(fl);
            }
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }
}
