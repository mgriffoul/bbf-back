package com.griffoul.mathieu.bbfback.authentication.jwt.controllers;

import com.griffoul.mathieu.bbfback.authentication.jwt.exception.JwtAuthenticationException;
import com.griffoul.mathieu.bbfback.authentication.jwt.model.JwtRequest;
import com.griffoul.mathieu.bbfback.authentication.jwt.model.JwtResponse;
import com.griffoul.mathieu.bbfback.authentication.jwt.service.JwtTokenService;
import com.griffoul.mathieu.bbfback.authentication.jwt.service.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (JwtAuthenticationException e) {
            logger.error(e.getMessage(), e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws JwtAuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new JwtAuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new JwtAuthenticationException("ERROR JWT INVALID_CREDENTIALS", e);
        }
    }


}
