package com.acme.kurs.rest;

import com.acme.kurs.entity.Kurs;
import com.acme.kurs.service.KursReadService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.Collection;

import static com.acme.kurs.rest.KursGetController.REST_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Kontroller Klasse für die REST-Schnittstelle
 */
@RestController
@RequestMapping(REST_PATH)
@OpenAPIDefinition(info = @Info(title = "Kurs API", version = "v1"))
@RequiredArgsConstructor
@Slf4j
public class KursGetController {

    /**
     * Basispfad für die REST-Schnittstelle.
     */
    public static final String REST_PATH = "/rest";

    /**
     * Muster für die UUID
     */
    public static final String ID_PATTERN = "[\\da-f]{8}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{4}-[\\da-f]{12}";

    private final KursReadService service;

    /**
     * Sucher mit einer Kurs-ID
     * @param id KursID
     * @return Liste des Kurses
     */
    @Operation(summary = "Suche mit der Kurs-ID", tags = "Suchen")
    @GetMapping(path ="{id:" + ID_PATTERN + "}", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Kurs gefunden")
    @ApiResponse(responseCode = "404", description = "Kurs nicht gefunden")
    Kurs getById(@PathVariable final UUID id) {
        log.debug("getById: id={}, Thread {}", id, Thread.currentThread().getName());
        return service.findById(id);
    }

    /**
     * Aufruf aller Kurse
     * @return Liste der gesamten Kurse
     */
    @Operation(summary = "Alle Kurse aufrufen", tags = "Suchen")
    @GetMapping(path = "AlleKurseAbrufen", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Alle Kurse")
    @ApiResponse(responseCode = "404", description = "Kurs nicht gefunden")
    Collection<Kurs> getAll() {
        log.debug("getAll: Thread {}", Thread.currentThread().getName());

        return service.findAll();
    }


    /**
     * Suche anhand von Suchkriterien
     * @param suchkriterien Suchkriterien
     * @return Liste mit passenden Kursen
     */
    @Operation(summary = "Suche mit Suchkriterien", tags = "Suchen")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Liste mit passenden Kursen")
    @ApiResponse(responseCode = "404", description = "Keine passenden Kurse gefunden")
    Collection<Kurs> get(@RequestParam @Nonnull final MultiValueMap<String, String> suchkriterien) {
        log.debug("get: Suchkriterien {}", suchkriterien);

        final var kurse = service.find(suchkriterien);
        log.debug("get: {}", suchkriterien);
        return kurse;

    }


}
