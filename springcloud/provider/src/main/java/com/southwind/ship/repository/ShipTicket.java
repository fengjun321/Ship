package com.southwind.ship.repository;

import com.southwind.ship.entity.Ticket;

import java.util.List;
import java.util.Map;

public interface ShipTicket {
    public List<Map<String, Object>> findAll();
    public List<Map<String, Object>> getUserAllTicket(Integer userId);
    public List<Map<String, Object>> getAllRoom(Integer shipId);
    public void buyTicket(Integer userId, Integer shipId, Integer roomId);
    public void updateRoomManage(Integer shipId, String reserve);
}
