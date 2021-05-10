package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.FoundationalDataType;
import com.simplify.vms.onboard.data.repository.FoundationalDataTypeRepository;
import com.simplify.vms.onboard.data.service.FoundationalDataTypeService;
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
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.FoundationalDataType}.
 */
@RestController
@RequestMapping("/api")
public class FoundationalDataTypeResource {

    private final Logger log = LoggerFactory.getLogger(FoundationalDataTypeResource.class);

    private static final String ENTITY_NAME = "foundationalDataType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoundationalDataTypeService foundationalDataTypeService;

    private final FoundationalDataTypeRepository foundationalDataTypeRepository;

    public FoundationalDataTypeResource(
        FoundationalDataTypeService foundationalDataTypeService,
        FoundationalDataTypeRepository foundationalDataTypeRepository
    ) {
        this.foundationalDataTypeService = foundationalDataTypeService;
        this.foundationalDataTypeRepository = foundationalDataTypeRepository;
    }

    /**
     * {@code POST  /foundational-data-types} : Create a new foundationalDataType.
     *
     * @param foundationalDataType the foundationalDataType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foundationalDataType, or with status {@code 400 (Bad Request)} if the foundationalDataType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foundational-data-types")
    public ResponseEntity<FoundationalDataType> createFoundationalDataType(@RequestBody FoundationalDataType foundationalDataType)
        throws URISyntaxException {
        log.debug("REST request to save FoundationalDataType : {}", foundationalDataType);
        if (foundationalDataType.getId() != null) {
            throw new BadRequestAlertException("A new foundationalDataType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoundationalDataType result = foundationalDataTypeService.save(foundationalDataType);
        return ResponseEntity
            .created(new URI("/api/foundational-data-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foundational-data-types/:id} : Updates an existing foundationalDataType.
     *
     * @param id the id of the foundationalDataType to save.
     * @param foundationalDataType the foundationalDataType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foundationalDataType,
     * or with status {@code 400 (Bad Request)} if the foundationalDataType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foundationalDataType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foundational-data-types/{id}")
    public ResponseEntity<FoundationalDataType> updateFoundationalDataType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoundationalDataType foundationalDataType
    ) throws URISyntaxException {
        log.debug("REST request to update FoundationalDataType : {}, {}", id, foundationalDataType);
        if (foundationalDataType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foundationalDataType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foundationalDataTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FoundationalDataType result = foundationalDataTypeService.save(foundationalDataType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foundationalDataType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foundational-data-types/:id} : Partial updates given fields of an existing foundationalDataType, field will ignore if it is null
     *
     * @param id the id of the foundationalDataType to save.
     * @param foundationalDataType the foundationalDataType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foundationalDataType,
     * or with status {@code 400 (Bad Request)} if the foundationalDataType is not valid,
     * or with status {@code 404 (Not Found)} if the foundationalDataType is not found,
     * or with status {@code 500 (Internal Server Error)} if the foundationalDataType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foundational-data-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FoundationalDataType> partialUpdateFoundationalDataType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoundationalDataType foundationalDataType
    ) throws URISyntaxException {
        log.debug("REST request to partial update FoundationalDataType partially : {}, {}", id, foundationalDataType);
        if (foundationalDataType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foundationalDataType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foundationalDataTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FoundationalDataType> result = foundationalDataTypeService.partialUpdate(foundationalDataType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foundationalDataType.getId().toString())
        );
    }

    /**
     * {@code GET  /foundational-data-types} : get all the foundationalDataTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foundationalDataTypes in body.
     */
    @GetMapping("/foundational-data-types")
    public ResponseEntity<List<FoundationalDataType>> getAllFoundationalDataTypes(Pageable pageable) {
        log.debug("REST request to get a page of FoundationalDataTypes");
        Page<FoundationalDataType> page = foundationalDataTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foundational-data-types/:id} : get the "id" foundationalDataType.
     *
     * @param id the id of the foundationalDataType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foundationalDataType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foundational-data-types/{id}")
    public ResponseEntity<FoundationalDataType> getFoundationalDataType(@PathVariable Long id) {
        log.debug("REST request to get FoundationalDataType : {}", id);
        Optional<FoundationalDataType> foundationalDataType = foundationalDataTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foundationalDataType);
    }

    /**
     * {@code DELETE  /foundational-data-types/:id} : delete the "id" foundationalDataType.
     *
     * @param id the id of the foundationalDataType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foundational-data-types/{id}")
    public ResponseEntity<Void> deleteFoundationalDataType(@PathVariable Long id) {
        log.debug("REST request to delete FoundationalDataType : {}", id);
        foundationalDataTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
