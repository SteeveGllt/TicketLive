package com.steeve.ticketlive.service;

import com.steeve.ticketlive.dto.user.InscriptionRequest;
import com.steeve.ticketlive.dto.user.UserResponse;
import com.steeve.ticketlive.model.User;
import com.steeve.ticketlive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse signUp(InscriptionRequest inscriptionRequest){
        if (userRepository.existsByEmail(inscriptionRequest.getEmail())) {
            throw new RuntimeException("Un compte existe déjà avec cet email");
        }
        User user = new User();
        user.setNom(inscriptionRequest.getNom());
        user.setPrenom(inscriptionRequest.getPrenom());
        user.setEmail(inscriptionRequest.getEmail());
        user.setPassword(passwordEncoder.encode(inscriptionRequest.getPassword()));

        User sauvegarde = userRepository.save(user);
        return toResponse(sauvegarde);
    }

    private UserResponse toResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setNom(user.getNom());
        response.setPrenom(user.getPrenom());
        response.setEmail(user.getEmail());

        return response;
    }

}
