package com.southwind;
import com.southwind.ship.entity.UserManager;
import com.southwind.ship.thread.ManageOnlineThtread;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@MapperScan(basePackages = {"com.southwind.ship.repository"})
public class ProviderApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProviderApplication.class, args);
        ManageOnlineThtread thread = new ManageOnlineThtread("main thread", (UserManager)run.getBean("userManager"));

    }
}