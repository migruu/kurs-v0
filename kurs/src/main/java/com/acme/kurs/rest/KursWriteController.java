package com.acme.kurs.rest;

import com.acme.kurs.service.KursReadService;
import com.acme.kurs.service.KursWriteService;
import com.acme.kurs.service.KursnameExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.util.UUID;

import static com.acme.kurs.rest.KursGetController.ID_PATTERN;
import static com.acme.kurs.rest.KursGetController.REST_PATH;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@Controller
@RequestMapping(REST_PATH)
@RequiredArgsConstructor
@Slf4j
class KursWriteController {

    private static final String PROBLEM_PATH = "/problem";
    private final KursWriteService service;
    private final KursMapper mapper;
    private final KursReadService readService;
    private final UriHelper uriHelper;


    /**
     * Einen neuen Kurs anlegen.
     *
     * @param kursDTO Ein Objekt Kurs aus dem Request-Body.
     * @param request Ein Request-Objekt, um "Location" im Response-Header zu erstellen
     * @return Response mit Statuscode
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Einen neuen Kurs anlegen", tags = "Neuanlegen")
    @ApiResponse(responseCode = "201", description = "Kurs neu angelegt")
    @ApiResponse(responseCode = "400", description = "Syntaktische Fehler im Request-Body")
    @ApiResponse(responseCode = "422", description = "Ungültige Werte oder Email vorhanden")
    ResponseEntity<Void> post(@RequestBody final KursDTO kursDTO, final HttpServletRequest request) {
        log.debug("post {}", kursDTO);

        final var kursInput = mapper.toKurs(kursDTO);
        final var kurs = service.create(kursInput);
        final var baseUri = uriHelper.getBaseUri(request).toString();
        final var location = URI.create(baseUri + '/' + kurs.getId());

        return created(location).build();
    }

    /**
     * Einen vorhandenen Kurs-Datensatz überschreiben.
     *
     * @param id      ID des zu aktualisierenden Kurses
     * @param kursDTO Das Kurs-Objekt
     */
    @PutMapping(path = "{id:" + ID_PATTERN + "}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Einen Kurs mit neuen Werten aktualisieren", tags = "Aktualisieren")
    @ApiResponse(responseCode = "204", description = "Aktualisiert")
    @ApiResponse(responseCode = "400", description = "Syntaktische Fehler im Request-Body")
    @ApiResponse(responseCode = "404", description = "Kurs nicht gefunden")
    @ApiResponse(responseCode = "422", description = "Ungültige Werte oder Kursnamen bereits vorhanden")
    void put(@PathVariable final UUID id, @RequestBody final KursDTO kursDTO) {
        log.debug("put: id={}, {}", id, kursDTO);

        final var kursInput = mapper.toKurs(kursDTO);
        service.update(kursInput, id);
    }


    @ExceptionHandler
    ProblemDetail onConstraintViolations(
        final ConstraintViolationException ex,
        final HttpServletRequest request
    ) {
        log.debug("onConstraintViolations: {}", ex.getMessage());

        final var problemDetail = ProblemDetail.forStatusAndDetail(
            UNPROCESSABLE_ENTITY,
            // Methodenname und Argumentname entfernen: siehe @Valid in der Service-Klasse
            ex.getMessage().replace("create.kurs.", "").replace("update.kurs.", "")
        );
        problemDetail.setType(URI.create(PROBLEM_PATH + ProblemType.CONSTRAINTS.getValue()));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));

        return problemDetail;
    }


    @ExceptionHandler
    ProblemDetail onKursnameExists(final KursnameExistsException ex, final HttpServletRequest request) {
        log.debug("onKursnameExists: {}", ex.getMessage());

        final var problemDetail = ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, ex.getMessage());

        problemDetail.setType(URI.create(PROBLEM_PATH + ProblemType.CONSTRAINTS.getValue()));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));

        return problemDetail;
    }

}
