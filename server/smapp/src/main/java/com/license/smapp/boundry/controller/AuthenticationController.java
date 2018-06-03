package com.license.smapp.boundry.controller;


import com.license.smapp.common.DeviceProvider;
import com.license.smapp.entity.model.User;
import com.license.smapp.entity.model.UserTokenState;
import com.license.smapp.security.TokenHelper;
import com.license.smapp.security.auth.JwtAuthenticationRequest;
import com.license.smapp.control.service.impl.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DeviceProvider deviceProvider;

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,
            HttpServletResponse response,
            Device device
    ) throws AuthenticationException, IOException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        User user = (User)authentication.getPrincipal();
        String jws = tokenHelper.generateToken( user.getUsername(), device);
        int expiresIn = tokenHelper.getExpiredIn(device);
        // Return user basic info and token to store on client side
        UserTokenState userTokenState = modelMapper.map(user, UserTokenState.class);
        userTokenState.setAccessToken(jws);
        userTokenState.setExpiresIn(Long.valueOf(expiresIn));
        return ResponseEntity.ok(userTokenState);
    }
}
