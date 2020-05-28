package com.ebibli.mapper;

import com.ebibli.domain.Reservation;
import com.ebibli.dto.ReservationDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ReservationMapper {

    ReservationDto map(Reservation reservation);
    Reservation map(ReservationDto reservation);

    default List<ReservationDto> reservationsToDtos(List<Reservation> reservations) {
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtos.add(map(reservation));
        }
        return reservationDtos;
    }
}
