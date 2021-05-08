package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.CustomFieldType;
import com.simplify.vms.onboard.data.repository.CustomFieldTypeRepository;
import com.simplify.vms.onboard.data.service.CustomFieldTypeService;
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
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.CustomFieldType}.
 */
@RestController
@RequestMapping("/api")
public class CustomFieldTypeResource {

    private final Logger log = LoggerFactory.getLogger(CustomFieldTypeResource.class);

    private static final String ENTITY_NAME = "customFieldType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomFieldTypeService customFieldTypeService;

    private final CustomFieldTypeRepository customFieldTypeRepository;

    public CustomFieldTypeResource(CustomFieldTypeService customFieldTypeService, CustomFieldTypeRepository customFieldTypeRepository) {
        this.customFieldTypeService = customFieldTypeService;
        this.customFieldTypeRepository = customFieldTypeRepository;
    }

    /**
     * {@code POST  /custom-field-types} : Create a new customFieldType.
     *
     * @param customFieldType the customFieldType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customFieldType, or with status {@code 400 (Bad Request)} if the customFieldType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-field-types")
    public ResponseEntity<CustomFieldType> createCustomFieldType(@RequestBody CustomFieldType customFieldType) throws URISyntaxException {
        log.debug("REST request to save CustomFieldType : {}", customFieldType);
        if (customFieldType.getId() != null) {
            throw new BadRequestAlertException("A new customFieldType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomFieldType result = customFieldTypeService.save(customFieldType);
        return ResponseEntity
            .created(new URI("/api/custom-field-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-field-types/:id} : Updates an existing customFieldType.
     *
     * @param id the id of the customFieldType to save.
     * @param customFieldType the customFieldType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customFieldType,
     * or with status {@code 400 (Bad Request)} if the customFieldType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customFieldType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-field-types/{id}")
    public ResponseEntity<CustomFieldType> updateCustomFieldType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomFieldType customFieldType
    ) throws URISyntaxException {
        log.debug("REST request to update CustomFieldType : {}, {}", id, customFieldType);
        if (customFieldType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customFieldType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customFieldTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomFieldType result = customFieldTypeService.save(customFieldType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customFieldType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /custom-field-types/:id} : Partial updates given fields of an existing customFieldType, field will ignore if it is null
     *
     * @param id the id of the customFieldType to save.
     * @param customFieldType the customFieldType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customFieldType,
     * or with status {@code 400 (Bad Request)} if the customFieldType is not valid,
     * or with status {@code 404 (Not Found)} if the customFieldType is not found,
     * or with status {@code 500 (Internal Server Error)} if the customFieldType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/custom-field-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustomFieldType> partialUpdateCustomFieldType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomFieldType customFieldType
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomFieldType partially : {}, {}", id, customFieldType);
        if (customFieldType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customFieldType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customFieldTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomFieldType> result = customFieldTypeService.partialUpdate(customFieldType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customFieldType.getId().toString())
        );
    }

    /**
     * {@code GET  /custom-field-types} : get all the customFieldTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customFieldTypes in body.
     */
    @GetMapping("/custom-field-types")
    public ResponseEntity<List<CustomFieldType>> getAllCustomFieldTypes(Pageable pageable) {
        log.debug("REST request to get a page of CustomFieldTypes");
        Page<CustomFieldType> page = customFieldTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /custom-field-types/:id} : get the "id" customFieldType.
     *
     * @param id the id of the customFieldType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customFieldType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-field-types/{id}")
    public ResponseEntity<CustomFieldType> getCustomFieldType(@PathVariable Long id) {
        log.debug("REST request to get CustomFieldType : {}", id);
        Optional<CustomFieldType> customFieldType = customFieldTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customFieldType);
    }

    /**
     * {@code DELETE  /custom-field-types/:id} : delete the "id" customFieldType.
     *
     * @param id the id of the customFieldType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-field-types/{id}")
    public ResponseEntity<Void> deleteCustomFieldType(@PathVariable Long id) {
        log.debug("REST request to delete CustomFieldType : {}", id);
        customFieldTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
