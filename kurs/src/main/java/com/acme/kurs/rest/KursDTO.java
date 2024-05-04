package com.acme.kurs.rest;

import com.acme.kurs.entity.Dozent;

import java.util.List;


record KursDTO(
    String kursname,
    Dozent dozent,
    int ects,
    String raumNummer,
    String beschreibung,
    List<StudentDTO> studenten

) {
}
