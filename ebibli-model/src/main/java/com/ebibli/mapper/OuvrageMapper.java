package com.ebibli.mapper;

import com.ebibli.dto.OuvrageDto;
import com.ebibli.domain.Ouvrage;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OuvrageMapper {

    OuvrageDto map(Ouvrage ouvrage);
    Ouvrage map(OuvrageDto ouvrageDto);

    default List<OuvrageDto> ouvragesToouvrageDtos(List<Ouvrage> ouvrages) {
        List<OuvrageDto> ouvrageDtoList = new ArrayList<>();
        for (Ouvrage ouvrage : ouvrages) {
            ouvrageDtoList.add(map(ouvrage));
        }
        return ouvrageDtoList;
    }
}
