package com.ankit.twitter.controller;

import com.ankit.twitter.config.JwtProvider;
import com.ankit.twitter.exception.UserException;
import com.ankit.twitter.model.User;
import com.ankit.twitter.model.Verification;
import com.ankit.twitter.repository.UserRespository;
import com.ankit.twitter.service.CustomUserDetailsServiceImpl;
import com.ankit.twitter.util.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    CustomUserDetailsServiceImpl customUserDetailsService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody User user) throws UserException
    {
        String email=user.getEmail();
        String password=user.getPassword();
        String fullName=user.getFullName();
        String birthDate=user.getBirthDate();
        User isEmailExist=userRespository.findByEmail(email);
        if(isEmailExist!=null)
        {
            throw new UserException("Email already exist");
        }
        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setBirthDate(birthDate);
        createdUser.setFullName(fullName);
        createdUser.setVerification(new Verification());
        User savedUser=userRespository.save(createdUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse(token,true);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody User user) throws UserException
    {
        String userName=user.getEmail();
        String password=user.getPassword();
        Authentication authentication=authenticate(userName,password);
        String token=jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse(token,true);
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails=customUserDetailsService.loadUserByUsername(userName);
        if(userDetails==null)
        {
            throw new BadCredentialsException("Invalid UserName");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid UserName or Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());

    }


}
