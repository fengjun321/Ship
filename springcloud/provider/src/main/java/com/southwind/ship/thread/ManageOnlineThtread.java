package com.southwind.ship.thread;

import com.southwind.ship.entity.UserA;
import com.southwind.ship.entity.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Map;

public class ManageOnlineThtread extends Thread {
    private Thread t;
    private String threadName;

    private UserManager userManager;

    public ManageOnlineThtread(String name, UserManager us) {
        threadName = name;
        userManager = us;
        System.out.println("Creating " +  threadName );
        start();
    }

    public void run() {
        System.out.println("Running " +  threadName );
        try {
            Integer i = 1;
            while (true) {
                //System.out.println("Thread: " + threadName + ", " + i);
                // 让线程睡眠一会
                if (userManager == null) {
                    continue;
                }
                Map<String, UserA> umap = userManager.getUserMap();

                if (umap != null)
                    //服务器用户下线
                    for (Map.Entry<String, UserA> entry: umap.entrySet()) {
                        UserA user = entry.getValue();
                        if (System.currentTimeMillis() - user.getOnlineTick() > 500000) {
                            System.out.println("服务器用户下线:"+ user.getName());
                            umap.remove(entry.getKey());
                        }
                }

                Thread.sleep(5000);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
