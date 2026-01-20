package com.projetspringboot.config;

import com.projetspringboot.entity.Administrateur;
import com.projetspringboot.entity.Etudiant;
import com.projetspringboot.entity.Formateur;
import com.projetspringboot.entity.Role;
import com.projetspringboot.repository.AdministrateurRepository;
import com.projetspringboot.repository.EtudiantRepository;
import com.projetspringboot.repository.FormateurRepository;
import com.projetspringboot.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UtilisateurRepository utilisateurRepository,
            AdministrateurRepository administrateurRepository,
            EtudiantRepository etudiantRepository,
            FormateurRepository formateurRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (utilisateurRepository.count() == 0) {
                System.out.println("Initializing Data...");

                // Admin
                Administrateur admin = new Administrateur();
                admin.setNom("Admin");
                admin.setPrenom("Super");
                admin.setEmail("admin@school.com");
                admin.setPassword(passwordEncoder.encode("password"));
                admin.setRole(Role.ADMIN);
                administrateurRepository.save(admin);

                // Formateur
                Formateur prof = new Formateur();
                prof.setNom("Turing");
                prof.setPrenom("Alan");
                prof.setEmail("alan@school.com");
                prof.setPassword(passwordEncoder.encode("password"));
                prof.setRole(Role.FORMATEUR);
                prof.setSpecialite("Informatique");
                formateurRepository.save(prof);

                // Etudiant
                Etudiant student = new Etudiant();
                student.setNom("Curie");
                student.setPrenom("Marie");
                student.setEmail("marie@school.com");
                student.setPassword(passwordEncoder.encode("password"));
                student.setRole(Role.ETUDIANT);
                student.setMatricule("S2024001");
                etudiantRepository.save(student);

                System.out.println("Data Initialized: admin@school.com / password");
            }
        };
    }
}
