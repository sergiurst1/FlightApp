package com.example.project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.project.entities.Ticket;
import com.example.project.service.Service;
import com.example.project.utils.events.ChangeEvent;
import com.example.project.utils.observer.Observer;

import java.time.LocalDateTime;

public class TicketsControlerData implements Observer<ChangeEvent> {
@FXML
private TableView<Ticket> tableView;

@FXML
private TableColumn<Ticket, LocalDateTime> cumparareColoana;

@FXML
private TableColumn<Ticket, Long> idFlightColoana;

@FXML
private TableColumn<Ticket, String> usernameColoana;

        ObservableList<Ticket> model= FXCollections.observableArrayList();

        Service service;

public void setService(Service service) {
        this.service = service;
        initModel();
        service.addObserver(this);
        }

public void initialize() {
        /*tableView.setItems(model);*/
        usernameColoana.setCellValueFactory(new PropertyValueFactory<Ticket, String>("username"));
        idFlightColoana.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("idFlight"));
        cumparareColoana.setCellValueFactory(new PropertyValueFactory<Ticket, LocalDateTime>("data"));

        tableView.setItems(model);
        }

private void initModel() {
        findTickets();
        }

        private void findTickets(){
            service.findTicketData().forEach(t -> model.add(t));
        }

        @Override
        public void update(ChangeEvent changeEvent) {
            initModel();
        }
}
