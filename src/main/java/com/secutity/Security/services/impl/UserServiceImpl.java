package com.secutity.Security.services.impl;

import com.secutity.Security.helper.UserFoundException;
import com.secutity.Security.models.Role;
import com.secutity.Security.models.User;
import com.secutity.Security.repo.RoleRepository;
import com.secutity.Security.repo.UserRepository;
import com.secutity.Security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;




    @Override
    public Optional<User> getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> list() {
        return this.userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public Role createRole(Role role){
        return roleRepository.save(role);
    }
}