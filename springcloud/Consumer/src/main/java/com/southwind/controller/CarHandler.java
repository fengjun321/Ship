package com.southwind.controller;

import com.southwind.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@RestController
@RequestMapping("/carhandler")
public class CarHandler {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/findall")
    public Collection<Car> FindAll(){
        return restTemplate.getForObject("http://eureka-svc:8010/Car/findAllCar",Collection.class);
    }
}
