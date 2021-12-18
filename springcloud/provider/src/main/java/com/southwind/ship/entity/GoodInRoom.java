package com.southwind.ship.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
public class GoodInRoom {
    public static List<String> goodNameList = new ArrayList<>();
    public static List<Integer> goodPrice = new ArrayList<>();
    static {
        goodNameList.add("肥皂");
        goodNameList.add("风筒");
        goodNameList.add("牙刷");
        goodNameList.add("保洁");
        goodNameList.add("其它");

        goodPrice.add(11);
        goodPrice.add(43);
        goodPrice.add(34);
        goodPrice.add(20);
        goodPrice.add(2);
    }
}
