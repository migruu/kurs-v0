package com.acme.kurs.rest;


import com.acme.kurs.entity.Dozent;
import com.acme.kurs.entity.Kurs;
import com.acme.kurs.entity.Student;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

/**
 * Model-Klasse für Sring - HATEOS.
 */
@JsonPropertyOrder({
    "kursName", "dozent", "ects", "raumNummer", "beschreibung", "studenten"
})
@Relation(collectionRelation = "kurse", itemRelation = "kurs")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@ToString(callSuper = true)
public class KursModel extends RepresentationModel<KursModel> {
    private final String kursName;

    private final Dozent dozent;
    private final int ects;
    private String raumNummer;
    private String beschreibung;
    private List<Student> studenten;

    KursModel(final Kurs kurs) {
        kursName = kurs.getKursName();
        dozent = kurs.getDozent();
        ects = kurs.getEcts();
        raumNummer = kurs.getRaumNummer();
        beschreibung = kurs.getBeschreibung();
        studenten = kurs.getStudenten();

    }
}
