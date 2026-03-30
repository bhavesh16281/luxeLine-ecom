package com.bhavesh16281.ecommerce.luxeLine_ecom.controller;

import com.bhavesh16281.ecommerce.luxeLine_ecom.model.AppRole;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.Role;
import com.bhavesh16281.ecommerce.luxeLine_ecom.model.User;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.RoleRepository;
import com.bhavesh16281.ecommerce.luxeLine_ecom.repositories.UserRepository;
import com.bhavesh16281.ecommerce.luxeLine_ecom.security.jwt.JwtUtils;
import com.bhavesh16281.ecommerce.luxeLine_ecom.security.request.LoginRequest;
import com.bhavesh16281.ecommerce.luxeLine_ecom.security.request.SignupRequest;
import com.bhavesh16281.ecommerce.luxeLine_ecom.security.response.MessageResponse;
import com.bhavesh16281.ecommerce.luxeLine_ecom.security.response.UserInfoResponse;
import com.bhavesh16281.ecommerce.luxeLine_ecom.security.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Tag(name = "Authentication API's", description = "Endpoints for user authentication and registration")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        assert userDetails != null;
//        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails); //This line is part of JWT token based authentication
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails); //This line is part of JWT Cookie based authentication
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                    userDetails.getUsername(), roles); //This line is part of JWT Cookie based authentication
//                userDetails.getUsername(), roles, jwtToken);//This line is part of JWT token based authentication

//        return ResponseEntity.ok(response);//This line is part of JWT token based authentication
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response); //This line is part of JWT Cookie based authentication
    }

    @Tag(name = "Authentication API's", description = "Endpoints for user authentication and registration")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody  SignupRequest signUpRequest) {

        if(userRepository.existsByUserName(signUpRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null || strRoles.isEmpty()){
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }else{
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(sellerRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Tag(name = "Authentication API's", description = "Endpoints for user authentication and registration")
    @GetMapping("/username")
    public String currentUsername(Authentication authentication) {
        if(authentication != null){
            return authentication.getName();
        }else{
            return "";
        }
    }

    @Tag(name = "Authentication API's", description = "Endpoints for user authentication and registration")
    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> currentUserDetails(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(), roles);

        return ResponseEntity.ok(response);
    }

    @Tag(name = "Authentication API's", description = "Endpoints for user authentication and registration")
    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser(){
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("You have been signed out!"));

    }
}
