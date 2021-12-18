package com.southwind.ship.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Room {
    private Integer shipId;
    private Integer roomRange;
    private List<Integer> Reserve;
    private List<Integer> PriceLev;
    private Integer cashPledge;

    public Room () {
        Reserve = new ArrayList<Integer>();
        PriceLev = new ArrayList<Integer>();
    }
}
