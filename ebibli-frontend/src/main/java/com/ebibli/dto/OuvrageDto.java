package com.ebibli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OuvrageDto {

    private static final Logger LOGGER = LoggerFactory.getLogger(OuvrageDto.class);

    private Integer id;
    private String titre;
    private String resume;
    private String image;
    private List<Disponibilite> disponibilite;
    private List<ReservationDto> reservations;
    private Integer reservationListSizeMax;
    private Date nextRetourPrevu;
    private Boolean reservationAvailable;
    private Boolean empruntEnCours;
    private Boolean reservationEnCours;

}
