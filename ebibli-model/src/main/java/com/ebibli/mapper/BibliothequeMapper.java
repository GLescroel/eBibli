package com.ebibli.mapper;

import com.ebibli.dto.BibliothequeDto;
import com.ebibli.domain.Bibliotheque;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface BibliothequeMapper {

    Bibliotheque map(BibliothequeDto bibliothequeDto);
    BibliothequeDto map(Bibliotheque bibliotheque);

    default List<BibliothequeDto> bibliothequesToBibliothequeDtos(List<Bibliotheque> bibliotheques) {
        List<BibliothequeDto> bibliothequeDtoList = new ArrayList<>();
        for (Bibliotheque bibliotheque : bibliotheques) {
            bibliothequeDtoList.add(map(bibliotheque));
        }
        return bibliothequeDtoList;

    }
}
