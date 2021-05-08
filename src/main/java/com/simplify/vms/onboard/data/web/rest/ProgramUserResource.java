package com.simplify.vms.onboard.data.web.rest;

import com.simplify.vms.onboard.data.domain.ProgramUser;
import com.simplify.vms.onboard.data.repository.ProgramUserRepository;
import com.simplify.vms.onboard.data.service.ProgramUserService;
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
 * REST controller for managing {@link com.simplify.vms.onboard.data.domain.ProgramUser}.
 */
@RestController
@RequestMapping("/api")
public class ProgramUserResource {

    private final Logger log = LoggerFactory.getLogger(ProgramUserResource.class);

    private static final String ENTITY_NAME = "programUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramUserService programUserService;

    private final ProgramUserRepository programUserRepository;

    public ProgramUserResource(ProgramUserService programUserService, ProgramUserRepository programUserRepository) {
        this.programUserService = programUserService;
        this.programUserRepository = programUserRepository;
    }

    /**
     * {@code POST  /program-users} : Create a new programUser.
     *
     * @param programUser the programUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programUser, or with status {@code 400 (Bad Request)} if the programUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/program-users")
    public ResponseEntity<ProgramUser> createProgramUser(@RequestBody ProgramUser programUser) throws URISyntaxException {
        log.debug("REST request to save ProgramUser : {}", programUser);
        if (programUser.getId() != null) {
            throw new BadRequestAlertException("A new programUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgramUser result = programUserService.save(programUser);
        return ResponseEntity
            .created(new URI("/api/program-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /program-users/:id} : Updates an existing programUser.
     *
     * @param id the id of the programUser to save.
     * @param programUser the programUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programUser,
     * or with status {@code 400 (Bad Request)} if the programUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/program-users/{id}")
    public ResponseEntity<ProgramUser> updateProgramUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProgramUser programUser
    ) throws URISyntaxException {
        log.debug("REST request to update ProgramUser : {}, {}", id, programUser);
        if (programUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProgramUser result = programUserService.save(programUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /program-users/:id} : Partial updates given fields of an existing programUser, field will ignore if it is null
     *
     * @param id the id of the programUser to save.
     * @param programUser the programUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programUser,
     * or with status {@code 400 (Bad Request)} if the programUser is not valid,
     * or with status {@code 404 (Not Found)} if the programUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the programUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/program-users/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProgramUser> partialUpdateProgramUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProgramUser programUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProgramUser partially : {}, {}", id, programUser);
        if (programUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProgramUser> result = programUserService.partialUpdate(programUser);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programUser.getId().toString())
        );
    }

    /**
     * {@code GET  /program-users} : get all the programUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programUsers in body.
     */
    @GetMapping("/program-users")
    public ResponseEntity<List<ProgramUser>> getAllProgramUsers(Pageable pageable) {
        log.debug("REST request to get a page of ProgramUsers");
        Page<ProgramUser> page = programUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /program-users/:id} : get the "id" programUser.
     *
     * @param id the id of the programUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/program-users/{id}")
    public ResponseEntity<ProgramUser> getProgramUser(@PathVariable Long id) {
        log.debug("REST request to get ProgramUser : {}", id);
        Optional<ProgramUser> programUser = programUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programUser);
    }

    /**
     * {@code DELETE  /program-users/:id} : delete the "id" programUser.
     *
     * @param id the id of the programUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/program-users/{id}")
    public ResponseEntity<Void> deleteProgramUser(@PathVariable Long id) {
        log.debug("REST request to delete ProgramUser : {}", id);
        programUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
