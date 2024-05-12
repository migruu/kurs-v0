package com.acme.kurs.repository;

import com.acme.kurs.entity.Kurs;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.acme.kurs.repository.DB.KURSE;
import static java.util.Collections.emptyList;


/**
 * Repository für den DB-Zugriff
 */
@Repository
@Slf4j
public class KursRepository {

    /**
     * Abruf für alle Kurse
     *
     * @return Collection von allen Kursen
     */
    public @NonNull Collection<Kurs> findAll() {
        return KURSE;
    }


    /**
     * Einen Kurs anhand seiner ID suchen.
     *
     * @param id Die Id des gesuchten Kurses
     * @return Optional mit dem gefundenen Kurs oder leeres Optional
     */
    public Optional<Kurs> findById(final UUID id) {

        log.debug("findById: id={}", id);
        return KURSE.stream()
            .filter(kurs -> Objects.equals(kurs.getId(), id))
            .findFirst();

    }


    /**
     * Abfrage nach bereits vorhandenen Kursnamen.
     * @param name Kursname
     * @return Bereits existent
     */
    public boolean isKursnameExisiting(final String name) {
        log.debug("isKursnameExisiting: name={}", name);
        final var count = KURSE.stream()
            .filter(kurs -> kurs.getKursName().contentEquals(name))
            .count();
        log.debug("isKursnameExististing: count={}", count);
        return count > 0L;
    }


    /**
     * Kurse anhand von Suchkriterien ermitteln
     *
     * @param suchkriterien Suchkriterien
     * @return Liste an passenden Kursen
     */
    public @NonNull Collection<Kurs> find(final Map<String, ? extends List<String>> suchkriterien) {
        log.debug("find: suchkriterien={}", suchkriterien);

        if (suchkriterien.isEmpty()) {
            return findAll();
        }

        for (final var entry : suchkriterien.entrySet()) {
            switch (entry.getKey()) {
                case "name" -> {
                    return findByName(entry.getValue().getFirst());
                }
                case "dozent" -> {
                    return findByDozent(entry.getValue().getFirst());
                }
                case "ects" -> {
                    return findByEcts(Integer.parseInt(entry.getValue().getFirst()));
                }
                default -> {
                    log.debug("find: Ungültige Suchkriterien {}", entry.getKey());
                    return emptyList();
                }
            }
        }

        return emptyList();
    }


    /**
     * Alle Kurse mit bestimmten Kursnamen finden
     *
     * @param name Kursnamen
     * @return Liste an Kursen
     */
    public Collection<Kurs> findByName(final String name) {
        log.debug("findByName: name={}", name);
        return KURSE.stream()
            .filter(kurs -> kurs.getKursName().contains(name))
            .toList();
    }


    /**
     * Kurse anhand Namens für einen Dozenten finden
     *
     * @param name Dozenten Namen
     * @return Liste an Kursen mit gegebenen Dozenten
     */
    public Collection<Kurs> findByDozent(final String name) {
        log.debug("findByDozent: name={}", name);
        return KURSE.stream()
            .filter(kurs -> kurs.getDozent().getName().contains(name))
            .toList();
    }

    /**
     * Kurse ausgeben anhand der ECTS Hoehe
     *
     * @param ects Ects Integer
     * @return Liste an Kursen mit gegebener ECTS Anzahl
     */
    public Collection<Kurs> findByEcts(final int ects) {
        log.debug("findByEcts: ects={}", ects);
        return KURSE.stream()
            .filter(kurs -> kurs.getEcts() == ects)
            .toList();
    }


    /**
     * Methode um einen neuen Kurs zu erstellen.
     *
     * @param kurs - Neuer Kurs
     * @return Kurs mit ID
     */
    public @NonNull Kurs create(final @NonNull Kurs kurs) {
        log.debug("create: kurs={}", kurs);
        kurs.setId(UUID.randomUUID());
        KURSE.add(kurs);
        log.debug("create: {}", kurs);
        return kurs;
    }


    /**
     * Einen Kurs aus der Liste verändern
     *
     * @param kurs Kurs aus der Liste KURSE
     */
    public void update(final @NonNull Kurs kurs) {
        log.debug("update: {}", kurs);
        final OptionalInt index = IntStream
            .range(0, KURSE.size())
            .filter(i -> Objects.equals((KURSE.get(i).getId()), kurs.getId()))
            .findFirst();
        log.trace("update: index={}", index);
        if (index.isEmpty()) {
            return;

        }
        KURSE.set(index.getAsInt(), kurs);
        log.debug("update: {}", kurs);
    }


}
