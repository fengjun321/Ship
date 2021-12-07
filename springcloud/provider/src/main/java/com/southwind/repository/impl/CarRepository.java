package com.southwind.repository.impl;

import com.southwind.entity.Car;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CarRepository {
    private Map<Integer, Car> map;

    CarRepository(){
        if (map == null){
            ProduceCar();
            System.out.println("Car Repository has be construct");
        }
        System.out.println("Car Repository has be finish");
    }

    public void ProduceCar(){
        map = new HashMap<Integer, Car>();
        map.put(1, new Car(1, "fute"));
        map.put(2, new Car(2, "laosilaishi"));
        map.put(3, new Car(3, "benchi"));
    }

    public Collection<Car> GetAllCar(){
        Collection<Car> collec = map.values();
        return collec;
    }
}
