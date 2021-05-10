package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.FoundationalData;
import com.simplify.vms.onboard.data.repository.FoundationalDataRepository;
import com.simplify.vms.onboard.data.service.FoundationalDataService;
import com.simplify.vms.onboard.data.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.FoundationalData}.
 */
@RestController
@RequestMapping("/api")
public class FoundationalDataResource {

    private final Logger log = LoggerFactory.getLogger(FoundationalDataResource.class);

    private static final String ENTITY_NAME = "foundationalData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoundationalDataService foundationalDataService;

    private final FoundationalDataRepository foundationalDataRepository;

    public FoundationalDataResource(
        FoundationalDataService foundationalDataService,
        FoundationalDataRepository foundationalDataRepository
    ) {
        this.foundationalDataService = foundationalDataService;
        this.foundationalDataRepository = foundationalDataRepository;
    }

    /**
     * {@code POST  /foundational-data} : Create a new foundationalData.
     *
     * @param foundationalData the foundationalData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foundationalData, or with status {@code 400 (Bad Request)} if the foundationalData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foundational-data")
    public ResponseEntity<FoundationalData> createFoundationalData(@RequestBody FoundationalData foundationalData)
        throws URISyntaxException {
        log.debug("REST request to save FoundationalData : {}", foundationalData);
        if (foundationalData.getId() != null) {
            throw new BadRequestAlertException("A new foundationalData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoundationalData result = foundationalDataService.save(foundationalData);
        return ResponseEntity
            .created(new URI("/api/foundational-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foundational-data/:id} : Updates an existing foundationalData.
     *
     * @param id the id of the foundationalData to save.
     * @param foundationalData the foundationalData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foundationalData,
     * or with status {@code 400 (Bad Request)} if the foundationalData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foundationalData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foundational-data/{id}")
    public ResponseEntity<FoundationalData> updateFoundationalData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoundationalData foundationalData
    ) throws URISyntaxException {
        log.debug("REST request to update FoundationalData : {}, {}", id, foundationalData);
        if (foundationalData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foundationalData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foundationalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FoundationalData result = foundationalDataService.save(foundationalData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foundationalData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foundational-data/:id} : Partial updates given fields of an existing foundationalData, field will ignore if it is null
     *
     * @param id the id of the foundationalData to save.
     * @param foundationalData the foundationalData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foundationalData,
     * or with status {@code 400 (Bad Request)} if the foundationalData is not valid,
     * or with status {@code 404 (Not Found)} if the foundationalData is not found,
     * or with status {@code 500 (Internal Server Error)} if the foundationalData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foundational-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FoundationalData> partialUpdateFoundationalData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoundationalData foundationalData
    ) throws URISyntaxException {
        log.debug("REST request to partial update FoundationalData partially : {}, {}", id, foundationalData);
        if (foundationalData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foundationalData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foundationalDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FoundationalData> result = foundationalDataService.partialUpdate(foundationalData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foundationalData.getId().toString())
        );
    }

    /**
     * {@code GET  /foundational-data} : get all the foundationalData.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foundationalData in body.
     */
    @GetMapping("/foundational-data")
    public ResponseEntity<List<FoundationalData>> getAllFoundationalData(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of FoundationalData");
        Page<FoundationalData> page;
        if (eagerload) {
            page = foundationalDataService.findAllWithEagerRelationships(pageable);
        } else {
            page = foundationalDataService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foundational-data/:id} : get the "id" foundationalData.
     *
     * @param id the id of the foundationalData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foundationalData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foundational-data/{id}")
    public ResponseEntity<FoundationalData> getFoundationalData(@PathVariable Long id) {
        log.debug("REST request to get FoundationalData : {}", id);
        Optional<FoundationalData> foundationalData = foundationalDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foundationalData);
    }

    /**
     * {@code DELETE  /foundational-data/:id} : delete the "id" foundationalData.
     *
     * @param id the id of the foundationalData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foundational-data/{id}")
    public ResponseEntity<Void> deleteFoundationalData(@PathVariable Long id) {
        log.debug("REST request to delete FoundationalData : {}", id);
        foundationalDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
