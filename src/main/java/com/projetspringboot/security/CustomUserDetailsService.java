package com.projetspringboot.security;

import com.projetspringboot.entity.Utilisateur;
import com.projetspringboot.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        String role = utilisateur.getRole() != null ? utilisateur.getRole().name() : "ETUDIANT";
        String password = utilisateur.getPassword() != null ? utilisateur.getPassword() : "";

        return User.builder()
                .username(utilisateur.getEmail())
                .password(password)
                .roles(role)
                .build();
    }
}
