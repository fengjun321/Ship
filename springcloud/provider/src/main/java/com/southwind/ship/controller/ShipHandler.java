package com.southwind.ship.controller;

import com.southwind.entity.User;
import com.southwind.ship.entity.Ticket;
import com.southwind.ship.entity.TicketManager;
import com.southwind.ship.entity.UserA;
import com.southwind.ship.entity.UserManager;
import com.southwind.ship.repository.Login;
import com.southwind.ship.repository.ShipRepository;
import com.southwind.entity.Message;
import com.southwind.ship.repository.ShipTicket;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Ship")
public class ShipHandler {
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private Login login;
    @Autowired
    private ShipTicket shipTick;
    @Autowired
    private UserManager userManager;
    @Autowired
    private TicketManager ticketManager;

    @GetMapping("/findAll")
    public Message findALl() {
        Message msg = new Message();
        msg.setErrcode(1);
        msg.setData(shipRepository.findAll());
        return msg;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String,Object> reg) {
        Map<String, Object> param = new HashMap<>();
        param.put("in_name", reg.get("username"));
        param.put("in_authority", reg.get("password"));
        param.put("in_password", reg.get("authority"));
        Map<String, Object> register = login.register(param);
        return register;
    }

    @PostMapping("/login")
    public Message login(@RequestBody Map<String, Object> lo) {
        Map<String, Object> loginInfo = this.login.login((String) lo.get("username"), (String) lo.get("password"));

        Message msg = new Message();
        UserA userI = userManager.getUserByName((String) lo.get("username"));
        if (userI != null) {
            if (userI.getPassword().equals((String)lo.get("password"))) {
                msg.setErrcode(0);
                msg.setData(userI);
            } else {
                msg.setErrcode(-2);
                msg.setData(null);
            }
        }else{
            if (loginInfo != null){
                UserA user = new UserA();
                user.setUserId((Integer) loginInfo.get("UserId"));
                user.setName((String)loginInfo.get("Name"));
                user.setAuthority((Integer)loginInfo.get("Authority"));
                user.setPassword((String)loginInfo.get("Password"));

                user.setCoin(2000);

                long curTick = System.currentTimeMillis();
                System.out.println("currenttick time:"+ curTick);
                user.setOnlineTick(curTick);

                userManager.addUser(user);

                msg.setErrcode(0);
                msg.setData(user);
            } else {
                msg.setErrcode(-1);
                msg.setData(null);
            }
        }
        return msg;
    }

    @PostMapping("/checkeffect")
    public Message checkEffect(@RequestBody Map<String, Object> ef) {
        return userManager.checkUserEffective((String)ef.get("tookenId"));
    }

    @PostMapping("/getshipticket")
    public Message getShipTicket(@RequestBody Map<String, Object> val) {
        //Id, Name, Price, Discout, Date, Des
        List<Map<String, Object>> allTicket = shipTick.findAll();

        List<Ticket> tiketList = ticketManager.getTiketList();
        tiketList.clear();

        for (Map<String, Object> ticketInfo : allTicket) {
            Ticket ticket = new Ticket();
            ticket.ShipTableValueToTicket(ticketInfo);
            tiketList.add(ticket);
        }

        Message msg = new Message();
        msg.setErrcode(1);
        msg.setData(allTicket);
        return msg;
    }

    @PostMapping("/getUserAllTicket")
    public Message getUserAllTicket(@RequestBody Map<String, Object> val) {
        String tookenId = (String)val.get("tookenId");
        Message msg = userManager.checkUserEffective(tookenId);
        if (msg.getErrcode() < 0) return msg;

        UserA user = userManager.getUserInfo(tookenId);
        List<Map<String, Object>> userAllTicket = shipTick.getUserAllTicket(user.getUserId());
        List<Ticket> ticketlist = user.getTickets();
        ticketlist.clear();

        for (Map<String, Object> ticketInfo : userAllTicket) {
            Ticket ticket = new Ticket();
            ticket.ShipTableValueToTicket(ticketInfo);
            ticketlist.add(ticket);
        }

        msg.setErrcode(0);
        msg.setData(user);
        return msg;
    }

    @PostMapping("/buyTicket")
    public Message buyTicket(@RequestBody Map<String, Object> val) {
        String tookenId = (String)val.get("tookenId");
        Integer ticketId = (Integer)val.get("ticketId");
        Message msg = userManager.checkUserEffective(tookenId);
        if (msg.getErrcode() < 0) return msg;

        UserA user = userManager.getUserInfo(tookenId);
        Ticket ticketInfo = ticketManager.getTicketInfo(ticketId);
        if (ticketInfo == null) {
            msg.setErrcode(-2);
            msg.setData(null);
            return msg;
        }

        //判断是否够钱
        if (user.getCoin() < ticketInfo.getPrice()) {
            msg.setErrcode(-1);
            msg.setData(null);
        } else {
            msg.setErrcode(0);
            msg.setData(null);

            shipTick.buyTicket(user.getUserId(), ticketInfo.getId());
        }
        return msg;
    }
}
