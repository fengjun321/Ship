package com.southwind.controller;

import com.southwind.entity.Car;
import com.southwind.repository.impl.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping("/Car")
public class CarHandler {
    private static ArrayList carInfo;
    static {
        carInfo = new ArrayList<Car>();
        carInfo.add(new Car(1,"baoma"));
        carInfo.add(new Car(2, "xiaoming"));
        carInfo.add(new Car(3, "dazhong"));
        carInfo.add(new Car(4, "biyadi"));
        carInfo.add(new Car(5, "jili"));
        carInfo.add(new Car(6, "benchi"));
        carInfo.add(new Car(7, "lanbojili"));
    }

    @Autowired
    CarRepository repository;

    @GetMapping("/initCar")
    String InitCard(){
        repository.ProduceCar();
        return "init success jj";
    }

    @GetMapping("/findAllCar")
    Collection<Car> FindAllCard(){
        return repository.GetAllCar();
    }

    @GetMapping("/RandGetCard")
    Collection<Car> RandGetCard(){
        Random random = new Random();
        Map map = new HashMap();
        for (int i = 0; i<3; i++) {
            int seed = random.nextInt(50) % carInfo.size();
            Object o = carInfo.get(seed);
            map.put(i+1, o);
        }
        return map.values();
    }

    @GetMapping("/dowhat")
    private String DoWhat(){
        return "redirect:/Car/findAllCar";
    }
}
