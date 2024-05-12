package com.acme.kurs.repository;

import com.acme.kurs.entity.Dozent;
import com.acme.kurs.entity.Student;
import com.acme.kurs.entity.Kurs;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class DB {

    /**
     * private constructor to prevent instantiation
     */
    private DB() {
    }

    /**
     * Aufruf der Liste für Studenten
     */
    static final List<Student> STUDENTS = getStudents();

    /**
     * Erstellung der Liste von Studenten
     *
     * @return Liste
     */
    private static List<Student> getStudents() {
        return Stream.of(
            Student.builder()
                .name("Alpha")
                .matrikelNr("00001")
                .build(),
            Student.builder()
                .name("Beta")
                .matrikelNr("00002")
                .build(),
            Student.builder()
                .name("Gamma")
                .matrikelNr("00003")
                .build(),
            Student.builder()
                .name("Delta")
                .matrikelNr("00004")
                .build()
        ).collect(Collectors.toList());
    }


    /**
     * Aufruf der Liste von Kursen
     */
    static final List<Kurs> KURSE = getKurse();

    /**
     * Erstellung der Kurse für Datenbank
     *
     * @return Liste an Kursen
     */
    private static List<Kurs> getKurse() {
        return Stream.of(
            // BaseKurs (Test)
            Kurs.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .kursName("BaseKurs")
                .dozent(Dozent.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000010"))
                    .name("Yamato")
                    .build()
                )
                .ects(0)
                .raumNummer("001")
                .beschreibung("Ein Test-Kurs zur Überprüfung der Eingaben")
                .studenten(
                    Stream.of(
                        STUDENTS.get(0),
                        STUDENTS.get(1),
                        STUDENTS.get(2),
                        STUDENTS.get(3)
                    ).toList()
                )
                .build(),

            Kurs.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .kursName("IT-Sicherheit")
                .dozent(
                    Dozent.builder()
                        .name("Martin")
                        .id(UUID.fromString("00000000-0000-0000-0000-000000000011"))
                        .build()
                )
                .ects(15)
                .raumNummer("102")
                .beschreibung("Modul aus Semester 3")
                .studenten(
                    Stream.of(
                        STUDENTS.get(0),
                        STUDENTS.get(2)
                    ).collect(Collectors.toList())
                ).build(),

            Kurs.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .kursName("Finanzwirtschaft")
                .dozent(Dozent.builder()
                    .name("Nees")
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000012"))
                    .build()
                )
                .ects(10)
                .raumNummer("333")
                .beschreibung("In diesem Kurs werden Interbankgeschäfte näher gebracht")
                .studenten(
                    Stream.of(
                        STUDENTS.get(1),
                        STUDENTS.get(3)
                    ).collect(Collectors.toList())
                )
                .build()
        ).collect(Collectors.toList());

    }

}
