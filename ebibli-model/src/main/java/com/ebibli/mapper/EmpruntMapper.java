package com.ebibli.mapper;

import com.ebibli.domain.Emprunt;
import com.ebibli.dto.EmpruntDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {UtilisateurMapper.class, LivreMapper.class})
public interface EmpruntMapper {

    Emprunt map(EmpruntDto empruntDto);
    EmpruntDto map(Emprunt emprunt);

    default List<EmpruntDto> empruntsToDtos(List<Emprunt> emprunts) {
        List<EmpruntDto> empruntDtos = new ArrayList<>();
        for (Emprunt emprunt: emprunts) {
            empruntDtos.add(map(emprunt));
        }
        return empruntDtos;
    }
}
