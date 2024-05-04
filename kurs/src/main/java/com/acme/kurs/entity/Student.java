package com.acme.kurs.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
@Setter
@ToString
public class Student {

    /**
     * Muster für eine Matrikel Nummer im HKA Format
     */
    public static final String MATRIKEL_PATTERN = "^\\d{5}$";

    /**
     * Name eines Studenten
     */
    @NotBlank
    private String name;

    /**
     * Matrikel Nummer im String Format mit Expression in Ziffern
     */
    @NotNull
    @Pattern(regexp = MATRIKEL_PATTERN)
    private String matrikelNr;


}
