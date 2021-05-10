package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.Hierarchy;
import com.simplify.vms.onboard.data.repository.HierarchyRepository;
import com.simplify.vms.onboard.data.service.HierarchyService;
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
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.Hierarchy}.
 */
@RestController
@RequestMapping("/api")
public class HierarchyResource {

    private final Logger log = LoggerFactory.getLogger(HierarchyResource.class);

    private static final String ENTITY_NAME = "hierarchy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HierarchyService hierarchyService;

    private final HierarchyRepository hierarchyRepository;

    public HierarchyResource(HierarchyService hierarchyService, HierarchyRepository hierarchyRepository) {
        this.hierarchyService = hierarchyService;
        this.hierarchyRepository = hierarchyRepository;
    }

    /**
     * {@code POST  /hierarchies} : Create a new hierarchy.
     *
     * @param hierarchy the hierarchy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hierarchy, or with status {@code 400 (Bad Request)} if the hierarchy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hierarchies")
    public ResponseEntity<Hierarchy> createHierarchy(@RequestBody Hierarchy hierarchy) throws URISyntaxException {
        log.debug("REST request to save Hierarchy : {}", hierarchy);
        if (hierarchy.getId() != null) {
            throw new BadRequestAlertException("A new hierarchy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hierarchy result = hierarchyService.save(hierarchy);
        return ResponseEntity
            .created(new URI("/api/hierarchies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hierarchies/:id} : Updates an existing hierarchy.
     *
     * @param id the id of the hierarchy to save.
     * @param hierarchy the hierarchy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hierarchy,
     * or with status {@code 400 (Bad Request)} if the hierarchy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hierarchy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hierarchies/{id}")
    public ResponseEntity<Hierarchy> updateHierarchy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Hierarchy hierarchy
    ) throws URISyntaxException {
        log.debug("REST request to update Hierarchy : {}, {}", id, hierarchy);
        if (hierarchy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hierarchy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hierarchyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Hierarchy result = hierarchyService.save(hierarchy);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hierarchy.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hierarchies/:id} : Partial updates given fields of an existing hierarchy, field will ignore if it is null
     *
     * @param id the id of the hierarchy to save.
     * @param hierarchy the hierarchy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hierarchy,
     * or with status {@code 400 (Bad Request)} if the hierarchy is not valid,
     * or with status {@code 404 (Not Found)} if the hierarchy is not found,
     * or with status {@code 500 (Internal Server Error)} if the hierarchy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hierarchies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Hierarchy> partialUpdateHierarchy(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Hierarchy hierarchy
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hierarchy partially : {}, {}", id, hierarchy);
        if (hierarchy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hierarchy.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hierarchyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hierarchy> result = hierarchyService.partialUpdate(hierarchy);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hierarchy.getId().toString())
        );
    }

    /**
     * {@code GET  /hierarchies} : get all the hierarchies.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hierarchies in body.
     */
    @GetMapping("/hierarchies")
    public ResponseEntity<List<Hierarchy>> getAllHierarchies(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Hierarchies");
        Page<Hierarchy> page;
        if (eagerload) {
            page = hierarchyService.findAllWithEagerRelationships(pageable);
        } else {
            page = hierarchyService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hierarchies/:id} : get the "id" hierarchy.
     *
     * @param id the id of the hierarchy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hierarchy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hierarchies/{id}")
    public ResponseEntity<Hierarchy> getHierarchy(@PathVariable Long id) {
        log.debug("REST request to get Hierarchy : {}", id);
        Optional<Hierarchy> hierarchy = hierarchyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hierarchy);
    }

    /**
     * {@code DELETE  /hierarchies/:id} : delete the "id" hierarchy.
     *
     * @param id the id of the hierarchy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hierarchies/{id}")
    public ResponseEntity<Void> deleteHierarchy(@PathVariable Long id) {
        log.debug("REST request to delete Hierarchy : {}", id);
        hierarchyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
