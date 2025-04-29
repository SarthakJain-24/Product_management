package com.example.productmanagement.controller;

import com.example.productmanagement.bean.Response;
import com.example.productmanagement.exception.PMMessage;
import com.example.productmanagement.security.JwtUtil;
import com.example.productmanagement.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public Response generateToken(@RequestParam String username, @RequestParam String password) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new Response(PMMessage.SUCCESS, jwtUtil.generateToken(userDetails.getUsername()));
    }
}