package com.southwind.controller;
import com.southwind.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Collection;

@RestController
@RequestMapping("/ribbon")
public class RibbonHandler {
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/findAll")
    public Collection<Student> findAll(){
        return
                restTemplate.getForObject("http://provider/provider/findAll",Collection.class);
    }
    @GetMapping("/index")
    public String index(){
        return
                restTemplate.getForObject("http://consumer/consumer/index",String.class);
    }

}