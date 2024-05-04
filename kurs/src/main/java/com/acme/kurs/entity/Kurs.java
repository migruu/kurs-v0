package com.acme.kurs.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
public class Kurs {

    /**
     * Muster zur dreistelligen Zahlendarstellung für einen Raum
     */
    public static final String RAUMNUMMER_PATTERN = "^\\d{3}$";

    /**
     * Eindeutige ID eines Kurses
     */
    @NotNull
    private UUID id;

    /**
     * Name eines Kurses
     */
    @NotBlank
    private String kursName;

    /**
     * Name des Dozenten für den zugehörigen Kurs
     */
    private Dozent dozent;

    /**
     * Die ECTS, welche ein Kurs vergibt
     */
    @NotEmpty
    private int ects;

    /**
     * Die Raum-Nummer eines Kurses
     */
    @Pattern(regexp = RAUMNUMMER_PATTERN)
    private String raumNummer;

    /**
     * Eine Möglichkeit für eine kurze Kursbeschreibung.
     */
    private String beschreibung;

    /**
     * Liste der Mitglieder/Studierenden im Kurs
     */
    @ToString.Exclude
    @UniqueElements
    private List<Student> studenten;


}
