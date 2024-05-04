package com.acme.kurs.service;

import com.acme.kurs.entity.Kurs;
import com.acme.kurs.repository.KursRepository;
import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;


/**
 * Anwendungslogik für Abrufe der Kurse
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KursReadService {

    private final KursRepository repo;

    /**
     * Logik für die Abrufe der Kurse mit bestimmter ID.
     *
     * @param id Kurs-ID
     * @return Kurs mit gegebener Kurs-ID
     */
    public @NonNull Kurs findById(final UUID id) {
        log.debug("findById: id={}", id);
        final var kurs = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(id));
        log.debug("findById: {}", kurs);
        return kurs;
    }

    /**
     * Aufruf für alle Kurse
     *
     * @return Liste mit allen Kursen
     */
    public @NonNull Collection<Kurs> findAll() {
        log.debug("findAll");
        return repo.findAll();

    }

    /**
     * Suche anhand bestimmter Suchkriterien
     *
     * @param suchkriterien Suchkriterien
     * @return Liste gesuchter Kurse
     */
    @SuppressWarnings({"NestedIfDepth"})
    public @NonNull Collection<Kurs> find(@NonNull final Map<String, List<String>> suchkriterien) {
        log.debug("find: suchkriterien={}", suchkriterien);

        if (suchkriterien.isEmpty()) {
            return repo.findAll();
        }

        if (suchkriterien.size() == 1) {

            final var kursname = suchkriterien.get("kursname");
            if (kursname != null && kursname.size() == 1) {
                final var kurse = repo.findByName(kursname.getFirst());
                if (kurse.isEmpty()) {
                    throw new NotFoundException(suchkriterien);
                }
                log.debug("find (kursname): {}", kurse);
                return kurse;
            }

            final var ects = suchkriterien.get("ects");
            if (ects != null && ects.size() == 1) {
                final var ectsSuche = repo.findByName(ects.getFirst());
                if (ectsSuche.isEmpty()) {
                    throw new NotFoundException(suchkriterien);
                }
                log.debug("find (ects): {}", ectsSuche);
                return ectsSuche;
            }
        }

        final var dozenten = suchkriterien.get("dozent");
        if (dozenten != null && dozenten.size() == 1) {
            final var dozent = repo.findByDozent(dozenten.getFirst());
            if (dozenten.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (dozent): {}", dozenten);
            return dozent;
        }


        final var kurse = repo.find(suchkriterien);
        if (kurse.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }
        log.debug("find : {}", kurse);
        return kurse;
    }


}
