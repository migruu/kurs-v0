package com.acme.kurs.service;


import com.acme.kurs.entity.Kurs;
import com.acme.kurs.repository.KursRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;
import java.util.UUID;


@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class KursWriteService {

    private final KursRepository repo;

    /**
     * Einenneuen Kurs anglegen.
     *
     * @param kurs
     * @return Validen erstellten neuen Kurs
     * @throws KursnameExistsException Falls der zu erstellende Kurs bereits exisitert
     */
    public Kurs create(@Valid final Kurs kurs) {
        log.debug("create: {}", kurs);

        if (repo.isKursnameExisiting(kurs.getKursName())) {
            throw new KursnameExistsException(kurs.getKursName());
        }

        final var kursDB = repo.create(kurs);
        log.debug("create: {}", kursDB);
        return kursDB;
    }


    /**
     * Einen vorhandenen Kurs aktualisieren
     * @param kurs Kurs-Objekt ohne ID
     * @param id ID des Kurses
     * @throws NotFoundException Keine Kurs-ID gefunden
     * @throws KursnameExistsException Bereits vorhandener Kursname
     */
    public void update(@Valid final Kurs kurs, final UUID id) {
        log.debug("update: {}", kurs);
        log.debug("update: id={}", id);

        final var kursname = kurs.getKursName();
        final var kursDb = repo
            .findById(id)
            .orElseThrow(() -> new NotFoundException(id));
        if (!Objects.equals(kursname, kursDb.getKursName()) && repo.isKursnameExisiting(kursname)) {
            log.debug("update: kursname {} existiert", kursname);
            throw new KursnameExistsException(kursname);
        }
        kurs.setId(id);
        repo.update(kurs);

    }


}
