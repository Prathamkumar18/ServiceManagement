package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.AuthenticationRequest;
import com.example.ServiceManagement.dto.SignupRequestDTO;
import com.example.ServiceManagement.dto.UserDto;
import com.example.ServiceManagement.entity.User;
import com.example.ServiceManagement.repository.UserRepository;
import com.example.ServiceManagement.services.authentication.AuthService;
import com.example.ServiceManagement.services.authentication.jwt.UserDetailsServiceImpl;
import com.example.ServiceManagement.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("WELCOME!!!",HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/client/sign-up")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO){
        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Client already exist with email!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUser=authService.signupClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO){
        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Company already exist with email!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUser=authService.signupCompany(signupRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws JSONException, IOException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password",e);
        }
        final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt=jwtUtil.generateToken(userDetails.getUsername());
        User user=userRepository.findByEmail(authenticationRequest.getUsername());
        response.getWriter().write(new JSONObject()
                .put("userId",user.getId())
                .put("role",user.getRole())
                .toString());
        response.addHeader("Access-Control-Expose-Headers","Authorization");
        response.addHeader("Access-Control-Allow-Headers","Authorization,"+" X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        response.addHeader(HEADER_STRING,TOKEN_PREFIX+jwt);
    }
}
