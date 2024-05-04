package com.acme.kurs.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

/**
 * Klasse für die Exceptions auf nicht gefundene Kurse
 */
@Getter
public final class NotFoundException extends RuntimeException {

    private final UUID id;
    private final Map<String, List<String>> suchkriterien;

    /**
     * Ausgabe über nicht gefundene Kurse bei einer Suche mit Kurs-ID
     *
     * @param id Kurs-ID
     */
    NotFoundException(final UUID id) {
        super("Kein Kurs mit der ID " + id + " gefunden.");         //Da STR Templates aus Java 23 entfernt werden
        this.id = id;
        suchkriterien = null;
    }

    /**
     * Ausgabe über nicht gefundene Kurse bei einer Suche mit bestimmten Suchkriterien
     *
     * @param suchkriterien Suchkriterien
     */
    NotFoundException(final Map<String, List<String>> suchkriterien) {
        super("Keine Kurse gefunden.");
        id = null;
        this.suchkriterien = suchkriterien;
    }

}
