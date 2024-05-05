package com.acme.kurs.rest;

import java.util.List;

/**
 * ValueObject für das Neuanlegen und Ändern eines Kurses.
 *
 * @param kursname Name des Kurses
 * @param dozent Zugehöriger Dozent
 * @param ects Anzahl an vergebenden ECTS
 * @param raumNummer Zugehörige Raumnummer
 * @param beschreibung Optionale Beschreibung
 * @param studenten Liste an Studenten
 */
record KursDTO(
    String kursname,
    DozentDTO dozent,
    int ects,
    String raumNummer,
    String beschreibung,
    List<StudentDTO> studenten

) {
}
