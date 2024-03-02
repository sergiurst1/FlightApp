package com.example.project.utils.events;

import com.example.project.entities.Ticket;

public class TicketChangeEvent extends ChangeEvent<Ticket> implements Event{
    public TicketChangeEvent(ChangeEventType type, Ticket oldData) {
        super(type, oldData);
    }

    public TicketChangeEvent(ChangeEventType type, Ticket data, Ticket oldData) {
        super(type, data, oldData);
    }
}
