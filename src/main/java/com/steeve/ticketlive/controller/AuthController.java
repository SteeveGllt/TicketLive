package com.steeve.ticketlive.controller;

import com.steeve.ticketlive.dto.JwtResponseDto;
import com.steeve.ticketlive.dto.security.JwtUtils;
import com.steeve.ticketlive.dto.security.requests.LoginRequest;
import com.steeve.ticketlive.dto.security.services.UserDetailsImpl;
import com.steeve.ticketlive.dto.user.InscriptionRequest;
import com.steeve.ticketlive.dto.user.UserResponse;
import com.steeve.ticketlive.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthService authService; // garde ta méthode inscrire() telle quelle

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody InscriptionRequest inscriptionRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(inscriptionRequest));
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.genererToken(authentication);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponseDto(jwt, principal.getId(), principal.getUsername(), principal.getEmail(), roles));
    }

}
