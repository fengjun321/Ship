package com.southwind.controller;

import com.southwind.entity.Message;
import com.southwind.entity.User;
//import com.southwind.mybatis.repository.UserRepository;
import com.southwind.mybatis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mybatis")
public class MyBatisHandler {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/findAll")
    public Message findALl() {
        Message msg = new Message();
        msg.setErrcode(1);
        msg.setData(userRepository.findAll());
        return msg;
    }
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
    @GetMapping("/findById/{id}")
    public User findById(@PathVariable("id") Integer id) {
        return userRepository.findById(id);
    }
    @PostMapping("/save")
    public int save(@RequestBody User user) {
        return userRepository.save(user);
    }
    @PutMapping("/update")
    public int update(@RequestBody User user){
        return userRepository.update(user);
    }
    @DeleteMapping("/deleteById/{id}")
    public int deleteById(@PathVariable("id") Integer id){
        return userRepository.deleteById(id);
    }
}
