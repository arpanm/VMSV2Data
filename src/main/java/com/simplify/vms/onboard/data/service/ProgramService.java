package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.Program;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Program}.
 */
public interface ProgramService {
    /**
     * Save a program.
     *
     * @param program the entity to save.
     * @return the persisted entity.
     */
    Program save(Program program);

    /**
     * Partially updates a program.
     *
     * @param program the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Program> partialUpdate(Program program);

    /**
     * Get all the programs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Program> findAll(Pageable pageable);

    /**
     * Get the "id" program.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Program> findOne(Long id);

    /**
     * Delete the "id" program.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
