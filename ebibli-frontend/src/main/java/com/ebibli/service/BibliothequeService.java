package com.ebibli.service;

import com.ebibli.domain.BibliothequeClient;
import com.ebibli.dto.BibliothequeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibliothequeService {

    private final BibliothequeClient biblioClients;

    public BibliothequeService(BibliothequeClient biblioClients) {
        this.biblioClients = biblioClients;
    }

    public List<BibliothequeDto> getAllBibliotheques() {
        return biblioClients.getAllBibliotheques();
    }

    public BibliothequeDto getBibliotheque(Integer bibliothequeId) {
        return biblioClients.getBibliotheque(bibliothequeId);
    }
}
