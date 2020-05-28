package com.ebibli.service;

import com.ebibli.dto.ReservationDto;
import com.ebibli.mapper.ReservationMapper;
import com.ebibli.repository.ReservationRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private static final ReservationMapper RESERVATION_MAPPER = Mappers.getMapper(ReservationMapper.class);

    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationDto> getAllReservationsByOuvrage(Integer ouvrageId) {
        return RESERVATION_MAPPER.reservationsToDtos(reservationRepository.findAllByOuvrage_IdOrderByDateReservation(ouvrageId));
    }
}
