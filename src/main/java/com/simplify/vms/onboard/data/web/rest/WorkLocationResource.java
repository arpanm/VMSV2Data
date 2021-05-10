package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.WorkLocation;
import com.simplify.vms.onboard.data.repository.WorkLocationRepository;
import com.simplify.vms.onboard.data.service.WorkLocationService;
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
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.WorkLocation}.
 */
@RestController
@RequestMapping("/api")
public class WorkLocationResource {

    private final Logger log = LoggerFactory.getLogger(WorkLocationResource.class);

    private static final String ENTITY_NAME = "workLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkLocationService workLocationService;

    private final WorkLocationRepository workLocationRepository;

    public WorkLocationResource(WorkLocationService workLocationService, WorkLocationRepository workLocationRepository) {
        this.workLocationService = workLocationService;
        this.workLocationRepository = workLocationRepository;
    }

    /**
     * {@code POST  /work-locations} : Create a new workLocation.
     *
     * @param workLocation the workLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workLocation, or with status {@code 400 (Bad Request)} if the workLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-locations")
    public ResponseEntity<WorkLocation> createWorkLocation(@RequestBody WorkLocation workLocation) throws URISyntaxException {
        log.debug("REST request to save WorkLocation : {}", workLocation);
        if (workLocation.getId() != null) {
            throw new BadRequestAlertException("A new workLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkLocation result = workLocationService.save(workLocation);
        return ResponseEntity
            .created(new URI("/api/work-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-locations/:id} : Updates an existing workLocation.
     *
     * @param id the id of the workLocation to save.
     * @param workLocation the workLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workLocation,
     * or with status {@code 400 (Bad Request)} if the workLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-locations/{id}")
    public ResponseEntity<WorkLocation> updateWorkLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkLocation workLocation
    ) throws URISyntaxException {
        log.debug("REST request to update WorkLocation : {}, {}", id, workLocation);
        if (workLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkLocation result = workLocationService.save(workLocation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-locations/:id} : Partial updates given fields of an existing workLocation, field will ignore if it is null
     *
     * @param id the id of the workLocation to save.
     * @param workLocation the workLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workLocation,
     * or with status {@code 400 (Bad Request)} if the workLocation is not valid,
     * or with status {@code 404 (Not Found)} if the workLocation is not found,
     * or with status {@code 500 (Internal Server Error)} if the workLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/work-locations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<WorkLocation> partialUpdateWorkLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkLocation workLocation
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkLocation partially : {}, {}", id, workLocation);
        if (workLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workLocation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkLocation> result = workLocationService.partialUpdate(workLocation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workLocation.getId().toString())
        );
    }

    /**
     * {@code GET  /work-locations} : get all the workLocations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workLocations in body.
     */
    @GetMapping("/work-locations")
    public ResponseEntity<List<WorkLocation>> getAllWorkLocations(Pageable pageable) {
        log.debug("REST request to get a page of WorkLocations");
        Page<WorkLocation> page = workLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-locations/:id} : get the "id" workLocation.
     *
     * @param id the id of the workLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-locations/{id}")
    public ResponseEntity<WorkLocation> getWorkLocation(@PathVariable Long id) {
        log.debug("REST request to get WorkLocation : {}", id);
        Optional<WorkLocation> workLocation = workLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workLocation);
    }

    /**
     * {@code DELETE  /work-locations/:id} : delete the "id" workLocation.
     *
     * @param id the id of the workLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-locations/{id}")
    public ResponseEntity<Void> deleteWorkLocation(@PathVariable Long id) {
        log.debug("REST request to delete WorkLocation : {}", id);
        workLocationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
