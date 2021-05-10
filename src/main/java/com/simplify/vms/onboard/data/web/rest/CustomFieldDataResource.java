package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.CustomFieldData;
import com.simplify.vms.onboard.data.repository.CustomFieldDataRepository;
import com.simplify.vms.onboard.data.service.CustomFieldDataService;
import com.simplify.vms.onboard.data.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.CustomFieldData}.
 */
@RestController
@RequestMapping("/api")
public class CustomFieldDataResource {

    private final Logger log = LoggerFactory.getLogger(CustomFieldDataResource.class);

    private static final String ENTITY_NAME = "customFieldData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomFieldDataService customFieldDataService;

    private final CustomFieldDataRepository customFieldDataRepository;

    public CustomFieldDataResource(CustomFieldDataService customFieldDataService, CustomFieldDataRepository customFieldDataRepository) {
        this.customFieldDataService = customFieldDataService;
        this.customFieldDataRepository = customFieldDataRepository;
    }

    /**
     * {@code POST  /custom-field-data} : Create a new customFieldData.
     *
     * @param customFieldData the customFieldData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customFieldData, or with status {@code 400 (Bad Request)} if the customFieldData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-field-data")
    public ResponseEntity<CustomFieldData> createCustomFieldData(@RequestBody CustomFieldData customFieldData) throws URISyntaxException {
        log.debug("REST request to save CustomFieldData : {}", customFieldData);
        if (customFieldData.getId() != null) {
            throw new BadRequestAlertException("A new customFieldData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomFieldData result = customFieldDataService.save(customFieldData);
        return ResponseEntity
            .created(new URI("/api/custom-field-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-field-data/:id} : Updates an existing customFieldData.
     *
     * @param id the id of the customFieldData to save.
     * @param customFieldData the customFieldData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customFieldData,
     * or with status {@code 400 (Bad Request)} if the customFieldData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customFieldData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-field-data/{id}")
    public ResponseEntity<CustomFieldData> updateCustomFieldData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomFieldData customFieldData
    ) throws URISyntaxException {
        log.debug("REST request to update CustomFieldData : {}, {}", id, customFieldData);
        if (customFieldData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customFieldData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customFieldDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomFieldData result = customFieldDataService.save(customFieldData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customFieldData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /custom-field-data/:id} : Partial updates given fields of an existing customFieldData, field will ignore if it is null
     *
     * @param id the id of the customFieldData to save.
     * @param customFieldData the customFieldData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customFieldData,
     * or with status {@code 400 (Bad Request)} if the customFieldData is not valid,
     * or with status {@code 404 (Not Found)} if the customFieldData is not found,
     * or with status {@code 500 (Internal Server Error)} if the customFieldData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/custom-field-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomFieldData> partialUpdateCustomFieldData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomFieldData customFieldData
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomFieldData partially : {}, {}", id, customFieldData);
        if (customFieldData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customFieldData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customFieldDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomFieldData> result = customFieldDataService.partialUpdate(customFieldData);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customFieldData.getId().toString())
        );
    }

    /**
     * {@code GET  /custom-field-data} : get all the customFieldData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customFieldData in body.
     */
    @GetMapping("/custom-field-data")
    public List<CustomFieldData> getAllCustomFieldData() {
        log.debug("REST request to get all CustomFieldData");
        return customFieldDataService.findAll();
    }

    /**
     * {@code GET  /custom-field-data/:id} : get the "id" customFieldData.
     *
     * @param id the id of the customFieldData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customFieldData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-field-data/{id}")
    public ResponseEntity<CustomFieldData> getCustomFieldData(@PathVariable Long id) {
        log.debug("REST request to get CustomFieldData : {}", id);
        Optional<CustomFieldData> customFieldData = customFieldDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customFieldData);
    }

    /**
     * {@code DELETE  /custom-field-data/:id} : delete the "id" customFieldData.
     *
     * @param id the id of the customFieldData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-field-data/{id}")
    public ResponseEntity<Void> deleteCustomFieldData(@PathVariable Long id) {
        log.debug("REST request to delete CustomFieldData : {}", id);
        customFieldDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
