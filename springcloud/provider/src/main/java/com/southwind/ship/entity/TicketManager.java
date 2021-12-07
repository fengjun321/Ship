package com.southwind.ship.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class TicketManager {
    private List<Ticket> tiketList;
    public TicketManager() {
        tiketList = new ArrayList<Ticket>();
    }

    public Ticket getTicketInfo(Integer ticketId) {
        for (Ticket ticket :tiketList) {
            if (ticket.getId().equals(ticketId)) {
                return ticket;
            }
        }
        return null;
    }
}
