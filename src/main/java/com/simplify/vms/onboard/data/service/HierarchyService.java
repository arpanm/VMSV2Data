package com.simplify.vms.onboard.data.service;

import com.simplify.vms.onboard.data.domain.Hierarchy;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Hierarchy}.
 */
public interface HierarchyService {
    /**
     * Save a hierarchy.
     *
     * @param hierarchy the entity to save.
     * @return the persisted entity.
     */
    Hierarchy save(Hierarchy hierarchy);

    /**
     * Partially updates a hierarchy.
     *
     * @param hierarchy the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Hierarchy> partialUpdate(Hierarchy hierarchy);

    /**
     * Get all the hierarchies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Hierarchy> findAll(Pageable pageable);

    /**
     * Get all the hierarchies with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Hierarchy> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" hierarchy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Hierarchy> findOne(Long id);

    /**
     * Delete the "id" hierarchy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
