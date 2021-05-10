package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.JobCategoryTitle;
import com.simplify.vms.onboard.data.repository.JobCategoryTitleRepository;
import com.simplify.vms.onboard.data.service.JobCategoryTitleService;
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
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.JobCategoryTitle}.
 */
@RestController
@RequestMapping("/api")
public class JobCategoryTitleResource {

    private final Logger log = LoggerFactory.getLogger(JobCategoryTitleResource.class);

    private static final String ENTITY_NAME = "jobCategoryTitle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobCategoryTitleService jobCategoryTitleService;

    private final JobCategoryTitleRepository jobCategoryTitleRepository;

    public JobCategoryTitleResource(
        JobCategoryTitleService jobCategoryTitleService,
        JobCategoryTitleRepository jobCategoryTitleRepository
    ) {
        this.jobCategoryTitleService = jobCategoryTitleService;
        this.jobCategoryTitleRepository = jobCategoryTitleRepository;
    }

    /**
     * {@code POST  /job-category-titles} : Create a new jobCategoryTitle.
     *
     * @param jobCategoryTitle the jobCategoryTitle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobCategoryTitle, or with status {@code 400 (Bad Request)} if the jobCategoryTitle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-category-titles")
    public ResponseEntity<JobCategoryTitle> createJobCategoryTitle(@RequestBody JobCategoryTitle jobCategoryTitle)
        throws URISyntaxException {
        log.debug("REST request to save JobCategoryTitle : {}", jobCategoryTitle);
        if (jobCategoryTitle.getId() != null) {
            throw new BadRequestAlertException("A new jobCategoryTitle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobCategoryTitle result = jobCategoryTitleService.save(jobCategoryTitle);
        return ResponseEntity
            .created(new URI("/api/job-category-titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-category-titles/:id} : Updates an existing jobCategoryTitle.
     *
     * @param id the id of the jobCategoryTitle to save.
     * @param jobCategoryTitle the jobCategoryTitle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobCategoryTitle,
     * or with status {@code 400 (Bad Request)} if the jobCategoryTitle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobCategoryTitle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-category-titles/{id}")
    public ResponseEntity<JobCategoryTitle> updateJobCategoryTitle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobCategoryTitle jobCategoryTitle
    ) throws URISyntaxException {
        log.debug("REST request to update JobCategoryTitle : {}, {}", id, jobCategoryTitle);
        if (jobCategoryTitle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobCategoryTitle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobCategoryTitleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobCategoryTitle result = jobCategoryTitleService.save(jobCategoryTitle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobCategoryTitle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-category-titles/:id} : Partial updates given fields of an existing jobCategoryTitle, field will ignore if it is null
     *
     * @param id the id of the jobCategoryTitle to save.
     * @param jobCategoryTitle the jobCategoryTitle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobCategoryTitle,
     * or with status {@code 400 (Bad Request)} if the jobCategoryTitle is not valid,
     * or with status {@code 404 (Not Found)} if the jobCategoryTitle is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobCategoryTitle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-category-titles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<JobCategoryTitle> partialUpdateJobCategoryTitle(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobCategoryTitle jobCategoryTitle
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobCategoryTitle partially : {}, {}", id, jobCategoryTitle);
        if (jobCategoryTitle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobCategoryTitle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobCategoryTitleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobCategoryTitle> result = jobCategoryTitleService.partialUpdate(jobCategoryTitle);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobCategoryTitle.getId().toString())
        );
    }

    /**
     * {@code GET  /job-category-titles} : get all the jobCategoryTitles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobCategoryTitles in body.
     */
    @GetMapping("/job-category-titles")
    public ResponseEntity<List<JobCategoryTitle>> getAllJobCategoryTitles(Pageable pageable) {
        log.debug("REST request to get a page of JobCategoryTitles");
        Page<JobCategoryTitle> page = jobCategoryTitleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-category-titles/:id} : get the "id" jobCategoryTitle.
     *
     * @param id the id of the jobCategoryTitle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobCategoryTitle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-category-titles/{id}")
    public ResponseEntity<JobCategoryTitle> getJobCategoryTitle(@PathVariable Long id) {
        log.debug("REST request to get JobCategoryTitle : {}", id);
        Optional<JobCategoryTitle> jobCategoryTitle = jobCategoryTitleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobCategoryTitle);
    }

    /**
     * {@code DELETE  /job-category-titles/:id} : delete the "id" jobCategoryTitle.
     *
     * @param id the id of the jobCategoryTitle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-category-titles/{id}")
    public ResponseEntity<Void> deleteJobCategoryTitle(@PathVariable Long id) {
        log.debug("REST request to delete JobCategoryTitle : {}", id);
        jobCategoryTitleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
