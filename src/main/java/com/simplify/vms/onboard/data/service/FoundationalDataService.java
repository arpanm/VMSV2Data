package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.FoundationalData;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FoundationalData}.
 */
public interface FoundationalDataService {
    /**
     * Save a foundationalData.
     *
     * @param foundationalData the entity to save.
     * @return the persisted entity.
     */
    FoundationalData save(FoundationalData foundationalData);

    /**
     * Partially updates a foundationalData.
     *
     * @param foundationalData the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FoundationalData> partialUpdate(FoundationalData foundationalData);

    /**
     * Get all the foundationalData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FoundationalData> findAll(Pageable pageable);

    /**
     * Get all the foundationalData with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FoundationalData> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" foundationalData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FoundationalData> findOne(Long id);

    /**
     * Delete the "id" foundationalData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
