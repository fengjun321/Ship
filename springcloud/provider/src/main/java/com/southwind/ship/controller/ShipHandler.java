package com.southwind.ship.controller;

import com.southwind.ship.entity.*;
import com.southwind.ship.repository.Login;
import com.southwind.ship.repository.ShipRepository;
import com.southwind.ship.repository.ShipTicket;
import org.apache.commons.lang.ObjectUtils;
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

    @PostMapping("/getRecordCost")
    public Message GetRecordCost() {
        Message msg = new Message();
        msg.setErrcode(1);
        msg.setData(shipRepository.GetRecordCost());
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
        System.out.println(lo);
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

    @PostMapping("/getAllRoom")
    public Message getAllRoom(@RequestBody Map<String, Object> val) {
        Integer shipId = (Integer)val.get("shipId");
        List<Map<String, Object>> roomList = shipTick.getAllRoom(shipId);

        for (Map<String, Object> room : roomList) {
            List<Room> allRoom = ticketManager.getRoomList();
            Room curRoom = null;
            for (Room room2 : allRoom) {
                if (room2.getShipId().equals((Integer) room.get("ShipId"))) {
                    curRoom = room2;
                }
            }
            if (curRoom == null) {
                Room newRoom = new Room();
                newRoom.setShipId((Integer) room.get("ShipId"));
                newRoom.setRoomRange((Integer) room.get("RoomRange"));
                newRoom.setCashPledge((Integer)room.get("CashPledge"));
                List<Integer> priceLev = newRoom.getPriceLev();
                String priceLev1 = (String)room.get("PriceLev");
                String[] priceList = priceLev1.split(",");
                for (String price: priceList) {
                    priceLev.add(Integer.parseInt(price));
                }

                List<Integer> reserve1 = newRoom.getReserve();
                String reserve = (String)room.get("Reserve");
                String[] reList = reserve.split(",");
                for (String re : reList) {
                    reserve1.add(Integer.parseInt(re));
                }
                allRoom.add(newRoom);

                Message msg = new Message();
                msg.setErrcode(0);
                msg.setData(newRoom);
                return msg;
            } else {
                Message msg = new Message();
                msg.setErrcode(0);
                msg.setData(curRoom);
                return msg;
            }
        }

        Message msg = new Message();
        msg.setErrcode(-1);
        msg.setData(null);
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

    //房间消耗物品登记
    @PostMapping("/recordCost")
    public Message roomCostRecord(@RequestBody Map<String, Object> val) {
        String tookenId = (String)val.get("tookenId");
        Integer ticketId = (Integer)val.get("ticketId");
        Integer goodId = (Integer)val.get("goodId");
        Message msg = userManager.checkUserEffective(tookenId);
        if (msg.getErrcode() < 0) return msg;

        String goodName = "";
        Integer goodPrice = 0;
        UserA user = userManager.getUserInfo(tookenId);
        int size = GoodInRoom.goodNameList.size();
        for (int i = 0; i < size; i++) {
            if (goodId.equals(i)) {
                goodName = GoodInRoom.goodNameList.get(i);
                goodPrice = GoodInRoom.goodPrice.get(i);
            }
        }
        if (goodName.isEmpty()) {
            goodName = GoodInRoom.goodNameList.get(size-1);
            goodPrice = GoodInRoom.goodPrice.get(size-1);
        }

        Ticket ticketInfo = ticketManager.getTicketInfo(ticketId);
        String shipName = ticketInfo.getName();
        Integer roomId = user.GetUserRoomByTicketId(ticketId);
        shipRepository.RecordCost(ticketId, shipName, roomId, user.getUserId(),
                user.getName(), goodId, goodName, goodPrice);

        msg.setErrcode(0);
        msg.setData(null);
        return msg;
    }

    @PostMapping("/buyTicket")
    public Message buyTicket(@RequestBody Map<String, Object> val) {
        String tookenId = (String)val.get("tookenId");
        Integer ticketId = (Integer)val.get("ticketId");
        Integer roomId = (Integer)val.get("roomId");
        Message msg = userManager.checkUserEffective(tookenId);
        if (msg.getErrcode() < 0) return msg;

        UserA user = userManager.getUserInfo(tookenId);
        Ticket ticketInfo = ticketManager.getTicketInfo(ticketId);
        if (ticketInfo == null) {
            msg.setErrcode(-2);
            msg.setData(null);
            return msg;
        }

        //判断房间是否可以购买
        List<Room> roomList = ticketManager.getRoomList();
        Room curRoom = null;
        for (Room room : roomList) {
            if (room.getShipId().equals(ticketId)) {
                curRoom = room;
                break;
            }
        }

        if (curRoom == null) {
            msg.setErrcode(-3);
            msg.setData(null);
            return msg;
        }

        List<Integer> reserveList = curRoom.getReserve();
        for (Integer reserve : reserveList) {
            if (reserve.equals(roomId)) {
                msg.setErrcode(-4);
                msg.setData(null);
                return msg;
            }
        }

        //检验玩家是否够贱 todo
        //房间预定
        reserveList.add(roomId);
        List<Ticket> ticketlist = user.getTickets();
        ticketlist.add(ticketInfo);

        StringBuilder strBuild = new StringBuilder();
        boolean flagdot = false;
        for (Integer r1 : reserveList) {
            if (flagdot) {
                strBuild.append(",");
            } else
                flagdot = true;
            strBuild.append(r1);
        }
        //更新已预定房间表
        shipTick.updateRoomManage(ticketId, strBuild.toString());

        //判断是否够钱
        if (user.getCoin() < ticketInfo.getPrice()) {
            msg.setErrcode(-1);
            msg.setData(null);
        } else {
            msg.setErrcode(0);
            msg.setData(null);

            shipTick.buyTicket(user.getUserId(), ticketInfo.getId(), roomId);
        }
        return msg;
    }
}
