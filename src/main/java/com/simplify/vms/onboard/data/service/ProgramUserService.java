package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.ProgramUser;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProgramUser}.
 */
public interface ProgramUserService {
    /**
     * Save a programUser.
     *
     * @param programUser the entity to save.
     * @return the persisted entity.
     */
    ProgramUser save(ProgramUser programUser);

    /**
     * Partially updates a programUser.
     *
     * @param programUser the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProgramUser> partialUpdate(ProgramUser programUser);

    /**
     * Get all the programUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramUser> findAll(Pageable pageable);

    /**
     * Get the "id" programUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramUser> findOne(Long id);

    /**
     * Delete the "id" programUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
