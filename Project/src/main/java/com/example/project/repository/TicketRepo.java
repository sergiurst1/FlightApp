package com.example.project.repository;

import com.example.project.entities.Ticket;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketRepo implements Repository<Long, Ticket>{
    private String url;
    private String username;
    private String passwd;

    public TicketRepo(String url, String username, String passwd) {
        this.url = url;
        this.username = username;
        this.passwd = passwd;
    }

    public void adauga(Ticket ticket) {
        String sql = "insert into ticket (username,idflight,datacumparare) values (?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, passwd);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, ticket.getUsername());
            ps.setLong(2, ticket.getIdFlight());
            ps.setTimestamp(3,Timestamp.valueOf(ticket.getData()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Iterable<Ticket> findAll() {
        List<Ticket> all = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, passwd);
             PreparedStatement statement = connection.prepareStatement("SELECT * from ticket");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long idFlight=resultSet.getLong("idflight");
                String usrname = resultSet.getString("username");
                LocalDateTime data=resultSet.getTimestamp("datacumparare").toLocalDateTime();

                Ticket tckt=new Ticket(usrname,idFlight,data);
                all.add(tckt);
            }
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return all;
    }
}
