package com.acme.kurs.service;

import lombok.Getter;

@Getter
public class KursnameExistsException extends RuntimeException {

    private final String name;

    /**
     * Ausnahme für bereits existierenden Kursnamen
     * @param name Kursname
     */
    KursnameExistsException(final String name) {
        super("Dieser Kursname " + name + "existiert bereits");
        this.name = name;
    }
}
