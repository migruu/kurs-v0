package com.acme.kurs.rest;


import com.acme.kurs.entity.Dozent;
import com.acme.kurs.entity.Kurs;
import com.acme.kurs.entity.Student;
import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;


@Mapper(nullValueIterableMappingStrategy = RETURN_DEFAULT, componentModel = "spring")
@AnnotateWith(ExcludeFromJacocoGeneratedReport.class)
interface KursMapper {

    /**
     * Erstellung eines Kurses aus einem DTO-Objekt.
     * @param dto DTO-Objekt
     * @return Konvertierte Kurs Objekt
     */
    @Mapping(target = "id", ignore = true)
    Kurs toKurs(KursDTO dto);

    /**
     * Erstellung eines Studenten aus einem DTO-Objekt.
     * @param dto DTO-Objekt
     * @return Konvertiertes Studenten Objekt
     */
    @Mapping(target = "matrikelNr", ignore = true)
    Student toStudent(StudentDTO dto);

    /**
     * Erstellung eines Dozenten aus einem DTO-Objekt.
     * @param dto DTO-Objekt
     * @return Konvertiertes Dozenten Objekt
     */
    @Mapping(target = "id", ignore = true)
    Dozent toDozent(DozentDTO dto);
}
