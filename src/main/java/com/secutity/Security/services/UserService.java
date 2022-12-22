package com.secutity.Security.services;


import com.secutity.Security.models.Role;
import com.secutity.Security.models.User;


import java.util.List;
import java.util.Optional;

public interface UserService {
    public Optional<User> getUser(String username);
    public List<User> list();
    public void deleteUser (Long userId);
     public Role createRole(Role role);
}