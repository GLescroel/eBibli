package com.ebibli.mapper;

import com.ebibli.domain.Reservation;
import com.ebibli.dto.ReservationDto;
import org.mapstruct.Mapper;

@Mapper
public interface ReservationMapper {

    ReservationDto map(Reservation reservation);
    Reservation map(ReservationDto reservation);
}
