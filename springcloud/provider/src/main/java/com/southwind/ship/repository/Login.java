package com.southwind.ship.repository;

import java.util.Map;

public interface Login {
    public Map<String, Object> register(Map<String, Object> regInfo);
    public Map<String, Object> login(String username, String password);
}
