package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.WorkLocation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link WorkLocation}.
 */
public interface WorkLocationService {
    /**
     * Save a workLocation.
     *
     * @param workLocation the entity to save.
     * @return the persisted entity.
     */
    WorkLocation save(WorkLocation workLocation);

    /**
     * Partially updates a workLocation.
     *
     * @param workLocation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkLocation> partialUpdate(WorkLocation workLocation);

    /**
     * Get all the workLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkLocation> findAll(Pageable pageable);

    /**
     * Get the "id" workLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkLocation> findOne(Long id);

    /**
     * Delete the "id" workLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
