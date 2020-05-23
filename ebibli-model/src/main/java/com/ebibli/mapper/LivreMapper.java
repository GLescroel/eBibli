package com.ebibli.mapper;

import com.ebibli.dto.LivreDto;
import com.ebibli.domain.Livre;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {BibliothequeMapper.class, OuvrageMapper.class})
public interface LivreMapper {

    Livre map(LivreDto livreDto);
    LivreDto map(Livre livre);

    default List<LivreDto> livresToLivreDtos(List<Livre> livres) {
        List<LivreDto> livreDtoList = new ArrayList<>();
        for (Livre livre : livres) {
            livreDtoList.add(map(livre));
        }
        return livreDtoList;
    }
}
