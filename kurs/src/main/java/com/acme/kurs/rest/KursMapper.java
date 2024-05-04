package com.acme.kurs.rest;


import com.acme.kurs.entity.Kurs;
import com.acme.kurs.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;


@Mapper(nullValueIterableMappingStrategy = RETURN_DEFAULT, componentModel = "spring")
interface KursMapper {

    @Mapping(target = "id", ignore = true)
    Kurs toKurs(KursDTO dto);

    Student toStudent(StudentDTO dto);

}
