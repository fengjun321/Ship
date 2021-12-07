package com.southwind.controller;
import com.southwind.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Target;
import java.util.Collection;
@RestController
@RequestMapping("/consumer")
public class StudentHandler {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${server.port}")
    private String port;

    @GetMapping("index")
    public String index(){
        return "consumer"+port;
    }

    @GetMapping("/findAll")
    public Collection<Student> findAll(){
        return restTemplate.getForObject("http://eureka-svc:8010/provider/findAll",Collection.class);
    }
    @GetMapping("/findById/{id}")
    public Student findById(@PathVariable("id") Integer id){
        return restTemplate.getForObject("http://eureka-svc:8010/provider/findById/{id}",Student.class,id);
    }
    @PostMapping("/save")
    public void save(@RequestBody Student student){

        restTemplate.postForObject("http://eureka-svc:8010/provider/save",student, Integer.class);
    }
    @PutMapping("/update")
    public void update(@RequestBody Student student){
        restTemplate.put("http://eureka-svc:8010/provider/update",student);
    }
    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){

        restTemplate.delete("http://eureka-svc:8010/provider/deleteById/{id}",id);
    }
}