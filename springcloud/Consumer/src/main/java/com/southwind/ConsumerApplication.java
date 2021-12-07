package com.southwind;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext contex = SpringApplication.run(ConsumerApplication.class, args);
        String[] arrayname = contex.getBeanDefinitionNames();
        for(String name: arrayname){
            System.out.println("contex value:"+ name);
        }
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}