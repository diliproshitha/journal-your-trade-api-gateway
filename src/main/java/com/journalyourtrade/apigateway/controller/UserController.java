package com.journalyourtrade.apigateway.controller;

import com.journalyourtrade.apigateway.model.entity.JYTUser;
import com.journalyourtrade.apigateway.model.web.AuthenticationRequest;
import com.journalyourtrade.apigateway.model.web.AuthenticationRespone;
import com.journalyourtrade.apigateway.model.web.JYTUserDto;
import com.journalyourtrade.apigateway.service.JYTUserDetailsService;
import com.journalyourtrade.apigateway.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gateway")
@AllArgsConstructor
public class UserController {

    private AuthenticationManager authenticationManager;
    private JYTUserDetailsService userDetailsService;
    private JwtUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationRespone(jwt));
    }
}
