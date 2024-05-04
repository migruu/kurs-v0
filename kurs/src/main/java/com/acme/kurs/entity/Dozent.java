package com.acme.kurs.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Oberflächliche Daten zur Darstellung von einem Dozenten
 */
@Builder
@Getter
@Setter
public class Dozent {

    /**
     * ID eines Dozenten
     */
    @NotNull
    private UUID id;

    /**
     * Name eines Dozenten
     */
    @NotBlank
    private String name;

    /**
     * Liste möglicher Kurse eines Dozenten
     */
    //private List<Kurs> kurse;


}
