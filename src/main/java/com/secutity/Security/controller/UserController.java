package com.secutity.Security.controller;

import com.secutity.Security.models.Role;
import com.secutity.Security.models.User;
import com.secutity.Security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

  @PostMapping("/addRole")
  public ResponseEntity<Role> addRole(@RequestBody Role role){

      URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/users/addRole").toUriString());

      return  ResponseEntity.created(uri).body(userService.createRole(new Role(role.getRoleName())));
  }
    @GetMapping("/")

    public List<User> listUser(){
        List<User> list=this.userService.list();
        return list;
    }
    @DeleteMapping("/{userId}")
    public void deleteuser(@PathVariable("userId")Long userId)
    {
        this.userService.deleteUser(userId);
    }



}