package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.JobTemplate;
import com.simplify.vms.onboard.data.repository.JobTemplateRepository;
import com.simplify.vms.onboard.data.service.JobTemplateService;
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
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.JobTemplate}.
 */
@RestController
@RequestMapping("/api")
public class JobTemplateResource {

    private final Logger log = LoggerFactory.getLogger(JobTemplateResource.class);

    private static final String ENTITY_NAME = "jobTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobTemplateService jobTemplateService;

    private final JobTemplateRepository jobTemplateRepository;

    public JobTemplateResource(JobTemplateService jobTemplateService, JobTemplateRepository jobTemplateRepository) {
        this.jobTemplateService = jobTemplateService;
        this.jobTemplateRepository = jobTemplateRepository;
    }

    /**
     * {@code POST  /job-templates} : Create a new jobTemplate.
     *
     * @param jobTemplate the jobTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobTemplate, or with status {@code 400 (Bad Request)} if the jobTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-templates")
    public ResponseEntity<JobTemplate> createJobTemplate(@RequestBody JobTemplate jobTemplate) throws URISyntaxException {
        log.debug("REST request to save JobTemplate : {}", jobTemplate);
        if (jobTemplate.getId() != null) {
            throw new BadRequestAlertException("A new jobTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobTemplate result = jobTemplateService.save(jobTemplate);
        return ResponseEntity
            .created(new URI("/api/job-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-templates/:id} : Updates an existing jobTemplate.
     *
     * @param id the id of the jobTemplate to save.
     * @param jobTemplate the jobTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobTemplate,
     * or with status {@code 400 (Bad Request)} if the jobTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-templates/{id}")
    public ResponseEntity<JobTemplate> updateJobTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobTemplate jobTemplate
    ) throws URISyntaxException {
        log.debug("REST request to update JobTemplate : {}, {}", id, jobTemplate);
        if (jobTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobTemplate result = jobTemplateService.save(jobTemplate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-templates/:id} : Partial updates given fields of an existing jobTemplate, field will ignore if it is null
     *
     * @param id the id of the jobTemplate to save.
     * @param jobTemplate the jobTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobTemplate,
     * or with status {@code 400 (Bad Request)} if the jobTemplate is not valid,
     * or with status {@code 404 (Not Found)} if the jobTemplate is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-templates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<JobTemplate> partialUpdateJobTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobTemplate jobTemplate
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobTemplate partially : {}, {}", id, jobTemplate);
        if (jobTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobTemplate> result = jobTemplateService.partialUpdate(jobTemplate);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobTemplate.getId().toString())
        );
    }

    /**
     * {@code GET  /job-templates} : get all the jobTemplates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobTemplates in body.
     */
    @GetMapping("/job-templates")
    public ResponseEntity<List<JobTemplate>> getAllJobTemplates(Pageable pageable) {
        log.debug("REST request to get a page of JobTemplates");
        Page<JobTemplate> page = jobTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-templates/:id} : get the "id" jobTemplate.
     *
     * @param id the id of the jobTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-templates/{id}")
    public ResponseEntity<JobTemplate> getJobTemplate(@PathVariable Long id) {
        log.debug("REST request to get JobTemplate : {}", id);
        Optional<JobTemplate> jobTemplate = jobTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobTemplate);
    }

    /**
     * {@code DELETE  /job-templates/:id} : delete the "id" jobTemplate.
     *
     * @param id the id of the jobTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-templates/{id}")
    public ResponseEntity<Void> deleteJobTemplate(@PathVariable Long id) {
        log.debug("REST request to delete JobTemplate : {}", id);
        jobTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
