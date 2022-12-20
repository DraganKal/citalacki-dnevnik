package com.citalacki_dnevnik.server.controller;

import com.citalacki_dnevnik.server.model.auth.AuthRequest;
import com.citalacki_dnevnik.server.model.auth.AuthResponse;
import com.citalacki_dnevnik.server.service.user.UserService;
import com.citalacki_dnevnik.server.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
    Controller used for generating the token
 */
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorizationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    //should be deleted
    @GetMapping("/")
    @PreAuthorize("hasPermission('test', 'read')")
    public void get() {
        System.out.println("hello world");
    }

    @PostMapping("/authenticate")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        return userService.generateAuthResponse(authRequest.getUsername());
    }
}
