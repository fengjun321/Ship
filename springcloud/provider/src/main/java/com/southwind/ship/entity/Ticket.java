package com.southwind.ship.entity;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Ticket {
    private Integer id;
    private String name;
    private Integer price;
    private Integer discout;
    private String dateTime;
    private String desc;
    private Integer roomId;

    //数据库ship表的数据直接映射到Ticket各个变量
    public void ShipTableValueToTicket(Map<String, Object> ticketInfo) {
        this.setId((Integer) ticketInfo.get("Id"));
        this.setName((String) ticketInfo.get("Name"));
        this.setPrice((Integer) ticketInfo.get("Price"));
        this.setDiscout((Integer) ticketInfo.get("Discout"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.setDateTime((String) formatter.format(ticketInfo.get("Date")));
        this.setDesc((String)ticketInfo.get("Des"));
    }
}
