package com.southwind.ship.repository;

import com.southwind.ship.entity.Staff;
import java.util.List;
import java.util.Map;

public interface ShipRepository {
    public List<Staff> findAll();
    public void RecordCost(Integer shipId, String shipName, Integer roomId, Integer userId, String userName,
                           Integer goodId, String goodName, Integer goodPrice);
    public List<Map<String, Object>> GetRecordCost();
}
