package com.ebibli.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OuvrageDto {

    private Integer id;
    private String titre;
    private String resume;
    private String image;
    private List<Disponibilite> disponibilite;
}
