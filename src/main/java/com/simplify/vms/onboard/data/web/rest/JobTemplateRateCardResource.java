package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.JobTemplateRateCard;
import com.simplify.vms.onboard.data.repository.JobTemplateRateCardRepository;
import com.simplify.vms.onboard.data.service.JobTemplateRateCardService;
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
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.JobTemplateRateCard}.
 */
@RestController
@RequestMapping("/api")
public class JobTemplateRateCardResource {

    private final Logger log = LoggerFactory.getLogger(JobTemplateRateCardResource.class);

    private static final String ENTITY_NAME = "jobTemplateRateCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobTemplateRateCardService jobTemplateRateCardService;

    private final JobTemplateRateCardRepository jobTemplateRateCardRepository;

    public JobTemplateRateCardResource(
        JobTemplateRateCardService jobTemplateRateCardService,
        JobTemplateRateCardRepository jobTemplateRateCardRepository
    ) {
        this.jobTemplateRateCardService = jobTemplateRateCardService;
        this.jobTemplateRateCardRepository = jobTemplateRateCardRepository;
    }

    /**
     * {@code POST  /job-template-rate-cards} : Create a new jobTemplateRateCard.
     *
     * @param jobTemplateRateCard the jobTemplateRateCard to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobTemplateRateCard, or with status {@code 400 (Bad Request)} if the jobTemplateRateCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-template-rate-cards")
    public ResponseEntity<JobTemplateRateCard> createJobTemplateRateCard(@RequestBody JobTemplateRateCard jobTemplateRateCard)
        throws URISyntaxException {
        log.debug("REST request to save JobTemplateRateCard : {}", jobTemplateRateCard);
        if (jobTemplateRateCard.getId() != null) {
            throw new BadRequestAlertException("A new jobTemplateRateCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobTemplateRateCard result = jobTemplateRateCardService.save(jobTemplateRateCard);
        return ResponseEntity
            .created(new URI("/api/job-template-rate-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-template-rate-cards/:id} : Updates an existing jobTemplateRateCard.
     *
     * @param id the id of the jobTemplateRateCard to save.
     * @param jobTemplateRateCard the jobTemplateRateCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobTemplateRateCard,
     * or with status {@code 400 (Bad Request)} if the jobTemplateRateCard is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobTemplateRateCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-template-rate-cards/{id}")
    public ResponseEntity<JobTemplateRateCard> updateJobTemplateRateCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobTemplateRateCard jobTemplateRateCard
    ) throws URISyntaxException {
        log.debug("REST request to update JobTemplateRateCard : {}, {}", id, jobTemplateRateCard);
        if (jobTemplateRateCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobTemplateRateCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobTemplateRateCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobTemplateRateCard result = jobTemplateRateCardService.save(jobTemplateRateCard);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobTemplateRateCard.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-template-rate-cards/:id} : Partial updates given fields of an existing jobTemplateRateCard, field will ignore if it is null
     *
     * @param id the id of the jobTemplateRateCard to save.
     * @param jobTemplateRateCard the jobTemplateRateCard to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobTemplateRateCard,
     * or with status {@code 400 (Bad Request)} if the jobTemplateRateCard is not valid,
     * or with status {@code 404 (Not Found)} if the jobTemplateRateCard is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobTemplateRateCard couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-template-rate-cards/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<JobTemplateRateCard> partialUpdateJobTemplateRateCard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobTemplateRateCard jobTemplateRateCard
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobTemplateRateCard partially : {}, {}", id, jobTemplateRateCard);
        if (jobTemplateRateCard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobTemplateRateCard.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobTemplateRateCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobTemplateRateCard> result = jobTemplateRateCardService.partialUpdate(jobTemplateRateCard);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobTemplateRateCard.getId().toString())
        );
    }

    /**
     * {@code GET  /job-template-rate-cards} : get all the jobTemplateRateCards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobTemplateRateCards in body.
     */
    @GetMapping("/job-template-rate-cards")
    public ResponseEntity<List<JobTemplateRateCard>> getAllJobTemplateRateCards(Pageable pageable) {
        log.debug("REST request to get a page of JobTemplateRateCards");
        Page<JobTemplateRateCard> page = jobTemplateRateCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-template-rate-cards/:id} : get the "id" jobTemplateRateCard.
     *
     * @param id the id of the jobTemplateRateCard to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobTemplateRateCard, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-template-rate-cards/{id}")
    public ResponseEntity<JobTemplateRateCard> getJobTemplateRateCard(@PathVariable Long id) {
        log.debug("REST request to get JobTemplateRateCard : {}", id);
        Optional<JobTemplateRateCard> jobTemplateRateCard = jobTemplateRateCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobTemplateRateCard);
    }

    /**
     * {@code DELETE  /job-template-rate-cards/:id} : delete the "id" jobTemplateRateCard.
     *
     * @param id the id of the jobTemplateRateCard to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-template-rate-cards/{id}")
    public ResponseEntity<Void> deleteJobTemplateRateCard(@PathVariable Long id) {
        log.debug("REST request to delete JobTemplateRateCard : {}", id);
        jobTemplateRateCardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
