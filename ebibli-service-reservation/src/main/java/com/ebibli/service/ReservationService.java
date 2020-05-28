package com.ebibli.service;

import com.ebibli.mapper.ReservationMapper;
import com.ebibli.repository.ReservationRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private static final ReservationMapper RESERVATION_MAPPER = Mappers.getMapper(ReservationMapper.class);

    @Autowired
    private ReservationRepository reservationRepository;

}
