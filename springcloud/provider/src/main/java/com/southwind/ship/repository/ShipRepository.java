package com.southwind.ship.repository;

import com.southwind.ship.entity.Staff;

import java.util.List;

public interface ShipRepository {
    public List<Staff> findAll();
}
