package com.southwind.ship.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserA {
    private String tookenId;
    private Integer userId;
    private String name;
    private Integer authority;
    private String password;
    private Integer coin;

    private Long onlineTick;
    private List<Ticket> tickets;

    public UserA() {
        tickets = new ArrayList<Ticket>();
    }
    public Integer GetUserRoomByTicketId(Integer ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(ticketId)) {
                return ticket.getRoomId();
            }
        }
        return -1;
    }
}
