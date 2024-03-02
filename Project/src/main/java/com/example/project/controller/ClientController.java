package com.example.project.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.project.StartApplication;
import com.example.project.entities.Client;
import com.example.project.entities.Flight;
import com.example.project.entities.Ticket;
import com.example.project.service.Service;
import com.example.project.utils.events.ChangeEvent;
import com.example.project.utils.observer.Observer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ClientController implements Observer<ChangeEvent> {
    private Service service;
    private Client client;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Flight, LocalDateTime> departureColoana;

    @FXML
    private ComboBox<String> fromCombo;

    @FXML
    private TableColumn<Flight, LocalDateTime> landingColoana;

    @FXML
    private TableColumn<Flight, Integer> seatsColoana;

    @FXML
    private TableView<Flight> tableView;

    @FXML
    private ComboBox<String> toCombo;

    @FXML
    private Button cautaButton;
    @FXML
    private Button buyButton;
    @FXML
    private TableColumn<Flight,Integer> availableColoana;
    @FXML
    private Button nextButton;

    private Integer index=5;
    ObservableList<Flight> model= FXCollections.observableArrayList();

    public void setService(Service service,Client client) {
        this.service = service;
        this.client=client;
        initModel();
        service.addObserver(this);
    }

    @FXML
    public void initialize() {
        /*tableView.setItems(model);*/
        landingColoana.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landing"));
        departureColoana.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departure"));
        seatsColoana.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));

        availableColoana.setCellValueFactory(c -> {
            Flight fl=c.getValue();
            Integer cnt=0;
            for(Ticket t: service.getTickets())
            {
                if(t.getIdFlight().equals(fl.getId()))
                    cnt++;
            }
            return new ReadOnlyObjectWrapper<Integer>(fl.getSeats()-cnt);
        });

        tableView.setItems(model);
    }

    private void initModel() {
        setCombo();
        handleSearch();
    }

    public void setCombo(){
        Set<String> from = new HashSet<>();
        Set<String> to = new HashSet<>();
        for(Flight flight : service.getFlights()) {
            from.add(flight.getFrom());
            to.add(flight.getTo());
        }
        fromCombo.getItems().addAll(from);
        toCombo.getItems().addAll(to);
    }

    public void handleSearch() {
        LocalDate start = datePicker.getValue();
        String from = fromCombo.getValue();
        String to=toCombo.getValue();
        if(start!=null && from!=null && to!=null) {
            Integer localIndex=0;

            model.clear();
            for (Flight fl : service.getFlights()) {
                if (fl.getDeparture().toLocalDate().compareTo(start) == 0 && fl.getFrom().equals(from) && fl.getTo().equals(to)) {
                    localIndex++;
                    if(localIndex<=index && localIndex>index-5)
                    model.add(fl);
                }
            }
        }
    }

    public void handleBilete(){
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("ticketView.fxml"));
            /*loader.setLocation(getClass().getResource("ticketView.fxml"));*/


            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Bilete");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            TicketsController ticketController = loader.getController();
            ticketController.setService(service, client);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleBileteData(){
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader(StartApplication.class.getResource("ticketViewData.fxml"));
            /*loader.setLocation(getClass().getResource("ticketView.fxml"));*/


            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Bilete");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            TicketsControlerData ticketController = loader.getController();
            ticketController.setService(service);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleBuy(ActionEvent event) {
        Flight flight= tableView.getSelectionModel().getSelectedItem();
        service.adaugaTicket(client.getUsername(),flight.getId());
    }

    public void handleNext(ActionEvent event){
        index=index+5;
        handleSearch();
    }

    public void handlePrevious(ActionEvent event){
        index=index-5;
        handleSearch();
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        initModel();
    }
}
