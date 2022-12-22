package com.secutity.Security.controller;
import com.secutity.Security.config.JwtUtils;
import com.secutity.Security.models.*;
import com.secutity.Security.payload.request.LoginRequest;
import com.secutity.Security.payload.request.SignupRequest;
import com.secutity.Security.payload.response.MessageResponse;
import com.secutity.Security.repo.RoleRepository;
import com.secutity.Security.repo.UserRepository;
import com.secutity.Security.services.UserService;
import com.secutity.Security.services.impl.UserDetailsImpl;
import com.secutity.Security.services.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.secutity.Security.payload.response.JwtResponse;
@RestController
 @RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticateController {
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest)   {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        signUpRequest.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        Set<Role> roles=new HashSet<>();
        Set<String> strRoles = signUpRequest.getRole();
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName("USER");
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepository.findByRoleName("ADMIN");
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByRoleName("MOD");
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName("USER");
                        roles.add(userRole);
                }
            });
        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)  {
            Authentication authentication =   authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String token=jwtUtils.generateToken((UserDetailsImpl) authentication.getPrincipal());
        List<String> roles = ((UserDetailsImpl) authentication.getPrincipal()).getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(token,
                ((UserDetailsImpl) authentication.getPrincipal()).getId(),
                ((UserDetailsImpl) authentication.getPrincipal()).getUsername(),
                ((UserDetailsImpl) authentication.getPrincipal()).getEmail(),
                roles));
    }

}
