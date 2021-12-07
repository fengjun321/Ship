package com.southwind.ship.entity;

import com.southwind.ship.entity.Message;
import com.southwind.ship.util.Md5;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sun.security.provider.MD4;
import sun.security.provider.MD5;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Component
@Scope("singleton")
public class UserManager {
    private Integer tookSeed;
    private Map<String ,UserA> umap;

    public UserManager() {
        tookSeed = 793434;
        umap = new HashMap<String ,UserA>();
        System.out.println("usermanager construct finish");
    }

    //录入用户信息
    public String addUser(UserA user) {
        tookSeed += 1;
        String md5Str = Md5.getMD5Str(String.valueOf(tookSeed));
        user.setTookenId(md5Str);
        umap.put(md5Str, user);
        return md5Str;
    }

    public Map<String ,UserA> getUserMap() {
        return umap;
    }
    //判断是否用户已被录入(返回msg，msg直接给前端)
    public Message checkUserEffective(String tookenId) {
        Message msg = new Message();
        UserA userA = umap.get(tookenId);
        if (userA != null) {
            userA.setOnlineTick(System.currentTimeMillis());
            System.out.println("用户心跳更新:" + userA.getName() + " =>" + userA.getOnlineTick());
            msg.setErrcode(0);
        } else {
            msg.setErrcode(-99);
        }
        msg.setData(null);

        return msg;
    }

    //获取用户信息
    public UserA getUserInfo(String tookenId) {
        return umap.get(tookenId);
    }

    //通过用户名查找用户
    public UserA getUserByName(String username) {
        for (Map.Entry<String, UserA> value:umap.entrySet()) {
            UserA user = value.getValue();
            if (username.equals(user.getName())) {
                return user;
            }
        }
        return null;
    }
}
