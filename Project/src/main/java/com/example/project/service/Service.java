package com.example.project.service;

import com.example.project.entities.Client;
import com.example.project.entities.Flight;
import com.example.project.entities.Ticket;
import com.example.project.repository.ClientRepo;
import com.example.project.repository.FlightRepo;
import com.example.project.repository.TicketRepo;
import com.example.project.utils.observer.Observable;
import com.example.project.utils.observer.Observer;
import com.example.project.utils.events.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Service implements Observable<ChangeEvent> {

    private ClientRepo clientRepo;
    private FlightRepo flightRepo;
    private TicketRepo ticketRepo;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List<Observer<ChangeEvent>> observers = new ArrayList<>();

    public Service(ClientRepo clientRepo, FlightRepo flightRepo, TicketRepo ticketRepo) {
        this.clientRepo = clientRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
    }

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Iterable<Client> getLocations() {
        return clientRepo.findAll();
    }
    public Iterable<Flight> getFlights() {
        return flightRepo.findAll();
    }
    public Iterable<Ticket> getTickets() {return ticketRepo.findAll();}

    public Client getClientByUsername(String username){
        for(Client c: clientRepo.findAll()) {
            if (c.getUsername().equals(username))
                return c;
        }
        return null;
    }

    public void adaugaTicket(String username, Long idFlight){
        Ticket ticket=new Ticket(username,idFlight, LocalDateTime.now());
        ticketRepo.adauga(ticket);
        notifyObservers(new TicketChangeEvent(ChangeEventType.ADD, ticket));
    }

    public List<Ticket> findTicketClient(Client c){
        List<Ticket> tickets = new ArrayList<>();
        ticketRepo.findAll().forEach(b -> {if(b.getUsername().equals(c.getUsername())) tickets.add(b);});
        return tickets;
    }

    public List<Ticket> findTicketData(){
        List<Ticket> tickets = new ArrayList<>();
        ticketRepo.findAll().forEach(b -> {if(b.getData().getDayOfMonth() == 24 && b.getData().getMonthValue() == 1 && b.getData().getYear() == 2024) tickets.add(b);});
        return tickets;
    }


}
