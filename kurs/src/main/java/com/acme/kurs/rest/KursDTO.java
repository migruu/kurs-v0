package com.acme.kurs.rest;

import com.acme.kurs.entity.Dozent;
import com.acme.kurs.entity.Student;

import java.util.List;

/**
 * ValueObject für das Neuanlegen und Ändern eines Kurses.
 *
 * @param kursname Name des Kurses
 * @param dozent Zugehoeriger Dozent
 * @param ects Anzahl an vergebenden ECTS
 * @param raumnummer Zugehoerige Raumnummer
 * @param beschreibung Optionale Beschreibung
 * @param studenten Liste an Studenten
 */
record KursDTO(
    String kursname,
    Dozent dozent,
    int ects,
    String raumnummer,
    String beschreibung,
    List<Student> studenten
) {
    /**
     * Marker-Interface für Jakarta Validation: zusätzliche Validierung beim Neuanlegen.
     */
    interface OnCreate { }
}
