package com.ebibli.service;

import com.ebibli.domain.UtilisateurClient;
import com.ebibli.dto.UtilisateurDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurClient utilisateurClient;

    public UtilisateurService(UtilisateurClient utilisateurClient) {
        this.utilisateurClient = utilisateurClient;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Objects.requireNonNull(email);
        return getUtilisateurByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<UtilisateurDto> getUtilisateurByEmail(String email) {
        return Optional.ofNullable(findUtilisateurByEmail(email));
    }

    public UtilisateurDto findUtilisateurByEmail(String email) {
        return utilisateurClient.getUtilisateurByEmail(email);
    }

    public UtilisateurDto save(UtilisateurDto utilisateur) {
        return utilisateurClient.save(utilisateur);
    }

    public Boolean remove(UtilisateurDto utilisateur) {
        return utilisateurClient.delete(utilisateur);
    }
}
